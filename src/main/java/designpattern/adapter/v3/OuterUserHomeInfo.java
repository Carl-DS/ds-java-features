package designpattern.adapter.v3;

import java.util.HashMap;
import java.util.Map;

/**
 * @author duosheng
 * @since 2019/5/30
 */
public class OuterUserHomeInfo implements IOuterUserHomeInfo {
    /**
     * 用户的家庭信息
     *
     * @return
     */
    @Override
    public Map getUserHomeInfo() {
        HashMap homeInfo = new HashMap();
        homeInfo.put("homeTelNumbner", "员工的家庭电话是...");
        homeInfo.put("homeAddress", "员工的家庭地址是...");
        return homeInfo;
    }
}
