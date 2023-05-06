package cn.howlingox.natcross.clientside.handler;

import cn.howlingox.natcross.clientside.adapter.IClientAdapter;
import cn.howlingox.natcross.model.InteractiveModel;
import cn.howlingox.natcross.model.enumeration.InteractiveTypeEnum;
import cn.howlingox.natcross.model.enumeration.NatcrossResultEnum;

/**
 * <p>
 * 心跳检测
 * </p>
 *
 * @author howlingfox
 * @since 2023-04-15 13:02:09
 */
public class ServerHeartHandler implements IClientHandler<InteractiveModel, InteractiveModel> {

	public static final ServerHeartHandler INSTANCE = new ServerHeartHandler();

	@Override
	public boolean proc(InteractiveModel model,
			IClientAdapter<? extends InteractiveModel, ? super InteractiveModel> clientAdapter) throws Exception {
		InteractiveTypeEnum interactiveTypeEnum = InteractiveTypeEnum.getEnumByName(model.getInteractiveType());
		if (!InteractiveTypeEnum.HEART_TEST.equals(interactiveTypeEnum)) {
			return false;
		}
		InteractiveModel sendModel = InteractiveModel.of(model.getInteractiveSeq(), InteractiveTypeEnum.COMMON_REPLY,
				NatcrossResultEnum.SUCCESS.toResultModel());

		clientAdapter.getSocketChannel().writeAndFlush(sendModel);

		return true;
	}

}
