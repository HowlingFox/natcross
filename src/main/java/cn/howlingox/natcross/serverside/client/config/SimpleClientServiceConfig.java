package cn.howlingox.natcross.serverside.client.config;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import cn.howlingox.natcross.serverside.client.adapter.DefaultReadAheadPassValueAdapter;
import cn.howlingox.natcross.serverside.client.adapter.IClientServiceAdapter;
import lombok.NoArgsConstructor;
import cn.howlingox.natcross.channel.InteractiveChannel;
import cn.howlingox.natcross.channel.SocketChannel;
import cn.howlingox.natcross.model.InteractiveModel;

/**
 * 
 * <p>
 * 简单交互的客户端服务配置
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:45:47
 */
@NoArgsConstructor
public class SimpleClientServiceConfig implements IClientServiceConfig<InteractiveModel, InteractiveModel> {

	private Integer listenPort;
	private IClientServiceAdapter clientServiceAdapter = new DefaultReadAheadPassValueAdapter(this);
	private Charset charset = StandardCharsets.UTF_8;

	public SimpleClientServiceConfig(Integer listenPort) {
		this.listenPort = listenPort;
	}

	public void setListenPort(int listenPort) {
		this.listenPort = listenPort;
	}

	@Override
	public Integer getListenPort() {
		return this.listenPort;
	}

	@Override
	public ServerSocket createServerSocket() throws Exception {
		ServerSocketChannel openServerSocketChannel = SelectorProvider.provider().openServerSocketChannel();
		openServerSocketChannel.bind(new InetSocketAddress(this.getListenPort()));
		return openServerSocketChannel.socket();
//		return new ServerSocket(this.getListenPort());
	}

	/**
	 * 设置适配器
	 * 
	 * @author howlingfox
	 * @since 2023-01-09 16:19:16
	 * @param clientServiceAdapter
	 */
	public void setClientServiceAdapter(IClientServiceAdapter clientServiceAdapter) {
		this.clientServiceAdapter = clientServiceAdapter;
	}

	@Override
	public IClientServiceAdapter getClientServiceAdapter() {
		return this.clientServiceAdapter;
	}

	@Override
	public SocketChannel<? extends InteractiveModel, ? super InteractiveModel> newSocketChannel(Socket listenSocket)
			throws Exception {
		InteractiveChannel channel = new InteractiveChannel();
		channel.setSocket(listenSocket);
		channel.setCharset(this.charset);
		return channel;
	}

	@Override
	public Charset getCharset() {
		return this.charset;
	}

	/**
	 * 设置字符编码
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:46:06
	 * @param charset
	 */
	public void setCharset(Charset charset) {
		this.charset = charset;
	}

}
