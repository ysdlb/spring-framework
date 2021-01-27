package ysdlb.learn.context.event.custom;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ysdlb.learn.context.event.custom.publisher.CustomPublisher;

@ComponentScan("ysdlb.learn.context.event")
public class MainApplication {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainApplication.class);
		context.getBean(CustomPublisher.class).doPublish("i am ysdlb");
	}
}
