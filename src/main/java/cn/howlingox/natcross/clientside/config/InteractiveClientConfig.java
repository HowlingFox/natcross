package cn.howlingox.natcross.clientside.config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import cn.howlingox.natcross.api.socketpart.AbsSocketPart;
import cn.howlingox.natcross.api.socketpart.SimpleSocketPart;
import cn.howlingox.natcross.clientside.adapter.IClientAdapter;
import cn.howlingox.natcross.clientside.adapter.InteractiveSimpleClientAdapter;
import cn.howlingox.natcross.clientside.handler.CommonReplyHandler;
import cn.howlingox.natcross.clientside.handler.ServerHeartHandler;
import cn.howlingox.natcross.clientside.handler.ServerWaitClientHandler;
import cn.howlingox.natcross.clientside.heart.ClientHeartThread;
import cn.howlingox.natcross.clientside.heart.IClientHeartThread;
import lombok.Data;
import cn.howlingox.natcross.channel.InteractiveChannel;
import cn.howlingox.natcross.channel.SocketChannel;
import cn.howlingox.natcross.clientside.ClientControlThread;
import cn.howlingox.natcross.model.InteractiveModel;

/**
 * 
 * <p>
 * 简单的以InteractiveModel为交互模型的配置
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:30:53
 */
@Data
public class InteractiveClientConfig implements IClientConfig<InteractiveModel, InteractiveModel> {

	private String clientServiceIp;
	private Integer clientServicePort;
	private Integer listenServerPort;

	private String destIp;
	private Integer destPort;

	private Charset charset = StandardCharsets.UTF_8;
	private int streamCacheSize = 8196;

	/**
	 * 心跳检测间隔（s）
	 */
	private long heartIntervalSeconds = 10L;
	/**
	 * 尝试重连次数，若超过则中断链接
	 */
	private int tryReclientCount = 10;

	@Override
	public void setDestIpPort(String destIp, Integer destPort) {
		this.destIp = destIp;
		this.destPort = destPort;
	}

	@Override
	public IClientHeartThread newClientHeartThread(ClientControlThread clientControlThread) {
		ClientHeartThread clientHeartThread = new ClientHeartThread(clientControlThread);
		clientHeartThread.setHeartIntervalSeconds(this.heartIntervalSeconds);
		clientHeartThread.setTryReclientCount(this.tryReclientCount);
		return clientHeartThread;
	}

	@Override
	public IClientAdapter<InteractiveModel, InteractiveModel> newCreateControlAdapter(
			ClientControlThread clientControlThread) {
		InteractiveSimpleClientAdapter simpleClientAdapter = new InteractiveSimpleClientAdapter(clientControlThread,
				this);
		simpleClientAdapter.addMessageHandler(CommonReplyHandler.INSTANCE);
		simpleClientAdapter.addMessageHandler(ServerHeartHandler.INSTANCE);
		simpleClientAdapter.addMessageHandler(ServerWaitClientHandler.INSTANCE);
		return simpleClientAdapter;
	}

	@Override
	public SocketChannel<? extends InteractiveModel, ? super InteractiveModel> newClientChannel() {
		InteractiveChannel interactiveChannel = new InteractiveChannel();
		try {
			java.nio.channels.SocketChannel openSocketChannel = SelectorProvider.provider().openSocketChannel();
			openSocketChannel.connect(new InetSocketAddress(this.getClientServiceIp(), this.getClientServicePort()));
			Socket socket = openSocketChannel.socket();
//			Socket socket = new Socket(this.getClientServiceIp(), this.getClientServicePort());
			interactiveChannel.setSocket(socket);
		} catch (IOException e) {
			return null;
		}

		interactiveChannel.setCharset(this.charset);

		return interactiveChannel;
	}

	@Override
	public AbsSocketPart newSocketPart(ClientControlThread clientControlThread) {
		SimpleSocketPart simpleSocketPart = new SimpleSocketPart(clientControlThread);
		simpleSocketPart.setStreamCacheSize(this.getStreamCacheSize());
		return simpleSocketPart;
	}

}
