package com.stylefeng.guns.api.film.vo;

import com.stylefeng.guns.api.film.vo.ActorVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 佛祖保佑    永无BUG
 *
 * @author Jerry
 **/
@Data
public class ActorRequestVO implements Serializable{
    private ActorVO director;
    private List<ActorVO> actors;
}
