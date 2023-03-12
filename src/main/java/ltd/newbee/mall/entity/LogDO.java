package ltd.newbee.mall.entity;

import java.util.Date;
import lombok.Data;

@Data
public class LogDO {
		private String userId;
		private Date   createTime;
		
		
		private String  productName;

}