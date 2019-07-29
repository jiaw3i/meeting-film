package com.stylefeng.guns.rest.modular.cinema.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.vo.*;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 佛祖保佑    永无BUG
 *
 * @author Jerry
 **/
@Component
@Service(interfaceClass = CinemaServiceAPI.class, filter = "tracing")
public class DefaultCinemaServiceImpl implements CinemaServiceAPI {

    @Autowired
    private MoocCinemaTMapper moocCinemaTMapper;
    @Autowired
    private MoocAreaDictTMapper moocAreaDictTMapper;
    @Autowired
    private MoocBrandDictTMapper moocBrandDictTMapper;
    @Autowired
    private MoocHallDictTMapper moocHallDictTMapper;
    @Autowired
    private MoocHallFilmInfoTMapper moocHallFilmInfoTMapper;
    @Autowired
    private MoocFieldTMapper moocFieldTMapper;

    // 查询影院列表
    public Page<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO) {
        // 业务实体
        List<CinemaVO> cinemas = new ArrayList<>();
        Page<MoocCinemaT> page = new Page<>(cinemaQueryVO.getNowPage(), cinemaQueryVO.getPageSize());
        EntityWrapper<MoocCinemaT> entityWrapper = new EntityWrapper<>();
        // 判断是否传入查询条件 --->  brandId,distId,hallType是否等于99
        if (cinemaQueryVO.getBrandId() != 99) {
            entityWrapper.eq("brand_id", cinemaQueryVO.getBrandId());
        }
        if (cinemaQueryVO.getDistrictId() != 99) {
            entityWrapper.eq("area_id", cinemaQueryVO.getDistrictId());
        }
        if (cinemaQueryVO.getHallType() != 99) {
            entityWrapper.like("hall_ids", "%#" + cinemaQueryVO.getHallType() + "#%");
        }
        // 将数据实体转换为业务实体
        List<MoocCinemaT> moocCinemaTS = moocCinemaTMapper.selectPage(page, entityWrapper);
        for (MoocCinemaT moocCinemaT : moocCinemaTS) {
            CinemaVO cinemaVO = new CinemaVO();
            cinemaVO.setAddress(moocCinemaT.getCinemaAddress());
            cinemaVO.setCinemaName(moocCinemaT.getCinemaName());
            cinemaVO.setMinimumPrice(moocCinemaT.getMinimumPrice() + "");
            cinemaVO.setUuid(moocCinemaT.getUuid() + "");
            cinemas.add(cinemaVO);
        }
        // 根据条件，判断影院列表总数
        Integer count = moocCinemaTMapper.selectCount(entityWrapper);
        // 组织返回值对象
        Page<CinemaVO> result = new Page<>();
        result.setRecords(cinemas);
        result.setSize(cinemaQueryVO.getPageSize());
        result.setTotal(count);

        return result;
    }

    // 获取影院品牌列表
    public List<BrandVO> getBrands(int brandId) {
        boolean flag = false;
        // 判断brandId是否存在
        MoocBrandDictT moocBrandDictT = moocBrandDictTMapper.selectById(brandId);
        // 判断brandId是否等于99 若等于99则查询所有  若不等于99则只查询brandId的品牌

        if (brandId == 99 || moocBrandDictT == null || moocBrandDictT.getUuid() == null) {
            flag = true;
        }
        List<MoocBrandDictT> moocBrandDictTS = moocBrandDictTMapper.selectList(null);
        // 组织返回值对象
        List<BrandVO> result = new ArrayList<>();
        for (MoocBrandDictT brandDictT : moocBrandDictTS) {
            BrandVO brandVO = new BrandVO();
            brandVO.setBrandId(brandDictT.getUuid() + "");
            brandVO.setBrandName(brandDictT.getShowName());
            if (flag) {
                if (brandDictT.getUuid() == 99) {
                    brandVO.setActive(true);
                }
            } else {
                if (brandDictT.getUuid() == brandId) {
                    brandVO.setActive(true);
                }
            }
            result.add(brandVO);
        }
        return result;
    }

    // 获取行政区域列表  【除了99以外 其他的数字为isActive】
    public List<AreaVO> getAreas(int areaId) {
        boolean flag = false;
        // 判断areaId是否存在
        MoocAreaDictT moocAreaDictT = moocAreaDictTMapper.selectById(areaId);
        // 判断areaId是否等于99 若等于99则查询所有  若不等于99则只查询brandId的品牌

        if (areaId == 99 || moocAreaDictT == null || moocAreaDictT.getUuid() == null) {
            flag = true;
        }
        List<MoocAreaDictT> moocAreaDictTS = moocAreaDictTMapper.selectList(null);
        // 组织返回值对象
        List<AreaVO> result = new ArrayList<>();
        for (MoocAreaDictT areaDictT : moocAreaDictTS) {
            AreaVO areaVO = new AreaVO();
            areaVO.setAreaId(areaDictT.getUuid() + "");
            areaVO.setAreaName(areaDictT.getShowName());
            if (flag) {
                if (areaDictT.getUuid() == 99) {
                    areaVO.setActive(true);
                }
            } else {
                if (areaDictT.getUuid() == areaId) {
                    areaVO.setActive(true);
                }
            }
            result.add(areaVO);
        }
        return result;
    }

    // 获取影厅类型列表
    public List<HallTypeVO> getHallTypes(int hallType) {
        boolean flag = false;
        // 判断hallType是否存在
        MoocHallDictT moocHallDictT = moocHallDictTMapper.selectById(hallType);
        // 判断hallType是否等于99 若等于99则查询所有  若不等于99则只查询hallType的品牌

        if (hallType == 99 || moocHallDictT == null || moocHallDictT.getUuid() == null) {
            flag = true;
        }
        List<MoocHallDictT> moocHallDictTS = moocHallDictTMapper.selectList(null);
        // 组织返回值对象
        List<HallTypeVO> result = new ArrayList<>();
        for (MoocHallDictT hallDictT : moocHallDictTS) {
            HallTypeVO hallTypeVO = new HallTypeVO();
            hallTypeVO.setHalltypeId(hallDictT.getUuid() + "");
            hallTypeVO.setHalltypeName(hallDictT.getShowName());
            if (flag) {
                if (hallDictT.getUuid() == 99) {
                    hallTypeVO.setActive(true);
                }
            } else {
                if (hallDictT.getUuid() == hallType) {
                    hallTypeVO.setActive(true);
                }
            }
            result.add(hallTypeVO);
        }
        return result;
    }

    // 根据影院编号获取影院信息
    public CinemaInfoVO getCinemaInfoById(int cinemaId) {
        // 数据实体
        CinemaInfoVO cinemaInfoVO = new CinemaInfoVO();
        MoocCinemaT moocCinemaT = moocCinemaTMapper.selectById(cinemaId);
        // 将数据实体转换为业务实体
        cinemaInfoVO.setCinemaAddress(moocCinemaT.getCinemaAddress());
        cinemaInfoVO.setImgUrl(moocCinemaT.getImgAddress());
        cinemaInfoVO.setCinemaPhone(moocCinemaT.getCinemaPhone());
        cinemaInfoVO.setCinemaName(moocCinemaT.getCinemaName());
        cinemaInfoVO.setCinemaId(moocCinemaT.getUuid() + "");
        return cinemaInfoVO;
    }

    // 根据影院编号获取所有电影的信息和对应放映场次信息
    public List<FilmInfoVO> getFilmInfoById(int cinemaId) {
        return moocFieldTMapper.getFilmInfos(cinemaId);
    }

    // 根据放映场次Id获取放映信息
    public HallInfoVO getFilmFieldInfo(int fieldId) {
        return moocFieldTMapper.getHallInfo(fieldId);

    }

    // 根据放映场次查询播放的电影编号，然后根据电影编号获取电影信息
    public FilmInfoVO getFilmInfoByFieldId(int fieldId) {
        return moocFieldTMapper.getFilmInfo(fieldId);
    }

    @Override
    public OrderQueryVO getOrderNeeds(int fieldId) {
        OrderQueryVO orderQueryVO = new OrderQueryVO();
        MoocFieldT moocFieldT = moocFieldTMapper.selectById(fieldId);
        orderQueryVO.setCinemaId(moocFieldT.getCinemaId() + "");
        orderQueryVO.setFilmPrice(moocFieldT.getPrice() + "");
        return orderQueryVO;
    }
}
