package cn.howlingox.natcross.clientside.adapter;

import cn.howlingox.natcross.channel.SocketChannel;
import cn.howlingox.natcross.model.interactive.ServerWaitModel;

/**
 * 
 * <p>
 * 客户端适配器
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:22:43
 * @param <R> 处理的对象
 * @param <W> 可写的对象
 */
public interface IClientAdapter<R, W> {

	/**
	 * 请求建立控制器
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:22:53
	 * @return
	 * @throws Exception
	 */
	boolean createControlChannel() throws Exception;

	/**
	 * 请求建立隧道连接
	 * 
	 * @param serverWaitModel
	 * @return
	 * @author howlingfox
	 * @since 2021-04-09 12:33:20
	 */
	boolean clientConnect(ServerWaitModel serverWaitModel);

	/**
	 * 等待消息处理
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:23:33
	 * @throws Exception
	 */
	void waitMessage() throws Exception;

	/**
	 * 关闭
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:23:42
	 * @throws Exception
	 */
	void close() throws Exception;

	/**
	 * 向控制器发送心跳
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:23:49
	 * @throws Exception
	 */
	void sendHeartTest() throws Exception;

	/**
	 * 获取socket读写通道
	 * 
	 * @return
	 * @author howlingfox
	 * @since 2021-04-09 12:42:27
	 */
	SocketChannel<? extends R, ? super W> getSocketChannel();

}
