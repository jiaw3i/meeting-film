package com.stylefeng.guns.rest.modular.order.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.vo.FilmInfoVO;
import com.stylefeng.guns.api.cinema.vo.OrderQueryVO;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.api.order.vo.OrderVO;
import com.stylefeng.guns.core.util.UUIDUtil;
import com.stylefeng.guns.rest.common.persistence.dao.MoocOrder2017TMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MoocOrder2017TMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocOrder2017T;
import com.stylefeng.guns.rest.common.persistence.model.MoocOrder2017T;
import com.stylefeng.guns.rest.common.persistence.model.MoocOrderT;
import com.stylefeng.guns.rest.common.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jerry
 **/
@Component
@Slf4j
@Service(interfaceClass = OrderServiceAPI.class, group = "order2017")
public class OrderServiceImpl2017 implements OrderServiceAPI {


    @Autowired
    private MoocOrder2017TMapper moocOrder2017TMapper;

    @Reference(interfaceClass = CinemaServiceAPI.class, check = false)
    private CinemaServiceAPI cinemaServiceAPI;
    @Autowired
    private FTPUtil ftpUtil;

    // 验证是否为真实的座位编号
    @Override
    public boolean isTrueSeats(String fieldId, String seats) {
        // 根据fieldId找到对应的位置图
        String seatPath = moocOrder2017TMapper.getSeatsByFieldId(fieldId);
        // 读取位置图 判断seats是否为真
        String fileStrByAddress = ftpUtil.getFileStrByAddress(seatPath);

        // 将fileStrByAddress转换为Json对象
        JSONObject jsonObject = JSONObject.parseObject(fileStrByAddress);
        String ids = jsonObject.get("ids").toString();

        String[] idsArray = ids.split(",");
        String[] seatsArray = seats.split(",");

        int isTrue = 0;
        for (String seat : seatsArray) {
            for (String id : idsArray) {
                if (seat.equalsIgnoreCase(id)) {
                    isTrue++;
                    break;
                }
            }
        }
        return isTrue == seatsArray.length;


    }


    // 判断是否为已售座位
    @Override
    public boolean isNotSoldSeats(String fieldId, String seats) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("field_id", fieldId);
        List<MoocOrder2017T> list = moocOrder2017TMapper.selectList(entityWrapper);
        String[] seatArray = seats.split(",");
        for (MoocOrder2017T moocOrder2017T : list) {
            String[] idArray = moocOrder2017T.getSeatsIds().split(",");
            for (String id : idArray) {
                for (String seat : seatArray) {
                    if (id.equalsIgnoreCase(seat)) {
                        return false;
                    }
                }
            }

        }
        return true;
    }

    // 创建订单 保存订单信息
    @Override
    public OrderVO saveOrderInfo(Integer fieldId, String soldSeats, String seatsName, Integer userId) {
        // 编号
        String uuid = UUIDUtil.genUuid();
        // 影片信息
        FilmInfoVO filmInfoByFieldId = cinemaServiceAPI.getFilmInfoByFieldId(fieldId);
        int filmId = Integer.parseInt(filmInfoByFieldId.getFilmId());
        // 获取影院信息
        OrderQueryVO orderNeeds = cinemaServiceAPI.getOrderNeeds(fieldId);
        int cinemaId = Integer.parseInt(orderNeeds.getCinemaId());
        Double filmPrice = Double.parseDouble(orderNeeds.getFilmPrice());
        // 求出订单总额
        int seatNum = soldSeats.split(",").length;
        Double totalPrice = getTotalPrice(seatNum, filmPrice);
        // 构建订单表实体
        MoocOrder2017T moocOrder2017T = new MoocOrder2017T();
        moocOrder2017T.setCinemaId(cinemaId);
        moocOrder2017T.setUuid(uuid);
        moocOrder2017T.setSeatsName(seatsName);
        moocOrder2017T.setSeatsIds(soldSeats);
        moocOrder2017T.setOrderUser(userId);
        moocOrder2017T.setOrderPrice(totalPrice);
        moocOrder2017T.setFilmPrice(filmPrice);
        moocOrder2017T.setFilmId(filmId);
        moocOrder2017T.setFieldId(fieldId);
        Integer insert = moocOrder2017TMapper.insert(moocOrder2017T);
        if (insert > 0) {
            // 返回查询结果
            // 构建返回实体
            OrderVO orderVO = moocOrder2017TMapper.getOrderInfoById(uuid);
            if (orderVO == null || orderVO.getOrderId() == null) {
                log.error("订单编号{}的信息查询失败", uuid);
                return null;
            }
            return orderVO;
        } else {
            // 返回插入出错的信息
            log.error("订单插入失败！");
            return null;
        }
    }

    private Double getTotalPrice(int seatNum, double filmPrice) {
        BigDecimal bigDecimal = new BigDecimal(seatNum);
        BigDecimal bigDecimal1 = new BigDecimal(filmPrice);
        BigDecimal result = bigDecimal.multiply(bigDecimal1);
        // 四舍五入 取小数点后两位
        BigDecimal bigDecimal2 = result.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal2.doubleValue();
    }

    @Override
    public Page<OrderVO> getOrdersByUserId(Integer userId, Page<OrderVO> page) {

        Page<OrderVO> result = new Page<>();
        if (userId == null) {
            log.error("用户编号未传入");
            return null;
        } else {
            List<OrderVO> orderInfoByUserId = moocOrder2017TMapper.getOrderInfoByUserId(userId, page);
            if (orderInfoByUserId == null || orderInfoByUserId.size() == 0) {
                log.error("没有查询到相关订单信息");
                result.setTotal(0);
                result.setRecords(new ArrayList<>());
                return result;
            } else {
                // 获取订单总数
                EntityWrapper<MoocOrder2017T> entityWrapper = new EntityWrapper<>();
                entityWrapper.eq("order_user", userId);
                Integer counts = moocOrder2017TMapper.selectCount(entityWrapper);
                // 将结果放入page
                result.setTotal(counts);
                result.setRecords(orderInfoByUserId);
                return result;
            }
        }
    }

    // 根据放映场次获取所有的已售座位
    @Override
    public String getSoldSeatsByFieldId(Integer fieldId) {
        if (fieldId == null) {
            log.error("未传入任何场次编号");
            return "";
        } else {
            return moocOrder2017TMapper.getSoldSeatsByFieldId(fieldId);
        }
    }

    @Override
    public OrderVO getOrderInfoById(String orderId) {

        return moocOrder2017TMapper.getOrderInfoById(orderId);
    }

    @Override
    public boolean paySuccess(String orderId) {
        MoocOrder2017T moocOrder2017T = new MoocOrder2017T();
        moocOrder2017T.setUuid(orderId);
        moocOrder2017T.setOrderStatus(1);   // 0 正在支付  1 支付成功   2 支付失败
        Integer integer = moocOrder2017TMapper.updateById(moocOrder2017T);
        return integer >= 1;
    }

    @Override
    public boolean payFail(String orderId) {
        MoocOrder2017T moocOrder2017T = new MoocOrder2017T();
        moocOrder2017T.setUuid(orderId);
        moocOrder2017T.setOrderStatus(2);   // 0 正在支付  1 支付成功   2 支付失败
        Integer integer = moocOrder2017TMapper.updateById(moocOrder2017T);
        return integer >= 1;
    }
}
