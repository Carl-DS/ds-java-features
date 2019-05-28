package designpattern.adapter;

import java.util.Map;

/**
 * @author duosheng
 * @since 2019/5/28
 */
public class OuterUserInfo extends OuterUser implements IUserInfo {

    /**
     * 员工的基本信息
     */
    private Map baseInfo = super.getUserBaseInfo();
    /**
     * 员工的家庭信息
     */
    private Map homeInfo = super.getUserHomeInfo();
    /**
     * 工作信息
     */
    private Map officeInfo = super.getUserOfficeInfo();

    /**
     * 获得用户姓名
     *
     * @return
     */
    @Override
    public String getUserName() {
        String userName = (String)this.baseInfo.get("userName");
        System.out.println(userName);
        return userName;
    }

    /**
     * 获得家庭地址
     *
     * @return
     */
    @Override
    public String getHomeAddress() {
        String homeAddress = (String)this.homeInfo.get("homeAddress");
        System.out.println(homeAddress);
        return homeAddress;
    }

    /**
     * 手机号码，这个太重要，手机泛滥呀
     *
     * @return
     */
    @Override
    public String getMobileNumber() {
        String mobileNumber = (String)this.baseInfo.get("mobileNumber");
        System.out.println(mobileNumber);
        return mobileNumber;
    }

    /**
     * 办公电话，一般是座机
     *
     * @return
     */
    @Override
    public String getOfficeTelNumber() {
        String officeTelNumber = (String)this.officeInfo.get("officeTelNumber");
        System.out.println(officeTelNumber);
        return officeTelNumber;
    }

    /**
     * 这个人的职位是什么
     *
     * @return
     */
    @Override
    public String getJobPosition() {
        String jobPosition = (String)this.officeInfo.get("jobPosition");
        System.out.println(jobPosition);
        return jobPosition;
    }

    /**
     * 获得家庭电话，这有点不好，我不喜欢打家庭电话讨论工作
     *
     * @return
     */
    @Override
    public String getHomeTelNumber() {
        String homeTelNumber = (String)this.homeInfo.get("homeTelNumber");
        System.out.println(homeTelNumber);
        return homeTelNumber;
    }
}
