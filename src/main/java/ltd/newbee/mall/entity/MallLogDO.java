package ltd.newbee.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

/**
 * @author zeyingshi
 * @date 2023/3/15 07:53
 */
@Data
@Builder
@TableName(value = "mall_log")
public class MallLogDO implements Serializable {
		
		@TableField(value = "user_name")
		private String userName;
		
		@TableField(value = "create_at")
		private Date createAt;
		
		@TableField(value = "product_name")
		private String productName;
		
		@TableField(value = "op")
		private String op;
		
		private static final long serialVersionUID = 1L;
}