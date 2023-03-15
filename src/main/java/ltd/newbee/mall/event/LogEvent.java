package ltd.newbee.mall.event;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ltd.newbee.mall.common.OpEnum;

/**
 * @author shizeying
 * @date 2022/07/18
 */
@Getter
@Setter
public class LogEvent  extends DomainEvent {

		private String userName;
		private String productName;
		private OpEnum op;
		private Date   createAt;
		private Long userId;
		private Long productId;
		
	
		
		public LogEvent(Object source) {
				super(source);
		}
		
		private LogEvent(Builder builder) {
				super(builder.source);
				source = builder.source;
				setDescribe(builder.describe);
				setUserName(builder.userName);
				setProductName(builder.productName);
				setOp(builder.op);
				setUserId(builder.userId);
				setProductId(builder.productId);
				setCreateAt(builder.createAt);
		}
		
		
		public static final class Builder {
				
				private Object source;
				private String describe;
				private String userName;
				private String productName;
				private       OpEnum op;
				private Long userId;
				private Long productId;
				private final Date   createAt =new Date();
				
				private Builder() {
				}
				
				public static Builder newBuilder() {
						return new Builder();
				}
				public Builder withUserId(Long val) {
						userId = val;
						return this;
				}
				public Builder withSource(Object val) {
						source = val;
						return this;
				}
				
				public Builder withDescribe(String val) {
						describe = val;
						return this;
				}
				public Builder withProductId(Long val) {
						productId = val;
						return this;
				}
				public Builder withUserName(String val) {
						userName = val;
						return this;
				}
				
				public Builder withProductName(String val) {
						productName = val;
						return this;
				}
				
				public Builder withOp(OpEnum val) {
						op = val;
						return this;
				}
				
			
				
				public LogEvent build() {
						return new LogEvent(this);
				}
		}
}