package com.tingfeng.ssm.controller;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.tingfeng.ssm.exception.CustomException;
import com.tingfeng.ssm.po.Items;
import com.tingfeng.ssm.po.ItemsCustom;
import com.tingfeng.ssm.po.ItemsQueryVo;
import com.tingfeng.ssm.service.ItemsService;
import com.tingfeng.ssm.validation.ValiGroup1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The type Items controller.
 * 商品的controller
 */
@Controller
//窄化请求映射
//为了对商品进行分类管理，在这里可以定义根路径，最终访问的url是根路径+子路径
//比如"/queryItems"的路径就变成了/items/queryItems.action
@RequestMapping("/items")
public class ItemsController {
    //注入ItemsService
    @Autowired
    private ItemsService itemsService;

    //商品分类
    //itemTypes表示最终将方法的返回值放在request中的key
    //在页面上得到这个itemsType的数据（页面请求不到这个方法）
    @ModelAttribute("itemsTypes")
    public Map<String, String> getItemsType() {
        Map<String, String> itemTypes = new HashMap<>();
        itemTypes.put("101", "数码");
        itemTypes.put("102", "母婴");

        return itemTypes;
    }


    //商品查询
    //@RequestMapping("/queryItems")
    //限制Htt请求的方法
    @RequestMapping(value = "/queryItems", method = {RequestMethod.POST, RequestMethod.GET})
    //对于简单类型的参数绑定，@RequestParam(value = "id") Integer item_id)这是参数绑定，当不使用参数绑定的时候，必须要求形参与传入的参数名称一致，
    //才可以，否则必须进行参数绑定
    //required = true的意思是是传入的参数是必须的
    //defaultValue可以设置默认值，如果id参数没有传入，将默认值和形参绑定到一起
    public ModelAndView queryItems(HttpServletRequest request, @RequestParam(value = "id", required = true, defaultValue = "1") Integer item_id) throws Exception {

        //调用service查找数据库
        List<ItemsCustom> itemsList = itemsService.findItemsList(null);
        //返回ModelAndView
        ModelAndView modelAndView = new ModelAndView();
        //相当于request的setAttribute，在jsp页面中，通过itemsList获取数据
        modelAndView.addObject("itemsList", itemsList);
        //指定视图
        modelAndView.setViewName("items/itemsList");

        return modelAndView;

    }

    //商品信息修改页面展示
    @RequestMapping("/editItems")
    public ModelAndView editItems(Integer id) throws Exception {

        //调用service根据商品id查询商品信息
        ItemsCustom itemsCustom = itemsService.fingItemsById(id);

        //未知异常测试
//        int a = 1/0;

        //自定义异常测试
//        if (null == itemsCustom) {
//            throw new CustomException("该商品不存在");
//        }

        //返回的ModelAndView
        ModelAndView modelAndView = new ModelAndView();

        //将商品信息放入model中
        modelAndView.addObject("items", itemsCustom);

        //设置返回的修改商品的页面
        modelAndView.setViewName("items/editItems");
        return modelAndView;
    }


    //controller的返回值可以是string，三此时有个入参是model，可以向这个model中设置attribute
    //利用controller的返回string的方式来实现响应，效果和上边的方法一样
    @RequestMapping(value = "/editItems2", method = {RequestMethod.GET, RequestMethod.POST})
    public String editItems2(Model model) throws Exception {

        //调用service根据商品id查询商品信息
        //在这里先写死
        ItemsCustom itemsCustom = itemsService.fingItemsById(3);

//        //返回的ModelAndView
//        ModelAndView modelAndView = new ModelAndView();
//
//        //将商品信息放入model中
//        modelAndView.addObject("itemsCustom", itemsCustom);
//
//        //设置返回的修改商品的页面
//        modelAndView.setViewName("items/editItems");
        model.addAttribute("itemsCustom", itemsCustom);
        return "items/editItems";
    }


