package cn.howlingox.natcross.serverside.client.process;

import cn.howlingox.natcross.serverside.listen.ListenServerControl;
import cn.howlingox.natcross.serverside.listen.ServerListenThread;
import cn.howlingox.natcross.channel.SocketChannel;
import cn.howlingox.natcross.model.InteractiveModel;
import cn.howlingox.natcross.model.NatcrossResultModel;
import cn.howlingox.natcross.model.enumeration.InteractiveTypeEnum;
import cn.howlingox.natcross.model.enumeration.NatcrossResultEnum;
import cn.howlingox.natcross.model.interactive.ClientControlModel;

/**
 * 
 * <p>
 * 请求建立控制器处理方法
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:48:43
 */
public class ClientControlProcess implements IProcess {

	public static final ClientControlProcess INSTANCE = new ClientControlProcess();

	@Override
	public boolean wouldProc(InteractiveModel recvInteractiveModel) {
		InteractiveTypeEnum interactiveTypeEnum = InteractiveTypeEnum
				.getEnumByName(recvInteractiveModel.getInteractiveType());
		return InteractiveTypeEnum.CLIENT_CONTROL.equals(interactiveTypeEnum);
	}

	@Override
	public boolean processMothed(SocketChannel<? extends InteractiveModel, ? super InteractiveModel> socketChannel,
			InteractiveModel recvInteractiveModel) throws Exception {
		ClientControlModel clientControlModel = recvInteractiveModel.getData().toJavaObject(ClientControlModel.class);
		ServerListenThread serverListenThread = ListenServerControl.get(clientControlModel.getListenPort());

		if (serverListenThread == null) {
			socketChannel.writeAndFlush(InteractiveModel.of(recvInteractiveModel.getInteractiveSeq(),
					InteractiveTypeEnum.COMMON_REPLY, NatcrossResultEnum.NO_HAS_SERVER_LISTEN.toResultModel()));
			return false;
		}

		socketChannel.writeAndFlush(InteractiveModel.of(recvInteractiveModel.getInteractiveSeq(),
				InteractiveTypeEnum.COMMON_REPLY, NatcrossResultModel.ofSuccess()));

		serverListenThread.setControlSocket(socketChannel.getSocket());
		return true;
	}

}
