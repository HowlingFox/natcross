package cn.howlingox.natcross.serverside.listen.recv;

import cn.howlingox.natcross.channel.SocketChannel;
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
public class ClientHeartHandler implements IRecvHandler<InteractiveModel, InteractiveModel> {

	public static final ClientHeartHandler INSTANCE = new ClientHeartHandler();

	@Override
	public boolean proc(InteractiveModel model,
			SocketChannel<? extends InteractiveModel, ? super InteractiveModel> channel) throws Exception {
		InteractiveTypeEnum interactiveTypeEnum = InteractiveTypeEnum.getEnumByName(model.getInteractiveType());
		if (!InteractiveTypeEnum.HEART_TEST.equals(interactiveTypeEnum)) {
			return false;
		}
		InteractiveModel sendModel = InteractiveModel.of(model.getInteractiveSeq(), InteractiveTypeEnum.COMMON_REPLY,
				NatcrossResultEnum.SUCCESS.toResultModel());

		channel.writeAndFlush(sendModel);

		return true;
	}

}
