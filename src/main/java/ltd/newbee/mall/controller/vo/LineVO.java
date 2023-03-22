package ltd.newbee.mall.controller.vo;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LineVO {
		
		@Builder.Default
		private List<Object> series = Lists.newArrayList();
		@Builder.Default
		private List<Object> xAxis  = Lists.newArrayList();
		@Builder.Default
		private List<Object> yAxis  = Lists.newArrayList();
		
		private String             title;
		@Builder.Default
		private List<KeyWithValue> result = Lists.newArrayList();
		
		
}