package designpattern.observer.v1;

/**
 * 具体的被观察者
 *
 * @author duosheng
 * @since 2019/5/14
 */
public class HanFeiZi implements IHanFeiZi {
    /**
     * 是否在吃饭，作为监控的判断标准
     */
    private boolean isHavingBreakfast = false;
    /**
     * 是否在娱乐
     */
    private boolean isHavingFun = false;

    @Override
    public void haveBreakfast() {
        System.out.println("韩非子:开始吃饭了...");
        this.isHavingBreakfast = true;
    }

    @Override
    public void haveFun() {
        System.out.println("韩非子:开始娱乐了...");
        this.isHavingFun = true;
    }

    public boolean isHavingBreakfast() {
        return isHavingBreakfast;
    }

    public void setHavingBreakfast(boolean havingBreakfast) {
        isHavingBreakfast = havingBreakfast;
    }

    public boolean isHavingFun() {
        return isHavingFun;
    }

    public void setHavingFun(boolean havingFun) {
        isHavingFun = havingFun;
    }
}
