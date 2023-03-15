package ltd.newbee.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import ltd.newbee.mall.dao.hive.MallLogMapper;
import ltd.newbee.mall.entity.MallLogDO;
import ltd.newbee.mall.service.MallLogService;
import org.springframework.stereotype.Service;

/**
 * @author zeyingshi
 * @date 2023/3/15 07:20
 */
@Service
public class MallLogServiceImpl extends ServiceImpl<MallLogMapper, MallLogDO> implements
		MallLogService {
		
		@Override
		public void insertSelective(MallLogDO record) {
				baseMapper.insertSelective(record);
		}
		
		@Override
		public List<MallLogDO> selectByUserName(String userName) {
				return baseMapper.selectByUserName(userName);
		}
		
}