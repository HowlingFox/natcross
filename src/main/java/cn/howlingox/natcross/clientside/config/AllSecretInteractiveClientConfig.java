package cn.howlingox.natcross.clientside.config;

import java.security.Key;

import cn.howlingox.natcross.api.secret.AESSecret;
import cn.howlingox.natcross.api.socketpart.AbsSocketPart;
import cn.howlingox.natcross.api.socketpart.SecretSocketPart;
import lombok.Getter;
import lombok.Setter;
import cn.howlingox.natcross.clientside.ClientControlThread;
import cn.howlingox.natcross.utils.AESUtil;

/**
 * 
 * <p>
 * 交互及隧道都加密
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 15:01:44
 */
public class AllSecretInteractiveClientConfig extends SecretInteractiveClientConfig {

    @Setter
    @Getter
    private Key passwayKey;

    @Override
    public AbsSocketPart newSocketPart(ClientControlThread clientControlThread) {
        AESSecret secret = new AESSecret();
        secret.setAesKey(this.passwayKey);
        SecretSocketPart secretSocketPart = new SecretSocketPart(clientControlThread);
        secretSocketPart.setSecret(secret);
        return secretSocketPart;
    }

    /**
     * base64格式设置密钥
     * 
     * @author howlingfox
     * @since 2022-02-13 16:29:49
     * @param key
     */
    public void setBasePasswayKey(String key) {
        this.passwayKey = AESUtil.createKeyByBase64(key);
    }

}
