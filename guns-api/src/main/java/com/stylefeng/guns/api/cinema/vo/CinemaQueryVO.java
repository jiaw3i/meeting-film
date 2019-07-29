package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 佛祖保佑    永无BUG
 *
 * @author Jerry
 **/
@Data
public class CinemaQueryVO implements Serializable {

    private Integer brandId = 99;
    private Integer districtId = 99;
    private Integer hallType = 99;
    private Integer pageSize = 12;
    private Integer nowPage = 1;

}
