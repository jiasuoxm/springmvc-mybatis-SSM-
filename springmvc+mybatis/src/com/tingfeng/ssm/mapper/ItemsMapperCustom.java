package com.tingfeng.ssm.mapper;

import com.tingfeng.ssm.po.Items;
import com.tingfeng.ssm.po.ItemsCustom;
import com.tingfeng.ssm.po.ItemsExample;
import com.tingfeng.ssm.po.ItemsQueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemsMapperCustom {
//    商品查询列表
    List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception;

}