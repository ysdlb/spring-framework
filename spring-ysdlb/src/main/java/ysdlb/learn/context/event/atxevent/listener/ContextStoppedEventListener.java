package ysdlb.learn.context.event.atxevent.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextStoppedEventListener implements ApplicationListener<ContextStoppedEvent> {
	@Override
	public void onApplicationEvent(ContextStoppedEvent event) {
		System.out.println("context stopped event");
	}
}
