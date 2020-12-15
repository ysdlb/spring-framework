package ysdlb.learn.springcontext.event.custom.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import ysdlb.learn.springcontext.event.custom.event.CustomEvent;

@Component
public class CustomPublisher {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	public void doPublish(final String message) {
		System.out.println("Publishing custom Event...");
		CustomEvent event = new CustomEvent(this, message);
		applicationEventPublisher.publishEvent(event);
	}
}
