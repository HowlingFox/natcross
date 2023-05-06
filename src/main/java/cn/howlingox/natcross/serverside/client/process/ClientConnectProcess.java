package cn.howlingox.natcross.serverside.client.process;

import cn.howlingox.natcross.serverside.listen.ListenServerControl;
import cn.howlingox.natcross.channel.SocketChannel;
import cn.howlingox.natcross.common.CommonFormat;
import cn.howlingox.natcross.model.InteractiveModel;
import cn.howlingox.natcross.model.NatcrossResultModel;
import cn.howlingox.natcross.model.enumeration.InteractiveTypeEnum;
import cn.howlingox.natcross.model.enumeration.NatcrossResultEnum;
import cn.howlingox.natcross.model.interactive.ClientConnectModel;
import cn.howlingox.natcross.serverside.listen.ServerListenThread;

/**
 * 
 * <p>
 * 请求建立隧道处理器
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:48:25
 */
public class ClientConnectProcess implements IProcess {

	public static final ClientConnectProcess INSTANCE = new ClientConnectProcess();

	@Override
	public boolean wouldProc(InteractiveModel recvInteractiveModel) {
		InteractiveTypeEnum interactiveTypeEnum = InteractiveTypeEnum
				.getEnumByName(recvInteractiveModel.getInteractiveType());
		return InteractiveTypeEnum.CLIENT_CONNECT.equals(interactiveTypeEnum);
	}

	@Override
	public boolean processMothed(SocketChannel<? extends InteractiveModel, ? super InteractiveModel> socketChannel,
			InteractiveModel recvInteractiveModel) throws Exception {
		ClientConnectModel clientConnectModel = recvInteractiveModel.getData().toJavaObject(ClientConnectModel.class);
		Integer listenPort = CommonFormat.getSocketPortByPartKey(clientConnectModel.getSocketPartKey());

		ServerListenThread serverListenThread = ListenServerControl.get(listenPort);

		if (serverListenThread == null) {
			socketChannel.writeAndFlush(InteractiveModel.of(recvInteractiveModel.getInteractiveSeq(),
					InteractiveTypeEnum.COMMON_REPLY, NatcrossResultEnum.NO_HAS_SERVER_LISTEN.toResultModel()));
			return false;
		}

		// 回复设置成功，如果doSetPartClient没有找到对应的搭档，则直接按关闭处理
		socketChannel.writeAndFlush(InteractiveModel.of(recvInteractiveModel.getInteractiveSeq(),
				InteractiveTypeEnum.COMMON_REPLY, NatcrossResultModel.ofSuccess()));

		boolean doSetPartClient = serverListenThread.doSetPartClient(clientConnectModel.getSocketPartKey(),
				socketChannel.getSocket());
		if (doSetPartClient) {
			// 若设置成功，则上层无需关闭
			return true;
		} else {
			// 若设置失败，则由上层关闭
			return false;
		}
	}

}
