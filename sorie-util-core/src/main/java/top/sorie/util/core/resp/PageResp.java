package top.sorie.util.core.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.sorie.util.core.constant.HttpStatus;

import java.util.List;

import static top.sorie.util.core.constant.RespConstant.DEFAULT_RESP_ERR_MSG;

/**
 * 分页响应头
 * @author soriee
 * @return
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResp<T> {

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
    private List<T> data;

    /**
     * 分页数据
     */
    private PageInfo pageInfo;

    /**
     * 成功响应
     * @author soriee
     * @date 2021/10/21 22:19
     * @param data
     * @param pageInfo
     * @return
     */
    public static <T> PageResp<T> ok(List<T> data, PageInfo pageInfo) {
        return PageResp.<T>builder()
                .code(HttpStatus.HTTP_OK)
                .data(data)
                .pageInfo(pageInfo)
                .build();
    }

    /**
     * 成功响应
     * @author soriee
     * @date 2021/10/21 22:22
     * @param data
     * @param pageInfo
     * @param msg
     * @return
     */
    public static <T> PageResp<T> ok(List<T> data, PageInfo pageInfo, String msg) {
        return PageResp.<T>builder()
                .code(HttpStatus.HTTP_OK)
                .data(data)
                .pageInfo(pageInfo)
                .msg(msg)
                .build();
    }

    /**
     * 失败响应
     * @author soriee
     * @date 2021/10/21 22:19
     * @return
     */
    public static <T> PageResp<T> fail() {
        return PageResp.<T>builder()
                .code(HttpStatus.HTTP_INTERNAL_ERROR)
                .msg(DEFAULT_RESP_ERR_MSG).build();
    }

    /**
     * 失败响应
     * @author soriee
     * @date 2021/10/21 22:22
     * @param msg
     * @return
     */
    public static <T> PageResp<T> fail(String msg) {
        return PageResp.<T>builder()
                .code(HttpStatus.HTTP_INTERNAL_ERROR)
                .msg(msg)
                .build();
    }

    /**
     * 失败响应
     * @author soriee
     * @date 2021/10/21 22:22
     * @param msg
     * @param code
     * @return
     */
    public static <T> PageResp<T> fail(String msg, Integer code) {
        return PageResp.<T>builder()
                .code(code)
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
     * @param pageInfo
     * @return
     */
    public static <T> PageResp<T> resp(List<T> data, String msg, Integer code, PageInfo pageInfo) {
        return PageResp.<T>builder()
                .code(code)
                .data(data)
                .msg(msg)
                .pageInfo(pageInfo)
                .build();
    }
}
