package cn.howlingox.natcross.api.secret;

/**
 * 
 * <p>
 * 加密方法
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:01:28
 */
public interface ISecret {

	/**
	 * 加密数据
	 *
	 * @param content
	 * @param offset
	 * @param len
	 * @return
	 * @throws Exception
	 * @author howlingfox
	 * @since 2021-04-26 16:38:46
	 */
	byte[] encrypt(byte[] content, int offset, int len) throws Exception;

	/**
	 * 解密数据
	 *
	 * @param result
	 * @return
	 * @throws Exception
	 * @author howlingfox
	 * @since 2021-04-26 16:39:07
	 */
	byte[] decrypt(byte[] result) throws Exception;

}
