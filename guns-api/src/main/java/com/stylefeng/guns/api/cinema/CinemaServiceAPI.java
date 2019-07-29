package com.stylefeng.guns.api.cinema;


import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.vo.*;

import java.util.List;

public interface CinemaServiceAPI {
    // 查询影院列表
    Page<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO);
    // 获取影院品牌列表
    List<BrandVO> getBrands(int brandId);
    // 获取行政区域列表  【除了99以外 其他的数字为isActive】
    List<AreaVO> getAreas(int areaId);
    // 获取影厅类型列表
    List<HallTypeVO> getHallTypes(int hallType);
    // 根据影院编号获取影院信息
    CinemaInfoVO getCinemaInfoById(int cinemaId);
    // 根据影院编号获取所有电影的信息和对应放映场次信息
    List<FilmInfoVO> getFilmInfoById(int cinemaId);
    // 根据放映场次Id获取放映信息
    HallInfoVO getFilmFieldInfo(int fieldId);
    // 根据放映场次查询播放的电影编号，然后根据电影编号获取电影信息
    FilmInfoVO getFilmInfoByFieldId(int fieldId);

    /*
    该部分是订单模块需要的内容
     */
    OrderQueryVO getOrderNeeds(int fieldId);
}
