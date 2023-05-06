package cn.howlingox.natcross.serverside.client.config;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

import cn.howlingox.natcross.serverside.client.adapter.IClientServiceAdapter;
import cn.howlingox.natcross.channel.SocketChannel;

/**
 * 
 * <p>
 * 客户端服务配置
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:43:17
 * @param <R> 交互通道读取的类
 * @param <W> 交互通道可写的类
 */
public interface IClientServiceConfig<R, W> {

	/**
	 * 监听端口
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:43:51
	 * @return
	 */
	Integer getListenPort();

	/**
	 * 创建监听端口
	 * 
	 * @return
	 * @throws Exception
	 */
	ServerSocket createServerSocket() throws Exception;

	/**
	 * 客户端适配器
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:44:14
	 * @return
	 */
	IClientServiceAdapter getClientServiceAdapter();

	/**
	 * 交互通道
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:44:23
	 * @param listenSocket
	 * @return
	 * @throws Exception
	 */
	SocketChannel<? extends R, ? super W> newSocketChannel(Socket listenSocket) throws Exception;

	/**
	 * 字符集
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:44:31
	 * @return
	 */
	Charset getCharset();

}
