package com.dazhou.chatroom.common.common.domain.vo.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-09 21:12
 */
@Data
@ApiModel("基础翻页请求")
public class PageBaseReq {

    @ApiModelProperty("页面大小")
    @Min(0)
    @Max(50)
    private Integer pageSize = 10;

    @ApiModelProperty("页面索引（从1开始）")
    private Integer pageNo = 1;

    /**
     * 获取mybatisPlus的page
     *
     * @return
     */
    public Page plusPage() {
        return new Page(pageNo, pageSize);
    }
}

