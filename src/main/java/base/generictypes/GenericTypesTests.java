package base.generictypes;

import java.io.Serializable;

/**
 * @author duosheng
 * @since 2019/9/10
 */
public class GenericTypesTests {

    class Base<T extends Comparable & Serializable & Cloneable> {
    }

    /**
     * 泛型方法
     *
     * @param inputArray
     * @param <E>
     */
    public static <E> void printArray(E[] inputArray) {
        for (E element : inputArray) {
            System.out.printf("%s ", element);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // 创建不同类型数组： Integer, Double 和 Character
        Integer[] intArray = {1, 2, 3};
        String[] stringArray = {"Hello", "World"};
        printArray(intArray);
        printArray(stringArray);
    }
}
