package cn.howlingox.natcross.serverside.listen.recv;

import cn.howlingox.natcross.channel.SocketChannel;

/**
 * <p>
 * 接收处理器
 * </p>
 *
 * @author howlingfox
 * @since 2023-04-15 11:13:20
 */
public interface IRecvHandler<R, W> {

	/**
	 * 处理方法
	 *
	 * @param model
	 * @param channel
	 * @return
	 * @throws Exception
	 * @author howlingfox
	 * @since 2021-04-26 17:25:19
	 */
    boolean proc(R model, SocketChannel<? extends R, ? super W> channel) throws Exception;

}
