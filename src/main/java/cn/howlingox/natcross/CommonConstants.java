package cn.howlingox.natcross;

/**
 * <p>
 * 公共参数
 * </p>
 *
 * @author howlingfox
 * @since 2023-04-10 12:29:01
 */
public final class CommonConstants {

	// 服务端地址，支持IP或域名，这个根据服务端放的网络位置进行设置
	public static final String serviceIp = "127.0.0.1";
	// 客户端服务的端口
	public static final int servicePort = 65530;

	// 映射对
	public static ListenDest[] listenDestArray = new ListenDest[] {
			//
			ListenDest.of(8081, "127.0.0.1", 8848),
			//
	};

	// 交互密钥 AES
	public static final String aesKey = "U2FsdGVkX18A3VB6Fj1VxKW1gNf/NqL8ltzU9/G7N0=";
	// 交互签名key
	public static final String tokenKey = "U2FsdGVkX18A3VB6Fj1VxKW1gNf/NqL2ltzU2/dG7N0=";

	/**
	 * <p>
	 * 监听、映射对
	 * </p>
	 */
	static class ListenDest {
		public static ListenDest of(int listenPort, String destIp, int destPort) {
			ListenDest model = new ListenDest();
			model.listenPort = listenPort;
			model.destIp = destIp;
			model.destPort = destPort;
			return model;
		}

		// 服务端监听的端口，外网访问服务端IP:listengPort即可进行穿透
		public int listenPort;
		// 穿透的目标，即要暴露在外网的内网IP
		public String destIp;
		// 要暴露的内网端口
		public int destPort;
	}

}
