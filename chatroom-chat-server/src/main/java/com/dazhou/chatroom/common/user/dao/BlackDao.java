package com.dazhou.chatroom.common.user.dao;

import com.dazhou.chatroom.common.user.domain.entity.Black;
import com.dazhou.chatroom.common.user.mapper.BlackMapper;
import com.dazhou.chatroom.common.user.service.IBlackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 黑名单 服务实现类
 * </p>
 *
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @since 2024-01-14
 */
@Service
public class BlackDao extends ServiceImpl<BlackMapper, Black>  {

}
