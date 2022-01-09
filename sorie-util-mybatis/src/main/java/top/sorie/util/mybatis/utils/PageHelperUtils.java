package top.sorie.util.mybatis.utils;

import com.github.pagehelper.PageHelper;
import top.sorie.util.core.utils.SqlUtils;
import top.sorie.util.core.utils.StringUtils;
import top.sorie.util.mybatis.annotation.CustomOrder;
import top.sorie.util.mybatis.annotation.DefaultOrder;
import top.sorie.util.mybatis.annotation.TableAlias;
import top.sorie.util.mybatis.domain.PageDomain;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * 分页工具类
 *
 * @author soriee
 * @date 2022/1/9 21:09
 */
public class PageHelperUtils {
    /**
     * 设置请求分页数据
     */
    public static void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            String orderBy = SqlUtils.escapeOrderBySql(pageDomain.getOrderBy());
            Boolean reasonable = pageDomain.getReasonable();
            PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
        }
    }

    /**
     * 设置请求分页数据
     * 并根据class的注解设置排序列
     * 列对应表名优先级
     * TableAlias > CustomOrder > DefaultOrder
     *
     * @param clazz
     */
    public static void startPage(Class<?> clazz) {
        // 正常分页的情况
        if (clazz == null) {
            startPage();
            return;
        }
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }

        // 处理orderBy的情况。
        String orderBy = SqlUtils.escapeOrderBySql(pageDomain.getOrderBy());
        String column = SqlUtils.escapeOrderBySql(pageDomain.getOrderByColumn());
        // 获取默认排序
        if (StringUtils.isEmpty(orderBy)) {
            orderBy = getDefaultOrderByAnnotation(clazz);
        }

        if (StringUtils.isEmpty(orderBy)) {
            return;
        }
        // 根据列获取表名
        String tableName = getTableNameByColumn(clazz, column);
        if (StringUtils.isNotEmpty(tableName)) {
            orderBy = tableName + "." + orderBy;
        }
        PageHelper.orderBy(orderBy);

        if (pageDomain.getReasonable() != null) {
            PageHelper.getLocalPage().setReasonable(pageDomain.getReasonable());
        }
    }

    /**
     * 尝试从field的TableAlias获取表别名
     *
     * @param clazz
     * @param column
     * @return
     */
    private static String getFieldTableName(Class<?> clazz, String column) {
        // 获取对象中所有的成员变量
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            String fieldName = field.getName();
            if (Objects.equals(column, fieldName)) {
                if (field.isAnnotationPresent(TableAlias.class)) {
                    TableAlias orderTableAlias = field.getAnnotation(TableAlias.class);
                    return orderTableAlias.value();
                }
            }
        }
        return null;
    }

    /**
     * 尝试从CustomOrder获取表别名
     *
     * @param clazz
     * @param column
     * @return
     */
    private static String getCustomOrderTableName(Class<?> clazz, String column) {
        // 获取对象中所有的成员变量
        if (clazz.isAnnotationPresent(CustomOrder.class)) {
            CustomOrder[] customOrders = clazz.getAnnotationsByType(CustomOrder.class);
            for (CustomOrder customOrder : customOrders) {
                String realColumn = column;
                if (column.contains("_")) {
                    realColumn = StringUtils.toCamelCase(customOrder.column());
                }
                if (Objects.equals(column, realColumn)) {
                    return customOrder.tableName();
                }
            }
        }
        return null;
    }

    /**
     * 获取默认排序
     *
     * @param clazz
     * @return
     */
    private static String getDefaultTableName(Class<?> clazz) {
        // 最后，查找类上的tableName
        if (clazz.isAnnotationPresent(DefaultOrder.class)) {
            DefaultOrder defaultOrder = clazz.getAnnotation(DefaultOrder.class);
            if (StringUtils.isNotEmpty(defaultOrder.tableName())) {
                return defaultOrder.tableName();
            }
        }
        return null;
    }

    /**
     * 通过过滤列获取对应的table
     *
     * @param clazz
     * @param column
     * @return
     */
    private static String getTableNameByColumn(Class<?> clazz, String column) {
        if (clazz == null) {
            return null;
        }
        String tableName = null;
        // 未指定排序 则尝试设置默认排序的Table
        if (StringUtils.isEmpty(column)) {
            tableName = getDefaultTableName(clazz);
            return tableName;
        }


        // 优先获取字段的tableName
        tableName = getFieldTableName(clazz, column);
        if (StringUtils.isNotEmpty(tableName)) {
            return tableName;
        }

        // 其次查找CustomOrder定义的column对应的TableName
        tableName = getCustomOrderTableName(clazz, column);
        if (StringUtils.isNotEmpty(tableName)) {
            return tableName;
        }

        // 获取默认排序
        tableName = getDefaultTableName(clazz);

        return tableName;
    }

    /**
     * 获取类注解获取默认过滤列
     *
     * @param clazz
     * @return
     */
    private static String getDefaultOrderByAnnotation(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(DefaultOrder.class)) {
            return null;
        }
        DefaultOrder defaultOrder = clazz.getAnnotation(DefaultOrder.class);
        String orderBy = null;
        if (StringUtils.isNotEmpty(defaultOrder.column())) {
            orderBy = StringUtils.toUnderScoreCase(defaultOrder.column()) + " " + defaultOrder.orderType();
        }
        return orderBy;
    }
}
