package ltd.newbee.mall.config;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class AriusScheduleThreadPool {
		
		private       ScheduledExecutorService scheduleThreadPool;
		
		@PostConstruct
		public void init() {
				int scheduleThreadNum = 1 << 5;
				scheduleThreadPool = new ScheduledThreadPoolExecutor(scheduleThreadNum,
						new DesmondThreadFactory("scheduleThreadPool"));
		}
		
		public void submitScheduleAtFixedRateTask(Runnable runnable, long initialDelay, long period) {
				scheduleThreadPool.scheduleAtFixedRate(runnable, initialDelay, period, TimeUnit.SECONDS);
		}
		
		public void submitScheduleAtFixedDelayTask(Runnable runnable, long initialDelay, long delay) {
				scheduleThreadPool.scheduleWithFixedDelay(runnable, initialDelay, delay, TimeUnit.SECONDS);
		}
		
		static class DesmondThreadFactory implements ThreadFactory {
				
				static final AtomicInteger poolNumber   = new AtomicInteger(1);
				final        AtomicInteger threadNumber = new AtomicInteger(1);
				final        String        namePrefix;
				
				DesmondThreadFactory(String prefix) {
						namePrefix = prefix + "-pool-" + poolNumber.getAndIncrement() + "-";
				}
				
				@Override
				public Thread newThread(Runnable r) {
						Thread t = new Thread(r, namePrefix + threadNumber.getAndIncrement());
						if (!t.isDaemon()) {
								t.setDaemon(true);
						}
						
						if (t.getPriority() != Thread.NORM_PRIORITY) {
								t.setPriority(Thread.NORM_PRIORITY);
						}
						return t;
				}
		}
}