package com.stylefeng.guns.api.order;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.order.vo.OrderVO;

import java.util.List;

public interface OrderServiceAPI {

    // 验证售出的票是否为真
    boolean isTrueSeats(String fieldId,String seats);

    // 已经销售的座位里有无这些座位
    boolean isNotSoldSeats(String fieldId,String seats);

    // 创建订单信息
    OrderVO saveOrderInfo(Integer fieldId, String soldSeats, String seatsName,Integer userId);

    // 获取当前登录用户已经购买的订单
    Page<OrderVO> getOrdersByUserId(Integer userId, Page<OrderVO> page);

    // 根据fieldId获取所有已经销售的座位编号
    String getSoldSeatsByFieldId(Integer fieldId);

    // 根据orderId获取订单信息
    OrderVO getOrderInfoById(String orderId);

    // 修改订单状态
    boolean paySuccess(String orderId);
    boolean payFail(String orderId);
}
