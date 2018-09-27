package netty.in.action.chapter08;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * @author duosheng
 * @since 2018/9/1
 */
public class SafeByteToMessageDecoder extends ByteToMessageDecoder {
    private static final int MAX_FRAME_SIZE = 1024;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readableBytes = in.readableBytes();
        // 检查缓冲区中是否有超过 MAX_FRAME_SIZE个字节
        if (readableBytes > MAX_FRAME_SIZE) {
            // 跳过所有的可读字节，抛出TooLongFrameException 并通知ChannelHandler
            in.skipBytes(readableBytes);
            throw new TooLongFrameException("Frame too big!");
        }
        // do something
    }
}
