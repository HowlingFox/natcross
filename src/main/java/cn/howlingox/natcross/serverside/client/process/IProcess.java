package cn.howlingox.natcross.serverside.client.process;

import cn.howlingox.natcross.channel.SocketChannel;
import cn.howlingox.natcross.model.InteractiveModel;

/**
 * 
 * <p>
 * 处理方法接口
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:49:06
 */
public interface IProcess {

    /**
     * 判断是否是由这个处理
     *
     * @author howlingfox
     * @since 2023-01-06 09:51:16
     * @param recvInteractiveModel
     * @return
     */
    boolean wouldProc(InteractiveModel recvInteractiveModel);

    /**
     * 处理方法，需要回复信息的，自己使用socketChannel回复
     *
     * @author howlingfox
     * @since 2023-01-06 09:56:43
     * @param socketChannel
     * @param recvInteractiveModel
     * @return 是否保持socket开启状态
     */
    boolean processMothed(SocketChannel<? extends InteractiveModel, ? super InteractiveModel> socketChannel,
            InteractiveModel recvInteractiveModel) throws Exception;

}
