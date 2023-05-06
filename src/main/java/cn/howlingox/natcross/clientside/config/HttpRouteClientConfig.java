package cn.howlingox.natcross.clientside.config;

import java.net.Socket;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.StampedLock;

import cn.howlingox.natcross.api.socketpart.AbsSocketPart;
import cn.howlingox.natcross.api.socketpart.HttpRouteSocketPart;
import cn.howlingox.natcross.clientside.adapter.IClientAdapter;
import cn.howlingox.natcross.clientside.adapter.InteractiveSimpleClientAdapter;
import cn.howlingox.natcross.clientside.handler.CommonReplyHandler;
import cn.howlingox.natcross.clientside.handler.ServerHeartHandler;
import cn.howlingox.natcross.clientside.handler.ServerWaitClientHandler;
import cn.howlingox.natcross.clientside.heart.IClientHeartThread;
import cn.howlingox.natcross.model.HttpRoute;
import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import cn.howlingox.natcross.api.IHttpRouting;
import cn.howlingox.natcross.channel.SocketChannel;
import cn.howlingox.natcross.clientside.ClientControlThread;
import cn.howlingox.natcross.model.InteractiveModel;
import cn.howlingox.natcross.utils.Assert;

/**
 * 
 * <p>
 * http路由客户端配置
 * </p>
 *
 * @author howlingfox
 * @since 2023-04-24 10:09:46
 */
@Slf4j
public class HttpRouteClientConfig extends InteractiveClientConfig implements IHttpRouting {

	private final InteractiveClientConfig baseConfig;

	@Getter
	private HttpRoute masterRoute = null;
	// 不可对routeMap进行修改，只得以重新赋值方式重设
	private Map<String, HttpRoute> routeMap = Collections.emptyMap();

	private final StampedLock routeLock = new StampedLock();

	public HttpRouteClientConfig() {
		this.baseConfig = new InteractiveClientConfig();
	}

	public HttpRouteClientConfig(InteractiveClientConfig baseConfig) {
		this.baseConfig = baseConfig;
	}

	/**
	 * 预设置
	 * 
	 * @author howlingfox
	 * @since 2023-04-24 11:37:35
	 * @param masterRoute
	 * @param routeMap
	 */
	public void presetRoute(HttpRoute masterRoute, LinkedHashMap<String, HttpRoute> routeMap) {
		Objects.requireNonNull(masterRoute, "主路由不得为空");
		Objects.requireNonNull(routeMap, "路由表不得为null");

		LinkedHashMap<String, HttpRoute> routeMapTemp = new LinkedHashMap<>(routeMap);

		StampedLock routeLock = this.routeLock;
		long stamp = routeLock.writeLock();
		try {
			this.masterRoute = masterRoute;
			this.routeMap = routeMapTemp;
		} finally {
			routeLock.unlockWrite(stamp);
		}

	}

	/**
	 * 获取路由表
	 *
	 * @return {@link Collections#unmodifiableMap(Map)} 不得对对象进行修改
	 * @author howlingfox
	 * @since 2021-04-26 09:36:28
	 */
	public Map<String, HttpRoute> getRouteMap() {
		return Collections.unmodifiableMap(this.routeMap);
	}

	/**
	 * 增加路由
	 * 
	 * @author howlingfox
	 * @since 2023-04-24 10:42:34
	 * @param httpRoutes
	 */
	public void addRoute(HttpRoute... httpRoutes) {
		if (httpRoutes == null || httpRoutes.length < 1) {
			return;
		}

		StampedLock routeLock = this.routeLock;
		long stamp = routeLock.writeLock();
		try {
			if (Objects.isNull(this.masterRoute)) {
				this.masterRoute = httpRoutes[0];
			}

			LinkedHashMap<String, HttpRoute> routeMap = new LinkedHashMap<>(this.routeMap);
			for (HttpRoute model : httpRoutes) {
				routeMap.put(model.getHost(), model);
				if (model.isMaster()) {
					this.masterRoute = model;
				}
			}

			this.routeMap = routeMap;
		} finally {
			routeLock.unlockWrite(stamp);
		}
	}

