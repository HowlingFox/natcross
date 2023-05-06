package cn.howlingox.natcross.serverside.client.config;

import java.net.Socket;
import java.security.Key;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import cn.howlingox.natcross.channel.SecretInteractiveChannel;
import cn.howlingox.natcross.channel.SocketChannel;
import cn.howlingox.natcross.model.InteractiveModel;
import cn.howlingox.natcross.utils.AESUtil;

/**
 * 
 * <p>
 * 隧道过程加密的配置类
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:44:42
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SecretSimpleClientServiceConfig extends SimpleClientServiceConfig {

	/**
	 * 签名混淆key
	 */
	private String tokenKey;
	/**
	 * 隧道过程加密key AES
	 */
	private Key aesKey;

	public SecretSimpleClientServiceConfig(Integer listenPort) {
		super(listenPort);
	}

	@Override
	public SocketChannel<? extends InteractiveModel, ? super InteractiveModel> newSocketChannel(Socket listenSocket)
			throws Exception {
		SecretInteractiveChannel channel = new SecretInteractiveChannel();
		channel.setCharset(this.getCharset());
		channel.setTokenKey(this.tokenKey);
		channel.setAesKey(this.aesKey);
		channel.setSocket(listenSocket);
		return channel;
	}

	/**
	 * BASE64格式设置隧道加密密钥
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:45:25
	 * @param aesKey
	 */
	public void setBaseAesKey(String aesKey) {
		this.aesKey = AESUtil.createKeyByBase64(aesKey);
	}

}
