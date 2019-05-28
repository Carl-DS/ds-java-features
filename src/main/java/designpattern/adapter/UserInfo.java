package designpattern.adapter;

/**
 * @author duosheng
 * @since 2019/5/28
 */
public class UserInfo implements IUserInfo {
    /**
     * 获得用户姓名
     *
     * @return
     */
    @Override
    public String getUserName() {
        System.out.println("姓名叫做...");
        return null;
    }

    /**
     * 获得家庭地址
     *
     * @return
     */
    @Override
    public String getHomeAddress() {
        System.out.println("这里是员工的家庭地址...");
        return null;
    }

    /**
     * 手机号码，这个太重要，手机泛滥呀
     *
     * @return
     */
    @Override
    public String getMobileNumber() {
        System.out.println("这个人的手机号码是0000...");
        return null;
    }

    /**
     * 办公电话，一般是座机
     *
     * @return
     */
    @Override
    public String getOfficeTelNumber() {
        System.out.println("办公室电话是...");
        return null;
    }

    /**
     * 这个人的职位是什么
     *
     * @return
     */
    @Override
    public String getJobPosition() {
        System.out.println("这个人的职位是BOSS...");
        return null;
    }

    /**
     * 获得家庭电话，这有点不好，我不喜欢打家庭电话讨论工作
     *
     * @return
     */
    @Override
    public String getHomeTelNumber() {
        System.out.println("员工的家庭电话是...");
        return null;
    }
}
