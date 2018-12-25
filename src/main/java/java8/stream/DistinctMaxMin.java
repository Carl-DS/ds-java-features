package java8.stream;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author duosheng
 * @since 2018/12/5
 */
public class DistinctMaxMin {

    @Test
    public void distinctTest() {
        Collection<String> list = Arrays.asList("A", "B", "C", "D", "A", "B", "C");

        // Get collection without duplicate i.e. distinct only
        List<String> distinctElements = list.stream().distinct().collect(Collectors.toList());

        // Let's verify distinct elements
        System.out.println(distinctElements);
    }

    @Test
    public void maxTest() {
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        List<LocalDate> dates = Stream.iterate(start, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(start, end))
                .collect(Collectors.toList());

        // Get Min or Max Date
        LocalDate maxDate = dates.stream().max( Comparator.comparing( LocalDate::toEpochDay ) ).get();
        LocalDate minDate = dates.stream().min( Comparator.comparing( LocalDate::toEpochDay ) ).get();

        System.out.println("maxDate = " + maxDate);
        System.out.println("minDate = " + minDate);
    }

    @Test
    public void numberMaxMinTest() {
        Integer maxNumber = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 9)
                .max(Comparator.comparing(Integer::valueOf)).get();
        Integer minNumber = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 1)
                .min(Comparator.comparing(Integer::valueOf)).get();

        System.out.println("maxNumber = " + maxNumber);
        System.out.println("minNumber = " + minNumber);
    }

    @Test
    public void objectMaxMinTest() {
        List<Employee> emps = new ArrayList<Employee>();

        emps.add(new Employee(1, "Lokesh", 36, "2018-12-01 11:04:32"));
        emps.add(new Employee(2, "Alex", 36, "2018-12-01 11:04:30"));
        emps.add(new Employee(3, "Brian", 52, "2018-12-01 14:04:32"));
        emps.add(new Employee(4, "Lds", 52, "2018-12-01 13:04:32"));

        Comparator<Employee> comparator = Comparator.comparing(Employee::getAge);

        // Get Min or Max Object
        Employee minObject = emps.stream().sorted(Comparator.comparing(Employee::getStartTime)).min(comparator).get();
        Employee maxObject = emps.stream().sorted(Comparator.comparing(Employee::getStartTime)).max(comparator).get();

        System.out.println("minObject = " + minObject);
        System.out.println("maxObject = " + maxObject);


    }

    class Employee {
        private int id;
        private String name;
        private int age;
        private String startTime;

        public Employee(int id, String name, int age) {
            super();
            this.id = id;
            this.name = name;
            this.age = age;
        }
        public Employee(int id, String name, int age, String startTime) {
            super();
            this.id = id;
            this.name = name;
            this.age = age;
            this.startTime = startTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        @Override
        public String toString() {
            StringBuilder str = null;
            str = new StringBuilder();
            str.append("Id:- " + getId() + " Name:- " + getName() + " Age:- " + getAge() + " StartTime:- " + getStartTime());
            return str.toString();
        }
    }
}
