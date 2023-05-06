package cn.howlingox.natcross.serverside.client.adapter;

import java.net.Socket;

/**
 * 
 * <p>
 * 客户端服务适配器
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:40:35
 */
public interface IClientServiceAdapter {

    /**
     * 处理方法
     * 
     * @author howlingfox
     * @since 2022-02-13 16:40:43
     * @param listenSocket
     * @throws Exception
     */
    void procMethod(Socket listenSocket) throws Exception;

}
