package com.qpp.admin.mapper.system;


import com.qpp.admin.controller.view.ChannelAccountView;
import com.qpp.admin.entity.system.DataChild;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by maping on 2018/6/28.
 */
@Repository
public interface DataChildMapper {

    List<DataChild> getContent(int parentId);

    int insertSelective(DataChild dataChild);

    int updateByPrimaryKey(DataChild dataChild);

    List<DataChild> selectByParentId(DataChild dataChild);

    List<DataChild> getAppMarketList();


    List<ChannelAccountView> getChannelAccountList(ChannelAccountView channelAccountParam);
    Integer selectcount(@Param("name") String name, @Param("identify") String identify);

}
