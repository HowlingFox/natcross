package cn.howlingox.natcross.serverside.listen.config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import cn.howlingox.natcross.api.socketpart.AbsSocketPart;
import cn.howlingox.natcross.api.socketpart.SimpleSocketPart;
import cn.howlingox.natcross.serverside.listen.control.ControlSocket;
import cn.howlingox.natcross.serverside.listen.control.IControlSocket;
import cn.howlingox.natcross.serverside.listen.recv.CommonReplyHandler;
import cn.howlingox.natcross.serverside.listen.serversocket.ICreateServerSocket;
import com.alibaba.fastjson.JSONObject;

import lombok.Data;
import lombok.NoArgsConstructor;
import cn.howlingox.natcross.channel.InteractiveChannel;
import cn.howlingox.natcross.channel.SocketChannel;
import cn.howlingox.natcross.model.InteractiveModel;
import cn.howlingox.natcross.serverside.listen.ServerListenThread;
import cn.howlingox.natcross.serverside.listen.clear.ClearInvalidSocketPartThread;
import cn.howlingox.natcross.serverside.listen.clear.IClearInvalidSocketPartThread;
import cn.howlingox.natcross.serverside.listen.recv.ClientHeartHandler;

/**
 * <p>
 * 简单的交互、隧道；监听服务配置
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:53:17
 */
@Data
@NoArgsConstructor
public class SimpleListenServerConfig implements IListenServerConfig {

	private Integer listenPort;

	private Long invaildMillis = 60000L;
	private Long clearInterval = 10L;

	private Charset charset = StandardCharsets.UTF_8;

	private ICreateServerSocket createServerSocket;

	private int streamCacheSize = 8196;

	public SimpleListenServerConfig(Integer listenPort) {
		this.listenPort = listenPort;
	}

	@Override
	public ServerSocket createServerSocket() throws Exception {
		if (this.createServerSocket == null) {
			ServerSocketChannel openServerSocketChannel = SelectorProvider.provider().openServerSocketChannel();
			openServerSocketChannel.bind(new InetSocketAddress(this.getListenPort()));
			return openServerSocketChannel.socket();
//			return new ServerSocket(this.getListenPort());
		} else {
			return this.createServerSocket.createServerSocket(this.getListenPort());
		}
	}

	/**
	 * 创建controlSocket使用channel
	 * 
	 * @author howlingfox
	 * @since 2023-04-15 13:19:49
	 * @param socket
	 * @return
	 */
	protected SocketChannel<? extends InteractiveModel, ? super InteractiveModel> newControlSocketChannel(
			Socket socket) {
		InteractiveChannel interactiveChannel;
		try {
			interactiveChannel = new InteractiveChannel(socket);
		} catch (IOException e) {
			return null;
		}
		return interactiveChannel;
	}

	@Override
	public IControlSocket newControlSocket(Socket socket, JSONObject config) {
		SocketChannel<? extends InteractiveModel, ? super InteractiveModel> controlSocketChannel = this
				.newControlSocketChannel(socket);
		ControlSocket controlSocket = new ControlSocket(controlSocketChannel);
		controlSocket.addRecvHandler(CommonReplyHandler.INSTANCE);
		controlSocket.addRecvHandler(ClientHeartHandler.INSTANCE);
		return controlSocket;
	}

	@Override
	public IClearInvalidSocketPartThread newClearInvalidSocketPartThread(ServerListenThread serverListenThread) {
		ClearInvalidSocketPartThread clearInvalidSocketPartThread = new ClearInvalidSocketPartThread(
				serverListenThread);
		clearInvalidSocketPartThread.setClearIntervalSeconds(this.getClearInterval());
		return clearInvalidSocketPartThread;
	}

	@Override
	public AbsSocketPart newSocketPart(ServerListenThread serverListenThread) {
		SimpleSocketPart socketPart = new SimpleSocketPart(serverListenThread);
		socketPart.setInvaildMillis(this.getInvaildMillis());
		socketPart.setStreamCacheSize(this.getStreamCacheSize());
		return socketPart;
	}

}
