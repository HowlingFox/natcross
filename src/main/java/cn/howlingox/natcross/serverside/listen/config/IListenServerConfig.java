package cn.howlingox.natcross.serverside.listen.config;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

import cn.howlingox.natcross.api.socketpart.AbsSocketPart;
import com.alibaba.fastjson.JSONObject;

import cn.howlingox.natcross.serverside.listen.ServerListenThread;
import cn.howlingox.natcross.serverside.listen.clear.IClearInvalidSocketPartThread;
import cn.howlingox.natcross.serverside.listen.control.IControlSocket;

/**
 * 
 * <p>
 * 穿透监听服务配置
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:51:04
 */
public interface IListenServerConfig {

	/**
	 * 获取监听的端口
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:51:17
	 * @return
	 */
	Integer getListenPort();

	/**
	 * 新建无效端口处理线程
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:51:26
	 * @param serverListenThread
	 * @return
	 */
	IClearInvalidSocketPartThread newClearInvalidSocketPartThread(ServerListenThread serverListenThread);

	/**
	 * 创建隧道伙伴
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:51:41
	 * @param serverListenThread
	 * @return
	 */
	AbsSocketPart newSocketPart(ServerListenThread serverListenThread);

	/**
	 * 获取字符集
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:51:57
	 * @return
	 */
	Charset getCharset();

	/**
	 * 新建控制器
	 * 
	 * @author howlingfox
	 * @since 2022-02-13 16:52:05
	 * @param socketChannel
	 * @param config
	 * @return
	 */
	IControlSocket newControlSocket(Socket socket, JSONObject config);

	/**
	 * 创建监听端口
	 * 
	 * @author howlingfox
	 * @since 2023-01-09 13:24:13
	 * @return
	 * @throws Exception
	 */
	public ServerSocket createServerSocket() throws Exception;
}
