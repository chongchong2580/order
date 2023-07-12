package com.iflytek.config;

import com.iflytek.interfaces.EncryptField;
import com.iflytek.utils.Base64Util;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @Author: chongchong
 * @DateTime: 2023/7/11 1:49
 * @Description: 加密asp
 */
@Slf4j
@Aspect
@Component
public class EncryptAspect {

    //拦截需加密注解
    @Pointcut("@annotation(com.iflytek.interfaces.NeedEncrypt)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //加密
        encrypt(joinPoint);

        return joinPoint.proceed();
    }

    public void encrypt(ProceedingJoinPoint joinPoint)  {
        Object[] objects=null;
        try {
            objects = joinPoint.getArgs();
            if (objects.length != 0) {
                for (int i = 0; i < objects.length; i++) {
                    //抛砖引玉 ，可自行扩展其他类型字段的判断
                    if (objects[i] instanceof String) {
                        objects[i] = encryptValue(objects[i]);
                    } else {
                        encryptObject(objects[i]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密对象
     * @param obj
     * @throws IllegalAccessException
     */
    private void encryptObject(Object obj) throws IllegalAccessException {

        if (Objects.isNull(obj)) {
            log.info("当前需要加密的object为null");
            return;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean containEncryptField = field.isAnnotationPresent(EncryptField.class);
            if (containEncryptField) {
                //获取访问权
                field.setAccessible(true);
                if(field.get(obj) != null){
                    String value = Base64Util.getBase64(String.valueOf(field.get(obj)));
                    field.set(obj, value);
                }
            }
        }
    }

    /**
     * 加密单个值
     * @param realValue
     * @return
     */
    public String encryptValue(Object realValue) {
        try {
            realValue = Base64Util.getBase64(String.valueOf(realValue));
        } catch (Exception e) {
            log.info("加密异常={}",e.getMessage());
        }
        return String.valueOf(realValue);
    }
}
