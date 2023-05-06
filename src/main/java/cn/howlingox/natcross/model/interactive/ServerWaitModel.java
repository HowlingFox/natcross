package cn.howlingox.natcross.model.interactive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * <p>
 * 服务端等待建立隧道模型
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:37:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerWaitModel {

    private String socketPartKey;

}
