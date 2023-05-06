package cn.howlingox.natcross.clientside.config;

import java.io.IOException;
import java.net.Socket;
import java.security.Key;

import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.howlingox.natcross.channel.SecretInteractiveChannel;
import cn.howlingox.natcross.channel.SocketChannel;
import cn.howlingox.natcross.model.InteractiveModel;
import cn.howlingox.natcross.utils.AESUtil;

/**
 * 
 * <p>
 * 交互加密的配置方案（AES加密）
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:32:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SecretInteractiveClientConfig extends InteractiveClientConfig {

    private String tokenKey;
    private Key aesKey;

    @Override
    public SocketChannel<? extends InteractiveModel, ? super InteractiveModel> newClientChannel() {
        SecretInteractiveChannel channel = new SecretInteractiveChannel();

        channel.setCharset(this.getCharset());
        channel.setTokenKey(this.tokenKey);
        channel.setAesKey(this.aesKey);

        try {
            Socket socket = new Socket(this.getClientServiceIp(), this.getClientServicePort());
            channel.setSocket(socket);
        } catch (IOException e) {
            return null;
        }
        return channel;
    }

    /**
     * 设置交互密钥
     * 
     * @author howlingfox
     * @since 2022-02-13 16:32:37
     * @param aesKey
     */
    public void setBaseAesKey(String aesKey) {
        this.aesKey = AESUtil.createKeyByBase64(aesKey);
    }

}
