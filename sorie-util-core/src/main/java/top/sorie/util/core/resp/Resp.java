package top.sorie.util.core.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.sorie.util.core.constant.HttpStatus;

import static top.sorie.util.core.constant.RespConstant.DEFAULT_RESP_ERR_MSG;

/**
 * 统一响应头
 * @author soriee
 * @return
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resp<T> {

    /**
     * 响应code HttpStatus.Code
     * @see top.sorie.util.core.constant.HttpStatus
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    /**
     * 成功响应
     * @author soriee
     * @date 2021/10/21 22:19
     * @param data
     * @return
     */
    public static <T> Resp<T> ok(T data) {
        return Resp.<T>builder()
                .code(HttpStatus.HTTP_OK)
                .data(data).build();
    }

    /**
     * 成功响应
     * @author soriee
     * @date 2021/10/21 22:22
     * @param data
     * @param msg
     * @return
     */
    public static <T> Resp<T> ok(T data, String msg) {
        return Resp.<T>builder()
                .code(HttpStatus.HTTP_OK)
                .data(data)
                .msg(msg)
                .build();
    }

    /**
     * 失败响应
     * @author soriee
     * @date 2021/10/21 22:19
     * @return
     */
    public static <T> Resp<T> fail() {
        return Resp.<T>builder()
                .code(HttpStatus.HTTP_INTERNAL_ERROR)
                .msg(DEFAULT_RESP_ERR_MSG)
                .build();
    }

    /**
     * 失败响应
     * @author soriee
     * @date 2021/10/21 22:22
     * @param msg
     * @return
     */
    public static <T> Resp<T> fail(String msg) {
        return Resp.<T>builder()
                .code(HttpStatus.HTTP_INTERNAL_ERROR)
                .msg(msg)
                .build();
    }


    /**
     * 响应
     * @author soriee
     * @date 2021/10/21 22:22
     * @param data
     * @param msg
     * @param code
     * @return
     */
    public static <T> Resp<T> resp(T data, String msg, Integer code) {
        return Resp.<T>builder()
                .code(code)
                .data(data)
                .msg(msg)
                .build();
    }
}
