package designpattern.observer.v2;

/**
 * 具体的被观察者
 *
 * @author duosheng
 * @since 2019/5/14
 */
public class HanFeiZi implements IHanFeiZi {
    //把李斯声明出来
    private ILiSi liSi = new LiSi();

    @Override
    public void haveBreakfast() {
        System.out.println("韩非子:开始吃饭了...");
        //通知李斯
        this.liSi.update("韩非子在吃饭");
    }

    @Override
    public void haveFun() {
        System.out.println("韩非子:开始娱乐了...");
        this.liSi.update("韩非子在娱乐");
    }
}
