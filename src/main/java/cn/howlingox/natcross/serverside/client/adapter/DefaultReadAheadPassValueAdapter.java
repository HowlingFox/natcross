package cn.howlingox.natcross.serverside.client.adapter;

import cn.howlingox.natcross.model.InteractiveModel;
import cn.howlingox.natcross.serverside.client.config.IClientServiceConfig;
import cn.howlingox.natcross.serverside.client.handler.DefaultInteractiveProcessHandler;

/**
 * 
 * <p>
 * 默认的预读后处理适配器
 * </p>
 *
 * @author howlingfox
 * @since 2021-04-26 17:06:25
 */
public class DefaultReadAheadPassValueAdapter extends ReadAheadPassValueAdapter<InteractiveModel, InteractiveModel> {

	public DefaultReadAheadPassValueAdapter(IClientServiceConfig<InteractiveModel, InteractiveModel> config) {
		super(config);
		this.addLast(DefaultInteractiveProcessHandler.INSTANCE);
	}

}
