package base.generictypes;

import java.io.Serializable;

/**
 * @author duosheng
 * @since 2019/9/10
 */
public class GenericTypesTests {

    class Base<T extends Comparable & Serializable & Cloneable> {}
}
