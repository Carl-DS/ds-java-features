package java11;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * @author duosheng
 * @since 2018/10/19
 */
public class VarTest {

    /**
     * 在Lambda表达式中使用var
     */
    @Test
    public void varTest() throws IOException {
        // URL url = new URL("http://www.oracle.com/");
        // URLConnection conn = url.openConnection();
        // Reader reader = new BufferedReader(
        //         new InputStreamReader(conn.getInputStream()));

        // var 改造
        var url = new URL("http://www.oracle.com/");
        var urlConnection = url.openConnection();
        var bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        var numbers = new int[]{1, 2, 3, 4, 5, 6, 7};

        int[] subset = Arrays.stream(numbers).filter((var a) -> a > 5).toArray();
        Arrays.stream(subset).forEach(System.out::println);
    }

    @Test
    public void httpClientTest() throws IOException, InterruptedException {
        String uri = "http://www.baidu.com/";
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response);
    }

    @Test
    public void unicode10Test() {
        // JDK 11 更新了已有的 API 来支持最新的 Unicode 10 标准，主要体现在如下的类：
        //
        // java.lang 包中的 Character, String
        // java.awt.font 包中的 NumericShaper
        // java.text包中的 Bidi, BreakIterator, Normalizer
        String emoj = "\ud83d\ude02\ud83d\ude0d\ud83c\udf89\ud83d\udc4d";
        System.out.println(emoj);
    }

    @Test
    public void stringTest() {
        // JDK 11 中新增的 String API 包括：
        // public String strip() 去除前后的空格
        //
        // public String stripLeading() 去除前面的空格
        //
        // public String stripTrailing() 去除后面的空格
        //
        // public boolean isBlank() 判断是否为空，或者只含有空格
        //
        // public Stream<String> lines() 依据 line terminators (\n \r \r\n) 来进行分割
        //
        // public String repeat​(int count) 将字符串重复n次
        String s1 = " Testing ";
        System.out.println(s1.strip());

        String s2 = " Testing ";
        System.out.println(s2.stripLeading());

        String s3 = " Testing ";
        System.out.println(s3.stripTrailing());

        System.out.println("   ".isBlank());

        String s4 = "A\nB\nC";
        Stream<String> lines = s4.lines();
        lines.forEach(System.out::println);

        System.out.println("A".repeat(3));

    }

    @Test
    public void timeUnitTest() {
        var tu = TimeUnit.DAYS;

        // 将 50 小时转换为天数
        System.out.println(tu.convert(Duration.ofHours(50)));
    }
}
