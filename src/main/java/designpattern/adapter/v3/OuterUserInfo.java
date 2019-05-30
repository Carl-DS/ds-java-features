package designpattern.adapter.v3;

import java.util.Map;

/**
 * @author duosheng
 * @since 2019/5/30
 */
public class OuterUserInfo implements IUserInfo {

    //源目标对象
    private IOuterUserBaseInfo baseInfo = null; //员工的基本信息
    private IOuterUserHomeInfo homeInfo = null; //员工的家庭信息
    private IOuterUserOfficeInfo officeInfo = null; //工作信息
    //数据处理
    private Map baseMap = null;
    private Map homeMap = null;
    private Map officeMap = null;

    /**
     * 构造函数传递对象
     *
     * @param _baseInfo
     * @param _homeInfo
     * @param _officeInfo
     */
    public OuterUserInfo(IOuterUserBaseInfo _baseInfo, IOuterUserHomeInfo _homeInfo, IOuterUserOfficeInfo _officeInfo) {
        this.baseInfo = _baseInfo;
        this.homeInfo = _homeInfo;
        this.officeInfo = _officeInfo;
        //数据处理
        this.baseMap = this.baseInfo.getUserBaseInfo();
        this.homeMap = this.homeInfo.getUserHomeInfo();
        this.officeMap = this.officeInfo.getUserOfficeInfo();
    }

    /**
     * 获得用户姓名
     *
     * @return
     */
    @Override
    public String getUserName() {
        String userName = (String) this.baseMap.get("userName");
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
        String homeAddress = (String) this.homeMap.get("homeAddress");
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
        String mobileNumber = (String) this.baseMap.get("mobileNumber");
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
        String officeTelNumber = (String) this.officeMap.get("officeTelNumber");
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
        String jobPosition = (String) this.officeMap.get("jobPosition");
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
        String homeTelNumber = (String) this.homeMap.get("homeTelNumber");
        System.out.println(homeTelNumber);
        return homeTelNumber;
    }
}
