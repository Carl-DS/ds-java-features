package base.ifelse;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class SwitchTests {

    @Test
    public void switchTest() {
        int type = 2;
        switch (type) {
            case 1:
                log.info("2");
                break;
            default:
                log.info("default");
        }
    }
}
