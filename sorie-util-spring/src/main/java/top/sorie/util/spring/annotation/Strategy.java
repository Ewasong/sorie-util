package top.sorie.util.spring.annotation;

import java.lang.annotation.*;


/**
 * 策略
 * @author soriee
 * @date 2022/1/9 22:48
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Strategy {
    // 策略名称
    String value();
}
