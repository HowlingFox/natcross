package cn.howlingox.natcross.model.interactive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * <p>
 * 请求建立隧道模型
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:36:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientConnectModel {

    private String socketPartKey;

}
