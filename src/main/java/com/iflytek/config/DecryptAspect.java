package com.iflytek.config;

import com.iflytek.interfaces.EncryptField;
import com.iflytek.utils.Base64Util;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author: chongchong
 * @DateTime: 2023/7/11 1:57
 * @Description: 解密asp
 */
//解密AOP
@Slf4j
@Aspect
@Component
public class DecryptAspect {


    //拦截需解密注解
    @Pointcut("@annotation(com.iflytek.interfaces.NeedDecrypt)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //解密
        Object result = decrypt(joinPoint);
        return result;
    }

    public Object decrypt(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            Object obj = joinPoint.proceed();
            if (obj != null) {
                //抛砖引玉 ，可自行扩展其他类型字段的判断
                if (obj instanceof String) {
                    decryptValue(obj);
                } else {
                    result = decryptData(obj);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }

    private Object decryptData(Object obj) throws IllegalAccessException {

        if (Objects.isNull(obj)) {
            return null;
        }
        if (obj instanceof ArrayList) {
            decryptList(obj);
        } else {
            decryptObj(obj);
        }


        return obj;
    }

    /**
     * 针对单个实体类进行 解密
     * @param obj
     * @throws IllegalAccessException
     */
    private void decryptObj(Object obj) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean hasSecureField = field.isAnnotationPresent(EncryptField.class);
            if (hasSecureField) {
                field.setAccessible(true);
                if(field.get(obj) != null) {
                    String realValue = (String) field.get(obj);
                    String value = Base64Util.getFromBase64(realValue);
                    field.set(obj, value);
                }
            }
        }
    }

    /**
     * 针对list<实体来> 进行反射、解密
     * @param obj
     * @throws IllegalAccessException
     */
    private void decryptList(Object obj) throws IllegalAccessException {
        List<Object> result = new ArrayList<>();
        if (obj instanceof ArrayList) {
            for (Object o : (List<?>) obj) {
                result.add(o);
            }
        }
        for (Object object : result) {
            decryptObj(object);
        }
    }

    public String decryptValue(Object realValue) {
        try {
            realValue = Base64Util.getFromBase64(String.valueOf(realValue));
        } catch (Exception e) {
            log.info("解密异常={}", e.getMessage());
        }
        return String.valueOf(realValue);
    }
}