    //商品信息修改提交
    @RequestMapping("/editItemsSubmit")
    //按照serivce接口de要求，在这里添加参数
    //参数绑定是一个request过来，如果其中有名字和自己设置的参数名一直的参数，则进行绑定，所以，折设置String name，String detail什么的都可以
    //pojo的绑定和简单类型的绑定互不影响
    //在需要校验的pojo中添加 @Validated 注解，在需要校验的pojo后边添加BindingResult bindingResult来接收校验出错的信息
    //注意，如果要校验多个pojo， @Validated与BindingResult bindingResult是配对出现的，并且形参顺序是固定的（一前一后）
    //value = {ValiGroup1.class}制定使用ValiGroup1分组的校验
    //@ModelAttribute可以指定pojo回显到页面在request域中的key
    public String editItemsSubmit(Integer id, Model model,
                                  @ModelAttribute("items") @Validated(value = {ValiGroup1.class}) ItemsCustom itemsCustom, BindingResult bindingResult
            , MultipartFile items_pic// 接收商品图片
    ) throws Exception {

        //调用service更新商品信息，页面需要将商品传到此方法
        //.....

        //获取校验错误信息
        if (bindingResult.hasErrors()) {
            //输出错误信息
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            //遍历
            for (ObjectError objectError :
                    allErrors) {
                //输出错误信息
                String string = objectError.getDefaultMessage();
                System.out.println(string);
            }
            //将错误信息传到页面
            model.addAttribute("allErrors", allErrors);

            //最简单的数据回显方式是使用model
            model.addAttribute("items", itemsCustom);

            //返回商品信息修改页面
            return "items/editItems";
        }

        //上传图片

        //获取图片原始名称
        String originFileName = items_pic.getOriginalFilename();

        if (items_pic != null && null != originFileName && originFileName.length() > 0) {
            //存储路径(物理路径)
            String pic_path = "D:/temp";
            //新的图片名称
            String newFileName = UUID.randomUUID() + originFileName.substring(originFileName.lastIndexOf("."));
            //新的图片
            File newFile = new File(pic_path + "/" + newFileName);
            //将内存中的数据写入磁盘
            items_pic.transferTo(newFile);

            //上传成功，则将新的图片名称写到itemsCustom中
            itemsCustom.setPic(newFileName);
        } else {
            //在这里是为了防止将图片地址写成空，覆盖原有的图片
            itemsCustom.setPic(itemsService.fingItemsById(id).getPic());
        }


        //在这里。写控制器的人根本不知道前边的页面传入什么参数，只是拿到service接口发现需要这么两个参数，才在上面进行添加
        itemsService.updateItems(id, itemsCustom);
//        System.out.println(itemsCustom);
//        String details = request.getParameter("id");
        //返回的ModelAndView
        ModelAndView modelAndView = new ModelAndView();

        //返回一个成功页面
        modelAndView.setViewName("success");
//        return modelAndView;
        return "success";
    }

    //返回值为String时的重定向
    //重定向到商品的查询列表
    @RequestMapping("/redirectToItemsList")
    public String redirectToItemsList() throws Exception {

        return "redirect:queryItems.action";
    }

    //返回值为String时的请求转发
    //请求转发到商品的查询列表
    //需要传入一个参数HttpServletRequest
    @RequestMapping("/forwardToItemsList")
    public String forwardToItemsList(HttpServletRequest request) throws Exception {

        //此时的request是可以共享的
        return "forward:queryItems.action";
    }


    //包装pojo类型参数绑定
    //jsp页面中的商品名称定义name="itemsCustom.name"
    @RequestMapping(value = "/queryItemsByName")
    public ModelAndView queryItemsByName(ItemsQueryVo itemsQueryVo) throws Exception {
        List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("itemsList", itemsList);
        modelAndView.setViewName("items/itemsList");
        return modelAndView;
    }

    //批量删除商品信息
    @RequestMapping(value = "deleteItems")
    public String deleteItems(int[] items_id) throws Exception {
        //调用service中的删除方法

        for (int id : items_id) {
            System.out.println(id);
        }
        return "success";
    }

    //批量修改商品页面，将商品信息查询出来，在页面中可以编辑商品信息
    @RequestMapping(value = "editItemsQuery")
    public ModelAndView editItemsQuery(ItemsQueryVo itemsQueryVo) throws Exception {
        List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("itemsList", itemsList);
        modelAndView.setViewName("items/editItemsQuery");
        return modelAndView;
    }


    //批量修改商品提交
    //通过ItemsQueryVo接收批量提交的商品信息，将商品信息存储到itemsQueryVo中的itemsList中
    @RequestMapping(value = "/editItemsAllSubmit")
    public String editItemsAllSubmit(ItemsQueryVo itemsQueryVo) throws Exception {

        return "success";
    }

    //RESTful风格实现的商品信息查询，返回json串
    @RequestMapping(value = "/itemsView/{id}/{type}")
    //@PathVariable("id")表示将"/itemsView/{id}/{type}"中的参数{id}传到其定义的形参后的变量itemsId中
    public @ResponseBody ItemsCustom itemsView(@PathVariable("id") Integer itemsId, @PathVariable("type") String itemsType) throws Exception {
        ItemsCustom itemsCustom = itemsService.fingItemsById(itemsId);
        System.out.println(itemsType);
        return itemsCustom;
    }
}



