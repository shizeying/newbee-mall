package ltd.newbee.mall.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author shizeying
 * @date 2022-07-12 3:03 下午
 */
@Component
public class SpringEventPublisher {

    @Autowired
    private ApplicationEventPublisher publisher;

    public DomainEvent publish(DomainEvent event) {
        publisher.publishEvent(event);
        return event;
    }
}