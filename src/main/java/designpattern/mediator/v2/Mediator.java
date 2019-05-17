package designpattern.mediator.v2;

/**
 * @author duosheng
 * @since 2019/5/17
 */
public class Mediator extends AbstractMediator {

    /**
     * 中介者最重要的方法
     *
     * @param str
     * @param objects
     */
    @Override
    public void execute(String str, Object... objects) {
        //采购电脑
        if (str.equals("purchase.buy")) {
            this.buyComputer((Integer) objects[0]);
        } else if (str.equals("sale.sell")) {
            //销售电脑
            this.sellComputer((Integer) objects[0]);
        } else if (str.equals("sale.offsell")) {
            //折价销售
            this.offSell();
        } else if (str.equals("stock.clear")) {
            //清仓处理
            this.clearStock();
        }
    }

    /**
     * 采购电脑
     *
     * @param number
     */
    private void buyComputer(int number) {
        int saleStatus = super.sale.getSaleStatus();
        // 销售情况良好
        if (saleStatus > 80) {
            System.out.println("采购IBM电脑:" + number + "台");
            super.stock.increase(number);
        } else { //销售情况不好
            // 折半采购
            int buyNumber = number / 2;
            System.out.println("采购IBM电脑：" + buyNumber + "台");
        }
    }

    /**
     * 销售电脑
     *
     * @param number
     */
    private void sellComputer(int number) {
        //库存数量不够销售
        if (super.stock.getStockNumber() < number) {
            super.purchase.buyIBMComputer(number);
        }
        super.stock.decrease(number);
    }

    /**
     * 折价销售电脑
     */
    private void offSell() {
        System.out.println("折价销售IBM电脑" + stock.getStockNumber() + "台");
    }

    /**
     * 清仓处理
     */
    private void clearStock() {
        //要求打折销售
        super.sale.offSale();
        //要求采购人员不要采购
        super.purchase.refuseBuyIBM();
    }
}
