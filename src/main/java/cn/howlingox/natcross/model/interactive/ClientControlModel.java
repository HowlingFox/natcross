package cn.howlingox.natcross.model.interactive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * <p>
 * 请求建立控制器模型
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:37:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientControlModel {

    private Integer listenPort;

}
