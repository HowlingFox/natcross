package cn.howlingox.natcross.api;

/**
 * 
 * <p>
 * 通知上次停止的统一类，为适应不同的类型进行不同的函数封装
 * </p>
 *
 * @author howlingfox
 * @since 2019-07-12 08:39:25
 */
public interface IBelongControl {

	/**
	 * 无标记通知
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:08:46
	 */
	default void noticeStop() {
		// do no thing
	}

	/**
	 * 有标记通知
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:08:55
	 * @param socketPartKey
	 * @return
	 */
	default boolean stopSocketPart(String socketPartKey) {
		return true;
	}

}
