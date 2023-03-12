package ltd.newbee.mall.config;

import static ltd.newbee.mall.config.MysqlMainDruidConfig.PACKAGE;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * @author: xmz
 */
@Configuration
@EnableConfigurationProperties({DataSourceProperties.class, DataSourceCommonProperties.class})

// 将配置类注入到 bean 容器，使 ConfigurationProperties 注解类生效
@MapperScan(basePackages = PACKAGE, sqlSessionFactoryRef = "mysqlSqlSessionFactory")
public class MysqlMainDruidConfig {
		
		private static Logger logger = LoggerFactory.getLogger(MysqlMainDruidConfig.class);
		public static final String PACKAGE = "ltd.newbee.mall.dao.mysql";
    private static final String MAPPER_LOCATION = "classpath:mapper/mysql/*Mapper.xml";
		@Autowired
		private DataSourceProperties dataSourceProperties;
		
		@Autowired
		private DataSourceCommonProperties dataSourceCommonProperties;
		
		@Primary // 标明为主数据源，只能标识一个主数据源，mybatis 连接默认主数据源
		@Bean("mysqlDruidDataSource") // 新建 bean 实例
		@Qualifier("mysqlDruidDataSource")// 标识
		public DataSource dataSource() {
				DruidDataSource datasource = new DruidDataSource();
				
				// 配置数据源属性
				datasource.setUrl(dataSourceProperties.getMysqlMain().get("url"));
				datasource.setUsername(dataSourceProperties.getMysqlMain().get("username"));
				datasource.setPassword(dataSourceProperties.getMysqlMain().get("password"));
				datasource.setDriverClassName(dataSourceProperties.getMysqlMain().get("driver-class-name"));
				// 配置统一属性
				datasource.setInitialSize(dataSourceCommonProperties.getInitialSize());
				datasource.setMinIdle(dataSourceCommonProperties.getMinIdle());
				datasource.setMaxActive(dataSourceCommonProperties.getMaxActive());
				datasource.setMaxWait(dataSourceCommonProperties.getMaxWait());
				datasource.setTimeBetweenEvictionRunsMillis(
						dataSourceCommonProperties.getTimeBetweenEvictionRunsMillis());
				datasource.setMinEvictableIdleTimeMillis(
						dataSourceCommonProperties.getMinEvictableIdleTimeMillis());
				datasource.setValidationQuery(dataSourceCommonProperties.getValidationQuery());
				datasource.setTestWhileIdle(dataSourceCommonProperties.isTestWhileIdle());
				datasource.setTestOnBorrow(dataSourceCommonProperties.isTestOnBorrow());
				datasource.setTestOnReturn(dataSourceCommonProperties.isTestOnReturn());
				datasource.setPoolPreparedStatements(dataSourceCommonProperties.isPoolPreparedStatements());
				try {
						datasource.setFilters(dataSourceCommonProperties.getFilters());
				} catch (SQLException e) {
						logger.error("Druid configuration initialization filter error.", e);
				}
				return datasource;
		}
		
		@Bean("hiveGlobalConfigByOpManager")
		public GlobalConfig globalConfigByOpManager() {
				GlobalConfig globalConfig = new GlobalConfig();
				globalConfig.setBanner(false);
				GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
				dbConfig.setIdType(IdType.AUTO);
				globalConfig.setDbConfig(dbConfig);
				return globalConfig;
		}
		
		@Qualifier("mysqlSqlSessionFactory")
		@Bean("mysqlSqlSessionFactory")
		public SqlSessionFactory masterSqlSessionFactory(
				@Qualifier("mysqlDruidDataSource") DataSource dataSource) throws Exception {
				// 将 SqlSessionFactoryBean 替换为 MybatisSqlSessionFactoryBean， 否则 mybatis-plus 提示 Invalid bound statement (not found)
				
				MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
				sessionFactory.setDataSource(dataSource);
				 sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
				sessionFactory.setGlobalConfig(globalConfigByOpManager());
				MybatisConfiguration mc = new MybatisConfiguration();
				// 查看打印 sql 日志
				// org.apache.ibatis.logging.stdout.StdOutImpl.class 只能打印到控制台
				// org.apache.ibatis.logging.slf4j.Slf4jImpl.class 打印到具体的文件中
				mc.setLogImpl(org.apache.ibatis.logging.slf4j.Slf4jImpl.class);
				sessionFactory.setConfiguration(mc);
				// 添加分页插件，不加这个，分页不生效
				sessionFactory.setPlugins(paginationInterceptor());
				// 设置 mybatis 的 xml 所在位置
				return sessionFactory.getObject();
		}
		
		@Bean("paginationInterceptorByOpManager")
		public MybatisPlusInterceptor paginationInterceptor() {
				MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
				interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MARIADB));
				return interceptor;
		}
		
		
		@Bean({"mysqlSqlSessionTemplate"})
		public SqlSessionTemplate primarySqlSessionTemplate(
				@Qualifier("mysqlSqlSessionFactory") SqlSessionFactory sessionFactory) {
				return new SqlSessionTemplate(sessionFactory);
		}
		
		@Primary
		@Bean(name = "msqlTransactionManager")
		public DataSourceTransactionManager masterTransactionManager() {
				return new DataSourceTransactionManager(dataSource());
		}
		
}