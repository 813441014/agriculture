package com.qpp.admin.mapper.system;

import com.qpp.admin.entity.system.DataParent;
import org.springframework.stereotype.Component;

/**
 * Created by maping on 2018/6/28.
 */
@Component
public interface DataParentMapper {
    DataParent getId(String identify);
}
