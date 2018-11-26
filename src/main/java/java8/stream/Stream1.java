package java8.stream;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author duosheng
 * @since 2018/11/24
 */
class Stream1 {

    public static void main(String[] args) {
        List<String> myList =
                Arrays.asList("a1", "a2", "b1", "c2", "c1");

        // Stream表示元素序列，并支持对这些元素进行不同类型的计算操作:
        // Stream 操作包括中间操作和终端操作
        // filter,map,sorted 是中间操作，foreach 是终端操作
        /// 所示的Stream操作链也称为操作管道
        // 当不修改stream的底层数据源时，该函数是不干扰的，例如，在上面的例子中，没有lambda表达式通过添加或删除集合中的元素来修改myList。
        //
        // 当操作的执行是确定的时候，函数是无状态的，例如，在上面的例子执行过程中，没有lambda表达式依赖于可能发生变化的外部作用域的任何可变变量或状态。
        myList.stream()
                .filter(s -> s.startsWith("c"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);

        Arrays.asList("a1", "a2", "a3")
                .stream()
                .findFirst()
                .ifPresent(System.out::println);

        // 使用 Stream.of() 从一堆对象引用中创建一个Stream。
        Stream.of("a1", "a2", "a3")
                .findFirst()
                .ifPresent(System.out::println);

        // 除了常规的对象Stream，Java 8有特殊类型的Stream，用于处理基本数据类型int,long和double。你可能已经猜到了，它是IntStream、LongStream和DoubleStream。
        IntStream.range(1, 4)
                .forEach(System.out::println);

        // 原生 Stream 支持额外的终端聚合操作sum()和average():
        Arrays.stream(new int[]{1, 2, 3})
                .map(n -> 2 * n + 1)
                .average()
                .ifPresent(System.out::println);

        // 有时需要将一个普通对象Stream转换为原生 Stream，反之亦然。为此，对象Stream支持专门的映射操作mapToInt()、mapToLong()和mapToDouble:
        Stream.of("a1", "a2", "a3")
                .map(s -> s.substring(1))
                .mapToInt(Integer::parseInt)
                .max()
                .ifPresent(System.out::println);
        // 原生Stream可以通过mapToObj()转换为对象Stream:
        IntStream.range(1, 4)
                .mapToObj(i -> "a" + i)
                .forEach(System.out::println);
        // 这里有一个组合示例:double的Stream首先映射到一个IntStream，而不是映射到字符串的对象Stream:
        Stream.of(1.0, 2.0, 3.0)
                .mapToInt(Double::intValue)
                .mapToObj(i -> "a" + i)
                .forEach(System.out::println);

        // 处理顺序
        // 中间操作的一个重要特征是惰性。以下例子中，终端操作是缺失的:
        // 在执行此代码片段时，不会向控制台输出任何内容。这是因为中间操作只在出现终端操作时执行。
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return true;
                });

        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return true;
                })
                .forEach(s -> System.out.println("forEach: " + s));

        // 当predicate应用于给定的输入元素时，anyMatch将立即返回true。这对于第二个被传递的“A2”来说是正确的。
        // 由于stream链的垂直执行，在这种情况下，map只会执行两次。因此，map将尽可能少地被调用，而不是所有的元素映射到Stream中。
        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .anyMatch(s -> {
                    System.out.println("anyMatch: " + s);
                    return s.startsWith("A");
                });

        System.out.println("底层集合中的每个字符串都被调用了5次map和filter，而forEach只调用一次。");
        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("A");
                })
                .forEach(s -> System.out.println("forEach: " + s));

        System.out.println("现在，map只被调用一次，因此操作管道在大量元素输入时执行得更快。在编写复杂的方法链时，请记住这一点。");
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));

        // 让我们通过一个额外的操作来扩展上面的示例，sorted:
        // 排序是一种特殊的中间操作。这是所谓的状态操作，因为要对元素进行排序，你需要维护元素的状态。
        Stream.of("d2", "a2", "b1", "b3", "c")
                .sorted((s1, s2) -> {
                    System.out.printf("sort: %s; %s\n", s1, s2);
                    return s1.compareTo(s2);
                })
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));
        // 我们再一次通过对链操作重排序来优化性能:
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .sorted((s1, s2) -> {
                    System.out.printf("sort: %s; %s\n", s1, s2);
                    return s1.compareTo(s2);
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));


        // Stream复用
        Stream<String> stream =
                Stream.of("d2", "a2", "b1", "b3", "c")
                        .filter(s -> s.startsWith("a"));
        stream.anyMatch(s -> true);    // ok
        // stream.noneMatch(s -> true);   // exception

        // 我们可以创建一个Stream提供者来创建已构建所有中间操作的新Stream:
        // 每次调用 get() 构造一个新Stream，我们在此调用终端操作。
        Supplier<Stream<String>> streamSupplier =
                () -> Stream.of("d2", "a2", "b1", "b3", "c")
                        .filter(s -> s.startsWith("a"));
        streamSupplier.get().anyMatch(s -> true);
        streamSupplier.get().noneMatch(s -> true);

        class Person {
            String name;
            int age;

            Person(String name, int age) {
                this.name = name;
                this.age = age;
            }

            @Override
            public String toString() {
                return name;
            }
        }

        // 高级操作collect，flatMap和 reduce。
        List<Person> persons =
                Arrays.asList(
                        new Person("Max", 18),
                        new Person("Peter", 23),
                        new Person("Pamela", 23),
                        new Person("David", 12));

        // Collect是一种非常有用的终端操作，可以将stream元素转换为不同类型的结果，例如List, Set or Map
        //  Collect 接受一个包含四个不同操作的Collector: supplier, accumulator, combiner 和 finisher。这听起来很复杂，优点是Java 8通过Collectors类支持各种内置收集器
        // 如果需要set而不是list 使用Collectors.toSet()就可以。
        List<Person> per = persons.stream()
                .filter(person -> person.name.startsWith("P"))
                .collect(Collectors.toList());

        // [Peter, Pamela]
        System.out.println(per);

        // 将所有人按年龄分组:
        Map<Integer, List<Person>> personsByAge = persons.stream()
                .collect(Collectors.groupingBy(person -> person.age));
        personsByAge.forEach((age, pe) -> System.out.format("age %s: %s\n", age, pe));

        // Collectors 是多功能的。您还可以在Stream的元素上创建聚合，例如计算平均年龄
        Double avgAge = persons.stream()
                .collect(Collectors.averagingInt(a -> a.age));
        // 19.0
        System.out.println(avgAge);

        // 年龄最小值、最大值和算术平均值以及总和和数量。
        IntSummaryStatistics ageSummary = persons.stream()
                .collect(Collectors.summarizingInt(s -> s.age));
        System.out.println("ageSummary: " + ageSummary);

        // 所有 persons 合并成一个字符串:
        String phrase = persons.stream()
                .filter(s -> s.age >= 18)
                .map(s -> s.name)
                .collect(Collectors.joining(" and ", "In Germany ", " are of legal age."));

        System.out.println(phrase);

        // 为了将stream元素转换为map，我们必须指定键和值如何映射。请记住，映射的键必须是惟一的，否则会抛出IllegalStateException。
        // 你可以将合并函数作为额外参数传递，以绕过异常:
        Map<Integer, String> map = persons.stream()
                .collect(Collectors.toMap(
                        m -> m.age,
                        m -> m.name,
                        (name1, name2) -> name1 + ";" + name2
                ));

        System.out.println(map);

        // 将Stream中所有person转换成一个由 | 管道字符分隔的大写字母组成的字符串。
        // 为了实现这一点，我们通过 Collector.of() 创建了一个新的Collector。我们必须传递Collector的四个要素:supplier、accumulator、 combiner和finisher。
        Collector<Person, StringJoiner, String> personNameCollector =
                Collector.of(
                        // supplier
                        () -> new StringJoiner(" | "),
                        // accumulator
                        (j, p) -> j.add(p.name.toUpperCase()),
                        // combiner
                        (j1, j2) -> j1.merge(j2),
                        StringJoiner::toString
                );
        String names = persons.stream()
                .collect(personNameCollector);
        // 由于Java中的字符串是不可变的，所以我们需要一个类似StringJoiner的helper类来让collector构造我们的字符串。
        // supplier最初使用适当的分隔符构造了这样一个StringJoiner。 accumulator用于将每个人的大写名称添加到StringJoiner中。
        // combiner 知道如何将两个StringJoiners合并成一个。在最后一步中，finisher从StringJoiner中构造所需的字符串。
        System.out.println(names);

        // Map是有局限的，因为每个对象只能映射到一个对象。但是，如果我们想要将一个对象变换为多个对象，
        // 或者将它变换成根本不存在的对象呢?这就是flatMap发挥作用的地方。
        // FlatMap将stream的每个元素转换到其他对象的Stream。因此，每个对象将被转换为零个、一个或多个基于Stream的不同对象。
        // 这些stream的内容将被放置到flatMap操作的返回Stream中。

        List<Foo> foos = new ArrayList<>();

        // create foos
        IntStream.range(1, 4)
                .forEach(i -> foos.add(new Foo("Foo" + i)));

        // create bars
        foos.forEach(f -> {
            IntStream.range(1, 4)
                    .forEach(i -> f.bars.add(new Bar("Bar" + i + "<- " + f.name)));
        });

        foos.stream()
                .flatMap(f -> f.bars.stream())
                .forEach(b -> System.out.println(b.name));

        // 上述代码示例可以简化为stream操作的单管道:
        IntStream.range(1, 4)
                .mapToObj(i -> new Foo("Foo" + i))
                .peek(f -> IntStream.range(1, 4)
                        .mapToObj(i -> new Bar("Bar" + i + "<-" + f.name))
                        .forEach(f.bars::add)
                )
                .flatMap(f -> f.bars.stream())
                .forEach(b -> System.out.println(b.name));


        // 为了处理外部实例的内部字符串foo，必须添加多个空检查以防止可能的NullPointerExceptions:
        Outer outer = new Outer();
        if (outer != null && outer.nested != null && outer.nested.inner != null) {
            System.out.println(outer.nested.inner.foo);
        }

        // 利用optionals flatMap操作可以达到相同效果:
        Optional.of(new Outer())
                .flatMap(o -> Optional.ofNullable(o.nested))
                .flatMap(n -> Optional.ofNullable(n.inner))
                .flatMap(i -> Optional.ofNullable(i.foo))
                .ifPresent(System.out::println);

        // reduce操作将stream的所有元素合并到一个结果中
        // Java 8支持三种不同的reduce方法。第一种将stream中元素reduce为一个。
        // 最年长的人:
        persons.stream()
                .reduce((p1, p2) -> p1.age > p2.age ? p1 : p2)
                .ifPresent(System.out::println);

        // 第二个reduce方法接受 实体值和BinaryOperator累加器,该方法可用于构建一个新的Person，它聚合来自于stream的其他人的的姓名和年龄:
        Person result = persons.stream()
                .reduce(new Person("", 0), (p1, p2) -> {
                    p1.age += p2.age;
                    p1.name += p2.name;
                    return p1;
                });
        System.out.format("name=%s; age=%s\n", result.name, result.age);

        // 第三种 reduce 方法接受三个参数:标识值、BiFunction累加器和BinaryOperator类型的组合函数。由于标识值类型并不局限于Person类型，所以我们可以确定所有人的年龄和:
        Integer ageSum = persons
                .stream()
                .reduce(0,
                        (sum, p) -> {
                            System.out.format("accumulator: sum=%s; person=%s\n", sum, p);
                            return sum += p.age;
                        },
                        (sum1, sum2) -> {
                            System.out.format("combiner: sum1=%s; sum2=%s\n", sum1, sum2);
                            return sum1 + sum2;
                        });
        System.out.println(ageSum);

        // 并行执行此stream将产生完全不同的执行过程。现在这个combiner被调用了。由于accumulator是并行调用的，所以需要combiner来汇总分离的累计值。
        persons.parallelStream()
                .reduce(0, (sum, p) -> {
                    System.out.format("accumulator: sum=%s; person=%s\n", sum, p);
                    return sum += p.age;
                }, (sum1, sum2) -> {
                    System.out.format("combiner: sum1=%s; sum2=%s\n", sum1, sum2);
                    return sum1 + sum2;
                });

        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        System.out.println("ForkJoinPool=> " + commonPool.getParallelism());


        // 并行Stream
        Arrays.asList("a1", "a2", "b1", "c2", "c1")
                .parallelStream()
                .filter(s -> {
                    System.out.format("filter: %s [%s]\n", s, Thread.currentThread().getName());
                    return true;
                })
                .map(s -> {
                    System.out.format("map: %s [%s]\n", s, Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.format("forEach: %s [%s]\n", s, Thread.currentThread().getName()));

        // 并行Stream 添加sort操作
        // 可以发现：1，sort操作还是一个行式执行的操作，它会组合元素的向下传播。2，次数的 sort 操作似乎是只是一个线程中执行，而不会使用多线程并发执行？
        // 实际上，并行 Stream 的 sort 操作会使用 Java 8 中的 Arrays.parallelSort() 方法，而该方法会更加需要 sort 的元素的个数的数量来决定是否使用多线程来执行。
        // 如JavaDoc中所述，如果将按顺序或并行执行排序，则此方法将决定数组的长度：
        //
        // 如果指定数组的长度小于最小粒度，则使用适当的 Arrays.sort 方法对其进行排序。
        Arrays.asList("a1", "a2", "b1", "c2", "c1")
                .parallelStream()
                .filter(s -> {
                    System.out.format("filter: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return true;
                })
                .map(s -> {
                    System.out.format("map: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .sorted((s1, s2) -> {
                    System.out.format("sort: %s <> %s [%s]\n",
                            s1, s2, Thread.currentThread().getName());
                    return s1.compareTo(s2);
                })
                .forEach(s -> System.out.format("forEach: %s [%s]\n",
                        s, Thread.currentThread().getName()));

        // 在我们 Reduce 部分的最后一段中，我们发现 组合函数只会被并行 Stream 所使用，而不会被顺序 Stream 所使用。
        // 可以发现，累加器和组合器都会利用 ForkJoinPool 中的多个线程来执行。
        // 可以发现，并行 Stream 会充分利用 ForkJoinPool 中的多个线程来执行操作，从而极大地调高程序的性能。
        // 当然，我们还比较记住的一件事就是：并行 Stream 的某些操作，比如 reduce 和 collect 需要另外的操作参数来执行必要的合并操作，
        // 而这些操作可能是顺序 Stream 所不需要的。
        // 注意：细心的你可能发现了，所有的并行 Stream 是在分享同一个 JVM 范围内的通用 ForkJoinPool。
        // 所以，你应该避免在并行 Stream 执行慢速的、阻塞性的操作，因为它们可能会减慢程序其他部分的执行速度和性能，
        // 如果程序的其他部分也直接或者间接使用了 ForkJoinPool 的话。
        persons.parallelStream()
                .reduce(0, (sum, p) -> {
                    System.out.format("accumulator: sum=%s; person=%s [%s]\n", sum, p, Thread.currentThread().getName());
                    return sum += p.age;
                }, (sum1, sum2) -> {
                    System.out.format("combiner: sum1=%s; sum2=%s [%s]\n",
                            sum1, sum2, Thread.currentThread().getName());
                    return sum1 + sum2;
                });

    }


    static class Foo {
        String name;
        List<Bar> bars = new ArrayList<>();

        Foo(String name) {
            this.name = name;
        }
    }

    static class Bar {
        String name;

        Bar(String name) {
            this.name = name;
        }
    }

    static class Outer {
        Nested nested;
    }

    class Nested {
        Inner inner;
    }

    class Inner {
        String foo;
    }
}


