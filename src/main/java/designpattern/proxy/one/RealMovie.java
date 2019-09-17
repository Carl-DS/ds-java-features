package designpattern.proxy.one;

/**
 * @author duosheng
 * @since 2019/9/17
 */
public class RealMovie implements Movie {
    /**
     * 电影播放
     */
    @Override
    public void play() {
        System.out.println("您正在观看电影 《肖申克的救赎》");
    }
}
