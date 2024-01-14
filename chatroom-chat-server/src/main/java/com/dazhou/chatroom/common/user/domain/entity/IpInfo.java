package com.dazhou.chatroom.common.user.domain.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * ip详细信息
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-14 20:38
 */
@Data
public class IpInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    //注册时的ip
    private String createIp;
    //注册时的ip详情
    private IpDetail createIpDetail;
    //最新登录的ip
    private String updateIp;
    //最新登录的ip详情
    private IpDetail updateIpDetail;

    public void refreshIp(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return;
        }
        updateIp = ip;
        if (createIp == null) {
            createIp = ip;
        }
    }

    /**
     * 需要刷新的ip，这里判断更新ip就够，初始化的时候ip也是相同的，只需要设置的时候多设置进去就行
     *
     * @return
     */
    public String needRefreshIp() {
        //updateIpDetail有可能为空
        boolean notNeedRefresh = Optional.ofNullable(updateIpDetail)
                //不为空时 进行这些操作  满足这些条件则不用刷新
                .map(IpDetail::getIp)
                .filter(ip -> Objects.equals(ip, updateIp))
                .isPresent();
        return notNeedRefresh ? null : updateIp;
    }

    public void refreshIpDetail(IpDetail ipDetail) {
        if (Objects.equals(createIp, ipDetail.getIp())) {
            createIpDetail = ipDetail;
        }
        if (Objects.equals(updateIp, ipDetail.getIp())) {
            updateIpDetail = ipDetail;
        }
    }
}
