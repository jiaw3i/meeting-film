package com.stylefeng.guns.rest.modular.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 佛祖保佑    永无BUG
 *
 * @author Jerry
 **/

@Data
public class FilmRequestVO implements Serializable {
    private Integer showType = 1;     // 查询类型  1.正在热映 2. 即将上映  3.经典影片
    private Integer sortId = 1;       // 排序方式  1. 按热门 2. 按时间 3.按评价
    private Integer catId = 99;        // 类型编号   默认99
    private Integer sourceId = 99;     // 来源地区编号 默认99
    private Integer yearId = 99;       // 年代编号  默认99
    private Integer nowPage = 1;      // 影片列表当前页 默认1
    private Integer pageSize = 18;     // 每页显示的条数 默认18

}
