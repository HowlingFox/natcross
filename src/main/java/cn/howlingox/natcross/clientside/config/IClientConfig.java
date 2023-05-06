package cn.howlingox.natcross.clientside.config;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.spi.SelectorProvider;

import cn.howlingox.natcross.api.socketpart.AbsSocketPart;
import cn.howlingox.natcross.clientside.adapter.IClientAdapter;
import cn.howlingox.natcross.clientside.heart.IClientHeartThread;
import cn.howlingox.natcross.channel.SocketChannel;
import cn.howlingox.natcross.clientside.ClientControlThread;

/**
 * 
 * <p>
 * 客户端配置接口
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:30:04
 * @param <R> 通道读取的类型
 * @param <W> 通道写入的类型
 */
public interface IClientConfig<R, W> {

	/**
	 * 获取服务端IP
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 08:56:10
	 * @return
	 */
	String getClientServiceIp();

	/**
	 * 获取服务端端口
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 08:56:24
	 * @return
	 */
	Integer getClientServicePort();

	/**
	 * 对应的监听端口
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 08:56:31
	 * @return
	 */
	Integer getListenServerPort();

	/**
	 * 目标IP
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 08:56:56
	 * @return
	 */
	String getDestIp();

	/**
	 * 目标端口
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 08:57:03
	 * @return
	 */
	Integer getDestPort();

	/**
	 * 设置目标IP
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 08:57:16
	 * @param destIp
	 */
	void setDestIpPort(String destIp, Integer destPort);

	/**
	 * 新建心跳测试线程
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 09:03:09
	 * @param clientControlThread
	 * @return
	 */
	IClientHeartThread newClientHeartThread(ClientControlThread clientControlThread);

	/**
	 * 新建适配器
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 09:03:21
	 * @param clientControlThread
	 * @return
	 */
	IClientAdapter<R, W> newCreateControlAdapter(ClientControlThread clientControlThread);

	/**
	 * 新建与服务端的交互线程
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 09:03:50
	 * @return
	 */
	SocketChannel<? extends R, ? super W> newClientChannel();

	/**
	 * 创建新的socketPart
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 13:47:17
	 * @return
	 */
	AbsSocketPart newSocketPart(ClientControlThread clientControlThread);

	/**
	 * 创建目标端口
	 * 
	 * @author howlingfox
	 * @since 2023-04-23 16:07:56
	 * @return
	 * @throws Exception
	 */
	default Socket newDestSocket() throws Exception {
		java.nio.channels.SocketChannel openSocketChannel = SelectorProvider.provider().openSocketChannel();
		openSocketChannel.connect(new InetSocketAddress(this.getDestIp(), this.getDestPort()));
		return openSocketChannel.socket();
//		return new Socket(this.getDestIp(), this.getDestPort());
	};

}
