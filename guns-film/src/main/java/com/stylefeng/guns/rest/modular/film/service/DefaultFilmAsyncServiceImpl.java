package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.film.FilmAsyncServiceAPI;
import com.stylefeng.guns.api.film.FilmServiceAPI;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.core.util.DateUtil;
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
@Service(interfaceClass = FilmAsyncServiceAPI.class)
public class DefaultFilmAsyncServiceImpl implements FilmAsyncServiceAPI {

    @Autowired
    MoocFilmInfoTMapper moocFilmInfoTMapper;
    @Autowired
    MoocActorTMapper moocActorTMapper;



    private MoocFilmInfoT getFilmInfo(String filmId) {

        MoocFilmInfoT moocFilmInfoT = new MoocFilmInfoT();
        moocFilmInfoT.setFilmId(filmId);
        moocFilmInfoT = moocFilmInfoTMapper.selectOne(moocFilmInfoT);


        return moocFilmInfoT;
    }

    @Override
    public FilmDescVO getFilmDesc(String filmId) {
        MoocFilmInfoT filmInfo = getFilmInfo(filmId);
        FilmDescVO filmDescVO = new FilmDescVO();
        filmDescVO.setBiography(filmInfo.getBiography());
        filmDescVO.setFilmId(filmId);
        return filmDescVO;
    }

    @Override
    public ImgVO getImgs(String filmId) {
        MoocFilmInfoT filmInfo = getFilmInfo(filmId);
        ImgVO imgVO = new ImgVO();
        String filmImgs = filmInfo.getFilmImgs();
        String[] imgs = filmImgs.split(",");
        imgVO.setMainImg(imgs[0]);
        imgVO.setImg01(imgs[1]);
        imgVO.setImg02(imgs[2]);
        imgVO.setImg03(imgs[3]);
        imgVO.setImg04(imgs[4]);
        return imgVO;
    }

    @Override
    public ActorVO getDirectorInfo(String filmId) {
        MoocFilmInfoT filmInfo = getFilmInfo(filmId);
        // 获取导演编号
        Integer directorId = filmInfo.getDirectorId();
        ActorVO actorVO = new ActorVO();
        MoocActorT moocActorT = moocActorTMapper.selectById(directorId);
        actorVO.setDirectorName(moocActorT.getActorName());
        actorVO.setImgAddress(moocActorT.getActorImg());

        return actorVO;
    }

    @Override
    public List<ActorVO> getActors(String filmId) {
        List<ActorVO> actors = moocActorTMapper.getActors(filmId);
        return actors;
    }
}
