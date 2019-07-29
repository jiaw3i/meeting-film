package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 佛祖保佑    永无BUG
 *
 * @author Jerry
 **/

@Data
public class FilmVO implements Serializable {

    private int filmNum;
    private List<FilmInfo> filmInfo;
    private int nowPage;
    private int totalPage;
}
