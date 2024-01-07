package com.dazhou.chatroom.common.user.domain.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-07 22:47
 */
@Data
public class UserInfoResp {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "用户名称")
    private String name;
    @ApiModelProperty(value = "用户名称")
    private String avatar;
    @ApiModelProperty(value = "性别")
    private Integer sex;
    @ApiModelProperty(value = "修改名称次数")
    private Integer modifyNameChance;
}
