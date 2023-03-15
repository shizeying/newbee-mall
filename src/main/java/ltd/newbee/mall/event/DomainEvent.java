package ltd.newbee.mall.event;

import org.springframework.context.ApplicationEvent;

/**
 *
 *
 * @author shizeying
 * @date 2022-07-12 3:07 下午
 */
public abstract class DomainEvent extends ApplicationEvent implements EventNotifyListener{
    private String describe;
    public DomainEvent(Object source) {
        super(source);
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;

    }
}