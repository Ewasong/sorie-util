package top.sorie.util.core.annotation;

import java.lang.annotation.*;


/**
 * 非null标记
 * @author soriee
 * @date 2021/10/21 22:21
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE})
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface NonNull {
}
