package com.tingfeng.ssm.service.impl;

import com.tingfeng.ssm.exception.CustomException;
import com.tingfeng.ssm.mapper.ItemsMapper;
import com.tingfeng.ssm.mapper.ItemsMapperCustom;
import com.tingfeng.ssm.po.Items;
import com.tingfeng.ssm.po.ItemsCustom;
import com.tingfeng.ssm.po.ItemsQueryVo;
import com.tingfeng.ssm.service.ItemsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * The type Items service.
 * 商品管理
 */
public class ItemsServiceImpl implements ItemsService {
    //  在这里直接使用自动注入标签是因为已经在spring的配置文件中配置了mapper扫描器，
//  所以在这里就可以直接使用了
    @Autowired
    private ItemsMapperCustom itemsMapperCustom;

    @Autowired
    private ItemsMapper itemsMapper;

    @Override
    public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception {
        return itemsMapperCustom.findItemsList(itemsQueryVo);
    }

    @Override
    public ItemsCustom fingItemsById(Integer id) throws Exception {
        Items items = itemsMapper.selectByPrimaryKey(id);

        //这种与业务功能相关的异常，建议在service层抛出
        //如果是出于安全性全局考虑的异常，如用户名不能为乱码什么的在controller层抛出
        if (null == items) {
            throw new CustomException("该商品不存在");
        }

        //这里为什么要这么麻烦呢？逆向工程生成的代码明明只返回一个Items的对象，我们在业务层却偏偏要
        //返回一个ItemsCustom，为什么呢？
        //因为我们这是业务层，中间对商品信息进行业务处理是非常正常的事情
        //...
        //最终返回一个ItemsCustom
        ItemsCustom itemsCustom = null;
        //将items中的内容拷贝到itemsCustom中
        if (null != items) {
            itemsCustom = new ItemsCustom();
            BeanUtils.copyProperties(items, itemsCustom);
        }

        return itemsCustom;
    }

    @Override
    public void updateItems(Integer id, ItemsCustom itemsCustom) throws Exception {
        //添加业务校验，通常在service接口对关键参数进行校验
        //校验id是否为空，如果为空抛出异常(所以说，在这里额int一般要写成Integer，这样才便于判断)


        //更新商品信息---使用updateByExampleWithBLOBs可以根据id更新items表中的所有字段，包括大文本类型字段
        //updateByPrimaryKeyWithBLOBs要求必须传入id，所以为了保证itemsCustom中有id，必须set进去
        itemsCustom.setId(id);
        itemsMapper.updateByPrimaryKeyWithBLOBs(itemsCustom);
    }
}
