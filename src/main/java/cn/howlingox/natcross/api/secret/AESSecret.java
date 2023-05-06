package cn.howlingox.natcross.api.secret;

import java.security.Key;

import lombok.Data;
import cn.howlingox.natcross.utils.AESUtil;

/**
 * <p>
 * AES加密方式
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:01:40
 */
@Data
public class AESSecret implements ISecret {

	private Key aesKey;

	@Override
	public byte[] encrypt(byte[] content, int offset, int len) throws Exception {
		return AESUtil.encrypt(this.aesKey, content, offset, len);
	}

	@Override
	public byte[] decrypt(byte[] result) throws Exception {
		return AESUtil.decrypt(this.aesKey, result);
	}

	/**
	 * 设置密钥
	 *
	 * @param aesKey
	 * @author howlingfox
	 * @since 2021-04-26 16:39:21
	 */
	public void setBaseAesKey(String aesKey) {
		this.aesKey = AESUtil.createKeyByBase64(aesKey);
	}

}
