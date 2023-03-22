package ltd.newbee.mall.dao.hive;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import ltd.newbee.mall.controller.vo.LineVO;
import ltd.newbee.mall.entity.MallLogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zeyingshi
 * @date 2023/3/15 07:53
 */
@Mapper
public interface MallLogMapper extends BaseMapper<MallLogDO> {
		
		/**
		 * insert record to table selective
		 *
		 * @param record the record
		 * @return insert count
		 */
		void insertSelective(MallLogDO record);
		
		/**
		 * 选择用户名
		 *
		 * @param userName 用户名
		 * @return {@link List}<{@link MallLogDO}>
		 */
		List<MallLogDO> selectByUserName(String userName);
		
		List<MallLogDO> selectAll();
}