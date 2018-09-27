package netty.in.action.chapter05;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.junit.jupiter.api.Test;

/**
 * @author duosheng
 * @since 2018/8/31
 */
public class ByteBufTests {

    @Test
    public void sliceTest() {
        ByteBuf byteBuf = Unpooled.copiedBuffer("Netty in Action rocks!", CharsetUtil.UTF_8);
        // 创建该ByteBuf 从索引 0 开始到索引 15 结束的一个新切片
        ByteBuf sliced = byteBuf.slice(0, 15);
        System.out.println(sliced.toString(CharsetUtil.UTF_8));
        byteBuf.setByte(0, (byte) 'J');
        // 将会成功，因为数据是共享的，对其中 一个所做的更改对另外一个也是可见的
        assert byteBuf.getByte(0) == sliced.getByte(0);
    }

    @Test
    public void copyTest() {
        ByteBuf byteBuf = Unpooled.copiedBuffer("Netty in Action rocks!", CharsetUtil.UTF_8);
        // 创建该ByteBuf 从索引 0 开始到索引 15 结束的分段的副本
        ByteBuf sliced = byteBuf.copy(0, 15);
        System.out.println(sliced.toString(CharsetUtil.UTF_8));
        byteBuf.setByte(0, (byte) 'J');
        // 将会成功，因为数据不是共享的
        assert byteBuf.getByte(0) != sliced.getByte(0);
    }
}
