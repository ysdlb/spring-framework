package ysdlb.learn.context.event.custom.event;

import org.springframework.context.ApplicationEvent;

/**
 * Spring 有很多现成的 Event 实现
 * @See ContextRefreshedEvent
 */
public class CustomEvent extends ApplicationEvent {

	private static final long serialVersionUID = 7099157708183571937L;

	private String message;

	public String getMessage() {
		return message;
	}

	public CustomEvent(Object source, String message) {
		super(source);
		this.message = message;
	}
}
