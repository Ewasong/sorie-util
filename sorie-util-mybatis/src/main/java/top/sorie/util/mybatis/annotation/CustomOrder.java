package top.sorie.util.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 自定义排序
 * 如何使用:
 * @DefaultOrder(column="createTime", orderType="desc", tableName="user")
 * class UserVO {
 *     ...
 * }
 * startPage(UserVO.class)
 * @author ruoyi
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CustomOrders.class)
public @interface CustomOrder {
    // 列名 驼峰最终会转换为下划线命名法作为最终排序列名。
    String column() default "";

    // 列对应的表名
    String tableName() default "";
}