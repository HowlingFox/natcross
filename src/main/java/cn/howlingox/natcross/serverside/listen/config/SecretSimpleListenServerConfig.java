package cn.howlingox.natcross.serverside.listen.config;

import java.io.IOException;
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
 * 交互加密配置
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:52:51
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SecretSimpleListenServerConfig extends SimpleListenServerConfig {

	private String tokenKey;
	private Key aesKey;

	public SecretSimpleListenServerConfig(Integer listenPort) {
		super(listenPort);
	}

	@Override
	protected SocketChannel<? extends InteractiveModel, ? super InteractiveModel> newControlSocketChannel(
			Socket socket) {
		SecretInteractiveChannel channel = new SecretInteractiveChannel();
		channel.setCharset(this.getCharset());
		channel.setTokenKey(this.tokenKey);
		channel.setAesKey(this.aesKey);
		try {
			channel.setSocket(socket);
		} catch (IOException e) {
			return null;
		}
		return channel;
	}

	/**
	 * BASE64格式设置交互加密密钥
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:52:25
	 * @param key
	 */
	public void setBaseAesKey(String aesKey) {
		this.aesKey = AESUtil.createKeyByBase64(aesKey);
	}

}
