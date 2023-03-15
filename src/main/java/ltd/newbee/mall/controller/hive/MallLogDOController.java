package ltd.newbee.mall.controller.hive;

import lombok.RequiredArgsConstructor;
import ltd.newbee.mall.entity.MallLogDO;
import ltd.newbee.mall.service.MallLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (mall_log)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/mall_log")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MallLogDOController {
		
		/**
		 * 服务对象
		 */
		private final MallLogService mallLogService;
		
		/**
		 * 通过主键查询单条数据
		 *
		 * @param id 主键
		 * @return 单条数据
		 */
		@GetMapping("selectOne")
		public MallLogDO selectOne(Integer id) {
				return null;
		}
		
}