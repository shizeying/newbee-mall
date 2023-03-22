package ltd.newbee.mall.service;

import java.util.List;
import ltd.newbee.mall.controller.vo.LineVO;
import ltd.newbee.mall.entity.MallLogDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author zeyingshi
 * @date 2023/3/15 07:20
 */
public interface MallLogService extends IService<MallLogDO> {
		
		
		/**
		 * 插入选择性
		 *
		 * @param record 记录
		 * @return int
		 */
		void insertSelective(MallLogDO record);
		
		/**
		 * 选择用户名
		 *
		 * @param userName 用户名
		 * @return {@link List}<{@link MallLogDO}>
		 */
		List<MallLogDO> selectByUserName(String userName);
		
		LineVO line();
		
		LineVO bar();
		
		LineVO pie();
}