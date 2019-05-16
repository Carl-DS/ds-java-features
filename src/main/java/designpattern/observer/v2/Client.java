package designpattern.observer.v2;

/**
 * @author duosheng
 * @since 2019/5/14
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {
        //定义出韩非子
        HanFeiZi hanFeiZi = new HanFeiZi();
        //然后我们看看韩非子在干什么
        hanFeiZi.haveBreakfast();
        //韩非子娱乐了
        hanFeiZi.haveFun();
    }
}
