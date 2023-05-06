package cn.howlingox.natcross.nio;

import java.nio.channels.SelectionKey;

/**
 * <p>
 * nio 执行器
 * </p>
 *
 * @author howlingfox
 * @since 2021-04-12 17:51:37
 */
@FunctionalInterface
public interface INioProcesser {

	/**
	 * 需要执行的方法
	 *
	 * @param key
	 * @author howlingfox
	 * @since 2021-04-12 17:54:50
	 */
	public void proccess(SelectionKey key);

}
