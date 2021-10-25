package top.sorie.util.core.utils.text;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CharUtils {


    /**
     * 是否空白符
     * 空白符包括空格、制表符、全角空格和不间断空格
     *
     * @param c 字符
     * @return 是否空白符
     * @see Character#isWhitespace(int)
     */
    public static boolean isBlankChar(char c) {
        return isBlankChar((int) c);
    }

    /**
     * 是否空白符<br>
     * 空白符包括空格、制表符、全角空格和不间断空格<br>
     *
     * @param c 字符
     * @return 是否空白符
     * @see Character#isWhitespace(int)
     * @see Character#isSpaceChar(int)
     */
    public static boolean isBlankChar(int c) {
        return Character.isWhitespace(c)
                || Character.isSpaceChar(c)
                || c == '\ufeff'
                || c == '\u202a'
                || c == '\u0000';
    }
}
