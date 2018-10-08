package nettyguide.ch7;

import java.io.Serializable;

/**
 * @author duosheng
 * @since 2018/10/8
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -5759254211418239784L;
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
