package ltd.newbee.mall.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zeyingshi
 * @date 2023/3/22 20:25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeyWithValue {
		private Object name;
		private Object value;

}