package com.stylefeng.guns.api.film.vo;

import com.stylefeng.guns.api.film.vo.ImgVO;
import lombok.Data;

import java.io.Serializable;

/**
 * 佛祖保佑    永无BUG
 *
 * @author Jerry
 **/
@Data
public class InfoRequestVO implements Serializable {
    private String biography;
    private ActorRequestVO actors;
    private ImgVO imgs;
    private String filmId;
}
