package java8.time;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author duosheng
 * @since 2018/12/3
 */
public class Time1 {

    @Test
    public void clockTest() {
        // Clock 时钟
        // Clock类提供了访问当前日期和时间的方法，Clock是时区敏感的，可以用来取代System.currentTimeMillis() 来获取当前的微秒数。
        // 某一个特定的时间点也可以使用Instant类来表示，Instant类也可以用来创建老的java.util.Date对象。
        Clock clock = Clock.systemDefaultZone();
        long millis = clock.millis();
        Instant instant = clock.instant();
        Date date = Date.from(instant);
        System.out.println("instant to date: " + date);
    }

    @Test
    public void DateTimeFormatterTest() {
        DateTimeFormatter isoDateTime = DateTimeFormatter.ISO_DATE_TIME;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss E");
        System.out.println(Instant.now());

        //Format examples
        LocalDate date = LocalDate.now();
        //default format
        System.out.println("Default format of LocalDate="+date);
        //specific format
        System.out.println(date.format(DateTimeFormatter.ofPattern("d::MMM::uuuu")));
        System.out.println(date.format(DateTimeFormatter.BASIC_ISO_DATE));

        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault());

        System.out.println(DATE_TIME_FORMATTER.format(new Date().toInstant()));
    }
}
