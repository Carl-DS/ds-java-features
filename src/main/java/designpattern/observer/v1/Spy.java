package designpattern.observer.v1;

/**
 * @author duosheng
 * @since 2019/5/14
 */
public class Spy extends Thread {
    private HanFeiZi hanFeiZi;
    private LiSi liSi;
    private String type;

    /**
     * 通过构造函数传递参数，我要监控的是谁，谁来监控，要监控什么
     *
     * @param hanFeiZi
     * @param liSi
     * @param type
     */
    public Spy(HanFeiZi hanFeiZi, LiSi liSi, String type) {
        this.hanFeiZi = hanFeiZi;
        this.liSi = liSi;
        this.type = type;
    }

    @Override
    public void run() {
        while (true) {
            //监控是否在吃早餐
            if (this.type.equals("breakfast")) {
                //如果发现韩非子在吃饭，就通知李斯
                if (this.hanFeiZi.isHavingBreakfast()) {
                    this.liSi.update("韩非子在吃饭");
                    //重置状态，继续监控
                    this.hanFeiZi.setHavingBreakfast(false);
                }
            } else {
                if (this.hanFeiZi.isHavingFun()) {
                    this.liSi.update("韩非子在娱乐");
                    this.hanFeiZi.setHavingFun(false);
                }
            }
        }
    }
}
