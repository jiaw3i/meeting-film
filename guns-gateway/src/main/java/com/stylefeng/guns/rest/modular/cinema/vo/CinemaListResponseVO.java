package com.stylefeng.guns.rest.modular.cinema.vo;

import com.stylefeng.guns.api.cinema.vo.CinemaVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 佛祖保佑    永无BUG
 * @author Jerry
 **/
@Data
public class CinemaListResponseVO implements Serializable {
    private List<CinemaVO> cinemas;

}
