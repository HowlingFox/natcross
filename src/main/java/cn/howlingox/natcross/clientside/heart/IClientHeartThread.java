package cn.howlingox.natcross.clientside.heart;

/**
 * 
 * <p>
 * 心跳测试线程
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:33:03
 */
public interface IClientHeartThread {

    /**
     * 是否还活着
     * 
     * @author howlingfox
     * @since 2022-02-13 16:33:15
     * @return
     */
    boolean isAlive();

    /**
     * 退出
     * 
     * @author howlingfox
     * @since 2022-02-13 16:33:24
     */
    void cancel();

    /**
     * 开始
     * 
     * @author howlingfox
     * @since 2022-02-13 16:33:29
     */
    void start();

}
