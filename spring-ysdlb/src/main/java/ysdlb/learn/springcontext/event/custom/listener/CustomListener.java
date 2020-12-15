package ysdlb.learn.springcontext.event.custom.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ysdlb.learn.springcontext.event.custom.event.CustomEvent;

@Component
public class CustomListener implements ApplicationListener<CustomEvent> {
	@Override
	public void onApplicationEvent(CustomEvent event) {
		System.out.println(String.format("received from custom event -> %s", event.getMessage()));
	}
}
