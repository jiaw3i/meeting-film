package com.stylefeng.guns.api.film;

import com.stylefeng.guns.api.film.vo.*;

import java.util.List;

/**
 * 佛祖保佑    永无BUG
 *
 * @author Jerry
 **/
public interface FilmAsyncServiceAPI {

    // 根据影片Id或者名称获取影片信息
//    FilmDetailVO getFilmDetail(int searchType, String searchParam);

    // 获取影片相关的其他信息 [演员表，图片地址]
    // 获取影片描述信息
    FilmDescVO getFilmDesc(String filmId);
    // 获取图片信息
    ImgVO getImgs(String filmId);
    // 获取导演信息
    ActorVO getDirectorInfo(String filmId);
    // 获取演员信息
    List<ActorVO> getActors(String filmId);

}
