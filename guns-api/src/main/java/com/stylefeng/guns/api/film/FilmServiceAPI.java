package com.stylefeng.guns.api.film;

import com.stylefeng.guns.api.film.vo.*;

import java.util.List;

/**
 * 佛祖保佑    永无BUG
 *
 * @author Jerry
 **/
public interface FilmServiceAPI {


    //获取banners
    List<BannerVO> getBanners();

    // 获取热映影片
    FilmVO getHotFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId);

    // 获取即将上映影片[按照受欢迎程度排序]
    FilmVO getSoonFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId);

    // 获取经典影片
    FilmVO getClassicFilms(int nums, int nowPage, int sortId, int sourceId, int yearId, int catId);



    //获取票房排行榜
    List<FilmInfo> getBoxRanking();

    // 获取人气排行榜
    List<FilmInfo> getExceptRanking();

    // 获取Top100
    List<FilmInfo> getTop();

    //==========================================================

    // 获取影片分类条件
    List<CatVO> getCats();

    // 片源
    List<SourceVO> getSources();

    // 年份分类
    List<YearVO> getYears();

    // 根据影片Id或者名称获取影片信息
    FilmDetailVO getFilmDetail(int searchType,String searchParam);

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
