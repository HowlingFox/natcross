package cn.howlingox.natcross.serverside.listen.config;

import java.security.Key;

import cn.howlingox.natcross.api.secret.AESSecret;
import cn.howlingox.natcross.api.socketpart.AbsSocketPart;
import cn.howlingox.natcross.api.socketpart.SecretSocketPart;
import lombok.Getter;
import lombok.Setter;
import cn.howlingox.natcross.serverside.listen.ServerListenThread;
import cn.howlingox.natcross.utils.AESUtil;

/**
 * 
 * <p>
 * 交互及隧道都加密
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 15:05:55
 */
public class AllSecretSimpleListenServerConfig extends SecretSimpleListenServerConfig {

	@Setter
	@Getter
	private Key passwayKey;

	public AllSecretSimpleListenServerConfig(Integer listenPort) {
		super(listenPort);
	}

	@Override
	public AbsSocketPart newSocketPart(ServerListenThread serverListenThread) {
		AESSecret secret = new AESSecret();
		secret.setAesKey(this.passwayKey);
		SecretSocketPart secretSocketPart = new SecretSocketPart(serverListenThread);
		secretSocketPart.setSecret(secret);
		return secretSocketPart;
	}

	/**
	 * BASE64格式设置隧道加密密钥
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:52:25
	 * @param key
	 */
	public void setBasePasswayKey(String key) {
		this.passwayKey = AESUtil.createKeyByBase64(key);
	}

}
