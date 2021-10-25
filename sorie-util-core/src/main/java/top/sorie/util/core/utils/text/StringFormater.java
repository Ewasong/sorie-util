package top.sorie.util.core.utils.text;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringFormater {
    /**
     * 格式化字符串<br>
     * 此方法只是简单将指定占位符 按照顺序替换为参数<br>
     * 如果想输出占位符使用 \\转义即可，如果想输出占位符之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "{}", "a", "b") =》 this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "{}", "a", "b") =》 this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "{}", "a", "b") =》 this is \a for b<br>
     *
     * @param strPattern  字符串模板
     * @param placeHolder 占位符，例如{}
     * @param paramArr    参数列表
     * @return 结果
     */
    public static String formatWith(String strPattern, String placeHolder, Object... paramArr) {
        if (StringUtils.isBlank(strPattern) || StringUtils.isBlank(placeHolder)) {
            return strPattern;
        }

        StringBuilder stringBuilder = new StringBuilder(strPattern.length() + 50);
        // 上一次处理的地方
        int handlePos = 0;
        int delimIndex;// 占位符所在位置
        for (int i = 0; i < paramArr.length; i++) {
            delimIndex = strPattern.indexOf(placeHolder, handlePos);
            if (delimIndex < 0) {
                if (handlePos == 0) {
                    // 不带占位符的模板直接返回
                    return strPattern;
                } else {
                    // 字符串模板剩余部分不再包含占位符，加入剩余部分后返回结果
                    stringBuilder.append(strPattern, handlePos, strPattern.length());
                }
            }
            // 转义符
            if (delimIndex > 0 && strPattern.charAt(delimIndex - 1) == StringContant.C_BACKSLASH) { // 转义符
                if (delimIndex > 1 && strPattern.charAt(delimIndex - 2) == StringContant.C_BACKSLASH) { // 双转义符
                    // 转义符之前还有一个转义符，占位符依旧有效
                    stringBuilder.append(strPattern, handlePos, delimIndex - 1);
                    stringBuilder.append(StringUtils.utf8Str(paramArr[i]));
                    handlePos = delimIndex + placeHolder.length();
                } else {
                    // 占位符被转义
                    i--;
                    stringBuilder.append(strPattern, handlePos, delimIndex - 1);
                    stringBuilder.append(placeHolder.charAt(0));
                    handlePos = delimIndex + 1;
                }
            } else {// 正常占位符
                stringBuilder.append(strPattern, handlePos, delimIndex);
                stringBuilder.append(StringUtils.utf8Str(paramArr[i]));
                handlePos = delimIndex + placeHolder.length();
            }
        }
        return stringBuilder.toString();
    }
}
