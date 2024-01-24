package com.dazhou.chatroom.common.user.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 单聊房间表
 * </p>
 *
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @since 2024-01-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("room_friend")
public class RoomFriend implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 房间id
     */
    @TableField("room_id")
    private Long roomId;

    /**
     * uid1（更小的uid）
     */
    @TableField("uid1")
    private Long uid1;

    /**
     * uid2（更大的uid）
     */
    @TableField("uid2")
    private Long uid2;

    /**
     * 房间key由两个uid拼接，先做排序uid1_uid2
     */
    @TableField("room_key")
    private String roomKey;

    /**
     * 房间状态 0正常 1禁用(删好友了禁用)
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


}
