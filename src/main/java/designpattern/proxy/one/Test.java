package designpattern.proxy.one;

/**
 * @author duosheng
 * @since 2019/9/17
 */
public class Test {

    public static void main(String[] args) {
        RealMovie realMovie = new RealMovie();
        Movie movie = new Cinema(realMovie);
        movie.play();
    }
}
