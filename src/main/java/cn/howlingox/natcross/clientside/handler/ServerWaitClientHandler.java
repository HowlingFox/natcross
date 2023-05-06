package cn.howlingox.natcross.clientside.handler;

import cn.howlingox.natcross.clientside.adapter.IClientAdapter;
import cn.howlingox.natcross.model.InteractiveModel;
import cn.howlingox.natcross.model.enumeration.InteractiveTypeEnum;
import cn.howlingox.natcross.model.interactive.ServerWaitModel;

/**
 * <p>
 * 心跳检测
 * </p>
 *
 * @author howlingfox
 * @since 2023-04-15 13:02:09
 */
public class ServerWaitClientHandler implements IClientHandler<InteractiveModel, InteractiveModel> {

	public static final ServerWaitClientHandler INSTANCE = new ServerWaitClientHandler();

	@Override
	public boolean proc(InteractiveModel model,
			IClientAdapter<? extends InteractiveModel, ? super InteractiveModel> clientAdapter) throws Exception {
		InteractiveTypeEnum interactiveTypeEnum = InteractiveTypeEnum.getEnumByName(model.getInteractiveType());
		if (!InteractiveTypeEnum.SERVER_WAIT_CLIENT.equals(interactiveTypeEnum)) {
			return false;
		}

		ServerWaitModel serverWaitModel = model.getData().toJavaObject(ServerWaitModel.class);
		clientAdapter.clientConnect(serverWaitModel);

		return true;
	}

}
