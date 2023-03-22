package ltd.newbee.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import io.vavr.collection.Traversable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import ltd.newbee.mall.common.OpEnum;
import ltd.newbee.mall.config.AriusScheduleThreadPool;
import ltd.newbee.mall.controller.vo.KeyWithValue;
import ltd.newbee.mall.controller.vo.LineVO;
import ltd.newbee.mall.dao.hive.MallLogMapper;
import ltd.newbee.mall.entity.MallLogDO;
import ltd.newbee.mall.service.MallLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zeyingshi
 * @date 2023/3/15 07:20
 */
@Service
public class MallLogServiceImpl extends ServiceImpl<MallLogMapper, MallLogDO> implements
		MallLogService {
		
		private static final Cache<String, List<MallLogDO>> MALL_LOG_DO_CACHE = CacheBuilder.newBuilder()
				.expireAfterWrite(10, TimeUnit.MINUTES).maximumSize(20000).build();
		@Autowired
		private              AriusScheduleThreadPool        ariusScheduleThreadPool;
		public static final  String                         MALL_LOG_DO_CACHE_KEY = "mallLogs";
		
		private void refreshClusterPhyPluginInfo() {
				final List<MallLogDO> mallLogs = baseMapper.selectAll();
				MALL_LOG_DO_CACHE.put(MALL_LOG_DO_CACHE_KEY, mallLogs);
		}
		
		@PostConstruct
		private void init() {
				ariusScheduleThreadPool.submitScheduleAtFixedDelayTask(this::refreshClusterPhyPluginInfo,
						0, 180);
		}
		
		@Override
		public void insertSelective(MallLogDO record) {
				baseMapper.insertSelective(record);
		}
		
		@Override
		public List<MallLogDO> selectByUserName(String userName) {
				return baseMapper.selectByUserName(userName);
		}
		
		@Override
		public LineVO line() {
				// 从缓存中获取数据
				final List<MallLogDO> mallLogs = MALL_LOG_DO_CACHE.getIfPresent(MALL_LOG_DO_CACHE_KEY);
				if (mallLogs == null) {
						return new LineVO();
				}
				final Map<String, Integer> map = io.vavr.collection.List.ofAll(mallLogs)
						.groupBy(i->new SimpleDateFormat("yyyy-MM-dd").format(i.getCreateAt()))
						.mapValues(i->i.filter(j-> OpEnum.getOp(j.getOp())==OpEnum.LOGIN||OpEnum.getOp(j.getOp())==OpEnum.LOGIN).size())
						.toJavaMap();
				final List<Object> xAxisData = new ArrayList<>(map.keySet());
				final List<Object> seriesData = new ArrayList<>(map.values());
				
				return LineVO.builder().xAxis(xAxisData).series(seriesData)
						.title("基于时间维度用户登录的折线图")
						.build();
		}
		
		@Override
		public LineVO bar() {
			// 从缓存中获取数据
				final List<MallLogDO> mallLogs = MALL_LOG_DO_CACHE.getIfPresent(MALL_LOG_DO_CACHE_KEY);
				if (mallLogs == null) {
						return new LineVO();
				}
				final Map<String, Integer> map = io.vavr.collection.List.ofAll(mallLogs)
						.groupBy(MallLogDO::getUserName)
						.mapValues(Traversable::size)
						.toJavaMap();
				final List<Object> xAxisData = new ArrayList<>(map.keySet());
				final List<Object> seriesData = new ArrayList<>(map.values());
				
				return LineVO.builder().xAxis(xAxisData).series(seriesData).title("基于用户的加入购物车产品数量柱状图").build();
		}
		
		@Override
		public LineVO pie() {
				// 从缓存中获取数据
				final List<MallLogDO> mallLogs = MALL_LOG_DO_CACHE.getIfPresent(MALL_LOG_DO_CACHE_KEY);
				if (mallLogs == null) {
						return new LineVO();
				}
				final List<KeyWithValue> keyWithValues = io.vavr.collection.List.ofAll(mallLogs)
						.filter(i -> StringUtils.isNotBlank(i.getProductName()))
						.groupBy(MallLogDO::getProductName)
						.mapValues(Traversable::size)
						.map(i -> KeyWithValue.builder()
								.name(i._1)
								.value(i._2())
								.build())
						.asJava();
				
				return LineVO.builder().result(keyWithValues)
						.title("基于产品维度的用户添加数量饼图")
						.build();
		}
}