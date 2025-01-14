package cn.howlingox.natcross;

import cn.howlingox.natcross.clientside.ClientControlThread;
import cn.howlingox.natcross.clientside.config.AllSecretInteractiveClientConfig;
import cn.howlingox.natcross.clientside.config.HttpRouteClientConfig;
import cn.howlingox.natcross.clientside.config.InteractiveClientConfig;
import cn.howlingox.natcross.clientside.config.SecretInteractiveClientConfig;
import cn.howlingox.natcross.model.HttpRoute;
import cn.howlingox.natcross.CommonConstants.ListenDest;

/**
 * 
 * <p>
 * 客户端，放在内网侧
 * </p>
 *
 * @author howlingfox
 * @since 2023-03-05 16:26:44
 */
public class ClientApp {

	public static void main(String[] args) throws Exception {
//		simple();
		secret();
//		secretAll();
//		secretHttpRoute();
	}

	/**
	 * http路由
	 * 
	 * 默认使用交互加密、数据不加密的策略
	 */
	public static void secretHttpRoute() throws Exception {
		HttpRoute[] routes = new HttpRoute[] {
				//集群
				HttpRoute.of(true,"localhost", "192.168.43.122", 5200),
//				HttpRoute.of("localhost", "192.168.43.103", 5200),
//				HttpRoute.of("localhost", "192.168.43.107", 5200),
//				HttpRoute.of("localhost", "192.168.43.102", 5200)
		};

		for (ListenDest model : CommonConstants.listenDestArray) {
			SecretInteractiveClientConfig baseConfig = new SecretInteractiveClientConfig();

			// 设置服务端IP和端口
			baseConfig.setClientServiceIp(CommonConstants.serviceIp);
			baseConfig.setClientServicePort(CommonConstants.servicePort);
			// 设置对外暴露的端口，该端口的启动在服务端，这里只是表明要跟服务端的那个监听服务对接
			baseConfig.setListenServerPort(model.listenPort);

			// 设置交互密钥和签名key
			baseConfig.setBaseAesKey(CommonConstants.aesKey);
			baseConfig.setTokenKey(CommonConstants.tokenKey);

			HttpRouteClientConfig config = new HttpRouteClientConfig(baseConfig);
			config.addRoute(routes);

			new ClientControlThread(config).createControl();
		}
	}

	/**
	 * 交互、隧道都进行加密
	 */
	public static void secretAll() throws Exception {
		for (ListenDest model : CommonConstants.listenDestArray) {
			AllSecretInteractiveClientConfig config = new AllSecretInteractiveClientConfig();

			// 设置服务端IP和端口
			config.setClientServiceIp(CommonConstants.serviceIp);
			config.setClientServicePort(CommonConstants.servicePort);
			// 设置对外暴露的端口，该端口的启动在服务端，这里只是表明要跟服务端的那个监听服务对接
			config.setListenServerPort(model.listenPort);
			// 设置要暴露的目标IP和端口
			config.setDestIp(model.destIp);
			config.setDestPort(model.destPort);

			// 设置交互密钥和签名key
			config.setBaseAesKey(CommonConstants.aesKey);
			config.setTokenKey(CommonConstants.tokenKey);
			// 设置隧道交互密钥
			config.setBasePasswayKey(CommonConstants.aesKey);

			new ClientControlThread(config).createControl();
		}
	}

	/**
	 * 交互加密，即交互验证
	 */
	public static void secret() throws Exception {
		for (ListenDest model : CommonConstants.listenDestArray) {
			SecretInteractiveClientConfig config = new SecretInteractiveClientConfig();

			// 设置服务端IP和端口
			config.setClientServiceIp(CommonConstants.serviceIp);
			config.setClientServicePort(CommonConstants.servicePort);
			// 设置对外暴露的端口，该端口的启动在服务端，这里只是表明要跟服务端的那个监听服务对接
			config.setListenServerPort(model.listenPort);
			// 设置要暴露的目标IP和端口
			config.setDestIp(model.destIp);
			config.setDestPort(model.destPort);

			// 设置交互密钥和签名key
			config.setBaseAesKey(CommonConstants.aesKey);
			config.setTokenKey(CommonConstants.tokenKey);

			new ClientControlThread(config).createControl();
		}
	}

	/**
	 * 无加密、无验证
	 */
	public static void simple() throws Exception {
		for (ListenDest model : CommonConstants.listenDestArray) {
			InteractiveClientConfig config = new InteractiveClientConfig();

			// 设置服务端IP和端口
			config.setClientServiceIp(CommonConstants.serviceIp);
			config.setClientServicePort(CommonConstants.servicePort);
			// 设置对外暴露的端口，该端口的启动在服务端，这里只是表明要跟服务端的那个监听服务对接
			config.setListenServerPort(model.listenPort);
			// 设置要暴露的目标IP和端口
			config.setDestIp(model.destIp);
			config.setDestPort(model.destPort);

			new ClientControlThread(config).createControl();
		}
	}

}
