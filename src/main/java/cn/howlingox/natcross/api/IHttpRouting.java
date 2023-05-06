package cn.howlingox.natcross.api;

import cn.howlingox.natcross.model.HttpRoute;

/**
 * <p>
 * http 路由器
 * </p>
 *
 * @author howlingfox
 * @since 2021-04-26 08:54:44
 */
public interface IHttpRouting {

	/**
	 * 获取有效路由
	 *
	 * @param host
	 * @return
	 */
	public HttpRoute pickEffectiveRoute(String host);

}
