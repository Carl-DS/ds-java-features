package designpattern.adapter.v3;

import java.util.HashMap;
import java.util.Map;

/**
 * @author duosheng
 * @since 2019/5/30
 */
public class OuterUserOfficeInfo implements IOuterUserOfficeInfo{


    /**
     * 工作区域信息
     *
     * @return
     */
    @Override
    public Map getUserOfficeInfo() {
        HashMap officeInfo = new HashMap();
        officeInfo.put("jobPosition","这个人的职位是BOSS...");
        officeInfo.put("officeTelNumber", "员工的办公电话是...");
        return officeInfo;
    }
}
