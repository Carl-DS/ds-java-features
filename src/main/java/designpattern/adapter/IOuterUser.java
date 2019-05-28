package designpattern.adapter;

import java.util.Map;

/**
 * @author duosheng
 * @since 2019/5/28
 */
public interface IOuterUser {
    /**
     * 基本信息，比如名称、性别、手机号码等
     *
     * @return
     */
    Map getUserBaseInfo();

    /**
     * 工作区域信息
     *
     * @return
     */
    Map getUserOfficeInfo();

    /**
     * 用户的家庭信息
     *
     * @return
     */
    Map getUserHomeInfo();
}
