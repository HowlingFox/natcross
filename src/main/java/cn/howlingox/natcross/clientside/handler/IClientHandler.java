package cn.howlingox.natcross.clientside.handler;

import cn.howlingox.natcross.clientside.adapter.IClientAdapter;

/**
 * <p>
 * 接收处理器
 * </p>
 *
 * @author howlingfox
 * @since 2023-04-15 11:13:20
 */
public interface IClientHandler<R, W> {

	/**
	 * 执行方法
	 *
	 * @param model
	 * @param clientAdapter
	 * @return
	 * @throws Exception
	 * @author howlingfox
	 * @since 2021-04-26 16:47:22
	 */
	boolean proc(R model, IClientAdapter<? extends R, ? super W> clientAdapter) throws Exception;

}
