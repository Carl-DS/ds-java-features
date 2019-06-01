package designpattern.iterator.v2;

/**
 * @author duosheng
 * @since 2019/5/31
 */
public class Boss {

    /**
     * 细心的读者可能会从代码中发现一个问题，java.util.iterator接口中定义next()方法的返回
     * 值类型是E，而你在ProjectIterator中返回值却是IProject，E和IProject有什么关系？
     * E是JDK 1.5中定义的新类型：元素（Element），是一个泛型符号，表示一个类型，具体
     * 什么类型是在实现或运行时决定，总之它代表的是一种类型，你在这个实现类中把它定义为
     * ProjectIterator，在另外一个实现类可以把它定义为String，都没有问题。它与Object这个类可
     * 是不同的，Object是所有类的父类，随便一个类你都可以把它向上转型到Object类，也只是
     * 因为它是所有类的父类，它才是一个通用类，而E是一个符号，代表所有的类，当然也代表
     * Object了。
     *
     * @param args
     */
    public static void main(String[] args) {
        //定义一个List，存放所有的项目对象
        IProject project = new Project();
        //增加星球大战项目
        project.add("星球大战项目ddddd", 10, 100000);
        //增加扭转时空项目
        project.add("扭转时空项目", 100, 10000000);
        //增加超人改造项目
        project.add("超人改造项目", 10000, 1000000000);
        //这边100个项目
        for (int i = 4; i < 104; i++) {
            project.add("第" + i + "个项目", i * 5, i * 1000000);
        }
        //遍历一下ArrayList，把所有的数据都取出
        IProjectIterator projectIterator = project.iterator();
        while (projectIterator.hasNext()) {
            IProject p = (IProject) projectIterator.next();
            System.out.println(p.getProjectInfo());
        }
    }
}
