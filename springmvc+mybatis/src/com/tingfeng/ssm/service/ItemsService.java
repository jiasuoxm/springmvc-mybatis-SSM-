package com.tingfeng.ssm.service;

import com.tingfeng.ssm.po.ItemsCustom;
import com.tingfeng.ssm.po.ItemsQueryVo;

import java.util.List;

/**
 * The type Items service.
 * 商品管理的service
 */
public interface ItemsService {
    /**
     * Find items list list.
     *
     * @param itemsQueryVo the items query vo
     * @return the list
     * @throws Exception the exception
     */
//商品的查询列表
    public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception;

    /**
     * Fing items by id items custom.
     *
     * @param id the id
     * @return the items custom
     * @throws Exception the exception
     */
//根据id查询商品信息
    public ItemsCustom fingItemsById(Integer id) throws Exception;

    /**
     * Update items.
     * 根据id与传入的商品扩展类修改商品
     *
     * @param id          修改商品的id
     * @param itemsCustom 修改的商品信息
     * @throws Exception the exception
     */
//修改商品信息
    public void updateItems(Integer id, ItemsCustom itemsCustom) throws Exception;
}
