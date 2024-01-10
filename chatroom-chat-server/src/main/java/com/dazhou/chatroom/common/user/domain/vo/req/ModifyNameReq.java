package com.dazhou.chatroom.common.user.domain.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-10 21:39
 */
@Data
public class ModifyNameReq {
    @ApiModelProperty(value = "用户名")
    @NotBlank
    @Length(max =6,message = "用户名不可太长")
    private String name;
}
