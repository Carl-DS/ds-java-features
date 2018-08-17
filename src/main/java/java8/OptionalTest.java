package java8;

import java.util.Optional;

/**
 * @author duosheng
 * @since 2018/6/26
 */
public class OptionalTest {

    public static void main(String[] args) {
        Optional<String> fullName = Optional.ofNullable("Tom");
        System.out.println("Full Name is set? " + fullName.isPresent());
        System.out.println("Full Name: " + fullName.orElseGet(() -> "[none]"));
        System.out.println(fullName.map(n -> "Hey " + n + "!").orElse("Hey Stranger!"));
    }
}
