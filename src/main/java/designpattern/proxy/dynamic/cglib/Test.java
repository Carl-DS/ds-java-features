package designpattern.proxy.dynamic.cglib;

import org.springframework.cglib.proxy.Enhancer;

/**
 * @author duosheng
 * @since 2019/9/17
 */
public class Test {

    public static void main(String[] args) {
        Cinema cinemaCglibProxy = new Cinema();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Movie.class);
        // 回调方法的参数为代理类对象 Cinema ,最后增强目标类调用的是代理类对象 Cinema 中的intercept方法
        enhancer.setCallback(cinemaCglibProxy);
        // 是增强过的目标类
        Movie movie = (Movie) enhancer.create();
        movie.play();

        Class<? extends Movie> movieClass = movie.getClass();
        // 查看增强过的类的父类是不是未增强的 Movie 类
        System.out.println("增强过的类的父类: " + movieClass.getSuperclass().getName());
        System.out.println("============打印增强过的类的所有方法==============");
        FanSheUtils.printMethods(movieClass);
        //Method[] allDeclaredMethods = ReflectionUtils.getAllDeclaredMethods(movieClass);
        //Arrays.asList(allDeclaredMethods).forEach(System.out::println);

        // 没有被增强过的 Movie 类
        Movie movie1 = new Movie();
        System.out.println("未增强过的类的父类："+movie1.getClass().getSuperclass().getName());
        System.out.println("=============打印增未强过的目标类的方法===============");
        FanSheUtils.printMethods(movie1.getClass());
        //Method[] allDeclaredMethods1 = ReflectionUtils.getAllDeclaredMethods(movie1.getClass());
        //Arrays.asList(allDeclaredMethods1).forEach(System.out::println);
    }
}
