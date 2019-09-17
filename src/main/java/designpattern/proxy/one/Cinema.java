package designpattern.proxy.one;

/**
 * @author duosheng
 * @since 2019/9/17
 */
public class Cinema implements Movie {

    private RealMovie realMovie;

    public Cinema(RealMovie realMovie) {
        this.realMovie = realMovie;
    }

    /**
     * 电影播放
     */
    @Override
    public void play() {
        guangGao(true);
        realMovie.play();
        guangGao(false);
    }

    private void guangGao(boolean isStart) {
        if (isStart) {
            System.out.println("电影马上开始了，爆米花、可乐、口香糖9.8折，快来买啊！");
        } else {
            System.out.println("电影马上结束了，爆米花、可乐、口香糖9.8折，买回家吃吧！");
        }
    }
}
