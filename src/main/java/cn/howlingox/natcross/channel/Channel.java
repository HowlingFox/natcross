package cn.howlingox.natcross.channel;

import java.io.Closeable;
import java.nio.charset.Charset;

/**
 * 
 * <p>
 * 读写通道
 * </p>
 *
 * @author howlingfox
 * @since 2023-01-03 15:40:28
 * @param <R> 读取返回的类型
 * @param <W> 写入的类型
 */
public interface Channel<R, W> extends Closeable {

	/**
	 * 简单的读取方式
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:09:22
	 * @return
	 * @throws Exception
	 */
	R read() throws Exception;

	/**
	 * 简单的写入
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:09:32
	 * @param value
	 * @throws Exception
	 */
	void write(W value) throws Exception;

	/**
	 * 刷新
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:09:46
	 * @throws Exception
	 */
	void flush() throws Exception;

	/**
	 * 简单的写入并刷新
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:09:57
	 * @param value
	 * @throws Exception
	 */
	void writeAndFlush(W value) throws Exception;

	/**
	 * 设置交互编码
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:10:06
	 * @param charset
	 */
	default public void setCharset(Charset charset) {
		throw new UnsupportedOperationException("不支持的操作");
	}
}
