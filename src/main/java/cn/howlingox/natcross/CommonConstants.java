package cn.howlingox.natcross;

/**
 * <p>
 * 公共参数
 * </p>
 *
 * @author howlingfox
 * @since 2023-02-18 12:29:01
 */
public final class CommonConstants {

	// 服务端地址，支持IP或域名，这个根据服务端放的网络位置进行设置
	public static final String serviceIp = "howlingfox.cn";
	// 服务端口，客户端将与此端口连接进行数据传输  临时分配socket端口
	public static final int servicePort = 65535;
	// 映射对
	public static ListenDest[] listenDestArray = new ListenDest[] {
			/**
			 * 因NAT网内CLIENT可以正常连接到SERVER端，并且能够保持一段时间的长连接，
			 * 则由CLIENT发起连接，建立SOCKET对，在SERVER收到外部请求时，
			 * 可以通过已经建立好的SOCKET将数据传输给CLIENT，CLIENT使用相同的方式将数据发送给指定的网络程序，
			 * 网络程序回发数据后则按原路返回给请求方。
			 * 参数解释：
			 * listenPort：服务端对外访问端口（一般是一台固定IP的服务器）
			 * destIp：需要代理的服务地址
			 * destPort：需要代理的服务端口
			*/
			// 解释：监听本地8801端口，转发到服务端8080端口
			ListenDest.of(8080, "127.0.0.1", 8801),
//			// sqlserver
//			ListenDest.of(8081, "192.168.56.102", 1443),
//			//redis
//			ListenDest.of(8082, "192.168.56.102", 6380),
	};

	// 交互密钥 AES
	public static final String aesKey = "U2FsdGVkX19iaon0F+Ksk2DxSqsit5dk/+Yt2IRgACI=";
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
