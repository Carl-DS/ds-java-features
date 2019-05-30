package designpattern.adapter.v3;

import java.util.HashMap;
import java.util.Map;

/**
 * @author duosheng
 * @since 2019/5/30
 */
public class OuterUserBaseInfo implements IOuterUserBaseInfo {
    /**
     * 基本信息，比如名称、性别、手机号码等
     *
     * @return
     */
    @Override
    public Map getUserBaseInfo() {
        HashMap baseInfoMap = new HashMap();
        baseInfoMap.put("userName", "这个员工叫混世魔王...");
        baseInfoMap.put("mobileNumber", "这个员工电话是...");
        return baseInfoMap;
    }
}