	/**
	 * 清理路由
	 * 
	 * @author howlingfox
	 * @since 2023-04-24 10:43:05
	 * @param hosts
	 */
	public void clearRoute(String... hosts) {
		if (Objects.isNull(hosts) || hosts.length < 1) {
			return;
		}

		StampedLock routeLock = this.routeLock;
		long stamp = routeLock.writeLock();
		try {
			LinkedHashMap<String, HttpRoute> routeMap = new LinkedHashMap<>(this.routeMap);
			HttpRoute masterRoute = this.masterRoute;

			String masterRouteHost = masterRoute.getHost();
			for (String host : hosts) {
				routeMap.remove(host);
				if (StringUtils.equals(masterRouteHost, host)) {
					masterRoute = null;

					// 减少string比较复杂度
					masterRouteHost = null;
				}
			}

			if (Objects.isNull(masterRoute)) {
				Iterator<HttpRoute> iterator = routeMap.values().iterator();
				if (iterator.hasNext()) {
					// 先将第一个设置为主路由，再遍历所有，如果是主标志则设置为主，以最后的主为准
					masterRoute = iterator.next();

					while (iterator.hasNext()) {
						HttpRoute model = iterator.next();
						if (model.isMaster()) {
							// 因使用的LinkedHashMap，就是让其符合初期定义，将后续加入有isMaster标志的路由设置为masterRoute
							masterRoute = model;
						}
					}

				} else {
					log.warn("{}:{} 路由是空的，若需要重新设置，请使用preset进行设置", this.getClientServiceIp(),
							this.getClientServicePort());
				}
			}

			this.masterRoute = masterRoute;
			this.routeMap = routeMap;
		} finally {
			routeLock.unlockWrite(stamp);
		}

	}

	@Override
	public void setDestIpPort(String destIp, Integer destPort) {
		// do nothing
	}

	@Override
	public IClientHeartThread newClientHeartThread(ClientControlThread clientControlThread) {
		return this.baseConfig.newClientHeartThread(clientControlThread);
	}

	@Override
	public IClientAdapter<InteractiveModel, InteractiveModel> newCreateControlAdapter(
			ClientControlThread clientControlThread) {
		InteractiveSimpleClientAdapter simpleClientAdapter = new InteractiveSimpleClientAdapter(clientControlThread,
				this);
		simpleClientAdapter.addMessageHandler(CommonReplyHandler.INSTANCE);
		simpleClientAdapter.addMessageHandler(ServerHeartHandler.INSTANCE);
		simpleClientAdapter.addMessageHandler(ServerWaitClientHandler.INSTANCE);
		return simpleClientAdapter;
	}

	@Override
	public SocketChannel<? extends InteractiveModel, ? super InteractiveModel> newClientChannel() {
		return this.baseConfig.newClientChannel();
	}

	@Override
	public AbsSocketPart newSocketPart(ClientControlThread clientControlThread) {
		HttpRouteSocketPart httpRouteSocketPart = new HttpRouteSocketPart(clientControlThread, this);

		httpRouteSocketPart.setStreamCacheSize(this.getStreamCacheSize());

		return httpRouteSocketPart;
	}

	@Override
	public HttpRoute pickEffectiveRoute(String host) {

		StampedLock routeLock = this.routeLock;
		long stamp = routeLock.tryOptimisticRead();

		Map<String, HttpRoute> routeMap = this.routeMap;
		HttpRoute masterRoute = this.masterRoute;

		if (!routeLock.validate(stamp)) {
			stamp = routeLock.readLock();
			try {
				routeMap = this.routeMap;
				masterRoute = this.masterRoute;
			} finally {
				routeLock.unlockRead(stamp);
			}
		}

		HttpRoute httpRoute = routeMap.get(host);
		if (Objects.isNull(httpRoute)) {
			httpRoute = masterRoute;
		}

		Assert.state(Objects.nonNull(httpRoute), "未能获取有效的路由");

		return httpRoute;
	};

	@Override
	public Socket newDestSocket() throws Exception {
		java.nio.channels.SocketChannel openSocketChannel = SelectorProvider.provider().openSocketChannel();
		return openSocketChannel.socket();
//		return new Socket();
	}

	@Override
	public String getClientServiceIp() {
		return this.baseConfig.getClientServiceIp();
	}

	@Override
	public void setClientServiceIp(String clientServiceIp) {
		this.baseConfig.setClientServiceIp(clientServiceIp);
	}

	@Override
	public Integer getClientServicePort() {
		return this.baseConfig.getClientServicePort();
	}

	@Override
	public void setClientServicePort(Integer clientServicePort) {
		this.baseConfig.setClientServicePort(clientServicePort);
	}

	@Override
	public Integer getListenServerPort() {
		return this.baseConfig.getListenServerPort();
	}

	@Override
	public void setListenServerPort(Integer listenServerPort) {
		this.baseConfig.setListenServerPort(listenServerPort);
	}

	@Override
	public String getDestIp() {
		return null;
	}

	@Override
	public void setDestIp(String destIp) {
		// do nothing
	}

	@Override
	public Integer getDestPort() {
		return null;
	}

	@Override
	public void setDestPort(Integer destPort) {
		// do nothing
	}

	@Override
	public Charset getCharset() {
		return this.baseConfig.getCharset();
	}

	@Override
	public void setCharset(Charset charset) {
		this.baseConfig.setCharset(charset);
	}

	@Override
	public int getStreamCacheSize() {
		return this.baseConfig.getStreamCacheSize();
	}

	@Override
	public void setStreamCacheSize(int streamCacheSize) {
		this.baseConfig.setStreamCacheSize(streamCacheSize);
	}

}
