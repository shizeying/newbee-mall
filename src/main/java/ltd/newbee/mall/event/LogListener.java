package ltd.newbee.mall.event;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ltd.newbee.mall.dao.mysql.MallUserMapper;
import ltd.newbee.mall.dao.mysql.NewBeeMallGoodsMapper;
import ltd.newbee.mall.entity.MallLogDO;
import ltd.newbee.mall.entity.MallUser;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.service.MallLogService;
import ltd.newbee.mall.service.NewBeeMallUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 模板事件:删除模板之后，发布事件删除关联的列表
 *
 * @author shizeying
 * @date 2022/07/18
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LogListener implements ApplicationListener<LogEvent> {
		
		private final MallLogService mallLogService;
		private final MallUserMapper mallUserMapper;
		private final NewBeeMallGoodsMapper newBeeMallGoodsMapper;
		
		@Override
		public void onApplicationEvent(LogEvent event) {
				String userName = event.getUserName();
				if (event.getUserId()!=null){
						final MallUser mallUser = mallUserMapper.selectByPrimaryKey(event.getUserId());
						if (mallUser!=null){
								userName = mallUser.getLoginName();
						}
				}
				String productName = event.getProductName();
				if (event.getProductId()!=null){
						final MallUser mallUser = mallUserMapper.selectByPrimaryKey(event.getProductId());
						if (mallUser!=null){
								final NewBeeMallGoods newBeeMallGoods = newBeeMallGoodsMapper.selectByPrimaryKey(
										event.getProductId());
								if (newBeeMallGoods!=null){
										productName = newBeeMallGoods.getGoodsName();
								}
						}
				}
				final MallLogDO mallLog = MallLogDO.builder().userName(userName)
						.productName(productName).op(event.getOp().getDesc()).createAt(new Date())
						.build();
				mallLogService.insertSelective(mallLog);
				
		}
}