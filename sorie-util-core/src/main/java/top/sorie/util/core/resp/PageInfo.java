package top.sorie.util.core.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageInfo {
    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 总条数
     */
    private Long totals;

    /**
     * 当前页
     */
    private Integer pageIndex = 1;

    /**
     * 页大小
     */
    private Integer pageSize = 10;
}
