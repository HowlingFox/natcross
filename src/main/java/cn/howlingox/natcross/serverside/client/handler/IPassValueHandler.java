package cn.howlingox.natcross.serverside.client.handler;

import cn.howlingox.natcross.serverside.client.adapter.PassValueNextEnum;
import cn.howlingox.natcross.channel.SocketChannel;
import cn.howlingox.natcross.common.Optional;

/**
 * 
 * <p>
 * 传值方式客户端是配置的处理接口
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:47:40
 * @param <R>
 * @param <W>
 */
public interface IPassValueHandler<R, W> {

	/**
	 * 处理方法
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:48:01
	 * @param socketChannel 交互通道
	 * @param optional      可以重设值
	 * @return
	 */
	PassValueNextEnum proc(SocketChannel<? extends R, ? super W> socketChannel, Optional<? extends R> optional);

}
