package com.stylefeng.guns.core.util;

/**
 * @author Jerry
 **/

// 令牌桶
public class TokenBucket {
    private int bucketNum = 100;    // 令牌数量
    private int rate = 1;           // 流入的速率
    private int nowTokens;         // 当前令牌数量
    private long timestamp = getNowTime();        // 时间

    private long getNowTime() {
        return System.currentTimeMillis();
    }


    private int min(int tokens) {
        // 当令牌自动增长到桶的容量时 返回桶的最大容量，而不是继续增长
        if (bucketNum > tokens) {
            return tokens;
        } else {
            return bucketNum;
        }
    }

    public boolean getToken() {
        // 记录请求来拿令牌的时间
        long nowTime = getNowTime();
        // 添加令牌，判断该有多少令牌
        nowTokens = nowTokens + ((int) ((nowTime - timestamp) * rate));
        nowTokens = min(nowTokens);
        System.out.println("当前令牌数量" + nowTokens);
        timestamp = nowTime;
        // 判断令牌是否足够
        if (nowTokens < 1) {
            return false;
        } else {
            nowTokens -= 1;
            return true;
        }
    }


}

