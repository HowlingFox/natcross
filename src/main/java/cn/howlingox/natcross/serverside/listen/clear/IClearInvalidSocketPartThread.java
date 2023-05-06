package cn.howlingox.natcross.serverside.listen.clear;

/**
 * 
 * <p>
 * 清理无效端口 线程
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:50:09
 */
public interface IClearInvalidSocketPartThread extends Runnable {

	/**
	 * 启动
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:50:46
	 */
	void start();

	/**
	 * 退出
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:50:51
	 */
	void cancel();

}
