package ltd.newbee.mall.config;

import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: xmz
 */
@ConfigurationProperties(prefix = DataSourceProperties.DS, ignoreUnknownFields = false)
@Data
public class DataSourceProperties {
		
		final static String              DS = "spring.datasource";
		private      Map<String, String> mysqlMain;
		private      Map<String, String> hive;
		private      Map<String, String> commonConfig;
}