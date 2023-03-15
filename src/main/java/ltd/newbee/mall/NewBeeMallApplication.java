/**
 * 严肃声明： 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！ 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！ Copyright (c) 2019-2020 十三 all rights reserved. 版权所有，侵权必究！
 */
package ltd.newbee.mall;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import ltd.newbee.mall.common.OpEnum;
import ltd.newbee.mall.event.LogEvent;
import ltd.newbee.mall.event.SpringEventPublisher;
import ltd.newbee.mall.service.MallLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link https://github.com/newbee-ltd
 */
@SpringBootApplication
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NewBeeMallApplication implements ApplicationRunner {
		
		public static void main(String[] args) {
				SpringApplication.run(NewBeeMallApplication.class, args);
		}
		
		private final SpringEventPublisher publisher;
		private final MallLogService mallLogService;
		@Override
		public void run(ApplicationArguments args) throws Exception {
				publisher.publish(
						LogEvent.Builder.newBuilder()
								.withOp(OpEnum.ADD)
								.withUserName("13")
								.withSource(this)
								.withProductName("13")
								
								.build());
				
				mallLogService.selectByUserName("13").forEach(System.out::println);
				
		}
}