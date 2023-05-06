package cn.howlingox.natcross.serverside.listen.config;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

import cn.howlingox.natcross.api.socketpart.AbsSocketPart;
import com.alibaba.fastjson.JSONObject;

import cn.howlingox.natcross.serverside.listen.ServerListenThread;
import cn.howlingox.natcross.serverside.listen.clear.IClearInvalidSocketPartThread;
import cn.howlingox.natcross.serverside.listen.control.IControlSocket;
import cn.howlingox.natcross.serverside.listen.control.MultControlSocket;

/**
 * <p>
 * 多客户端；监听服务配置
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:53:17
 */
public class MultControlListenServerConfig implements IListenServerConfig {

	protected final IListenServerConfig baseConfig;

	protected final MultControlSocket multControlSocket = new MultControlSocket();

	public MultControlListenServerConfig(IListenServerConfig baseConfig) {
		this.baseConfig = baseConfig;
	}

	@Override
	public ServerSocket createServerSocket() throws Exception {
		return this.baseConfig.createServerSocket();
	}

	@Override
	public IControlSocket newControlSocket(Socket socket, JSONObject config) {
		IControlSocket controlSocket = this.baseConfig.newControlSocket(socket, config);
		multControlSocket.addControlSocket(controlSocket);
		return multControlSocket;
	}

	@Override
	public IClearInvalidSocketPartThread newClearInvalidSocketPartThread(ServerListenThread serverListenThread) {
		return this.baseConfig.newClearInvalidSocketPartThread(serverListenThread);
	}

	@Override
	public AbsSocketPart newSocketPart(ServerListenThread serverListenThread) {
		return this.baseConfig.newSocketPart(serverListenThread);
	}

	@Override
	public Integer getListenPort() {
		return this.baseConfig.getListenPort();
	}

	@Override
	public Charset getCharset() {
		return this.baseConfig.getCharset();
	}

}
