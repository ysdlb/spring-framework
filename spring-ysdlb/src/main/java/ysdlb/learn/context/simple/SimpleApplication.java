package ysdlb.learn.context.simple;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("ysdlb.learn.context.simple")
public class SimpleApplication {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ac = new
				AnnotationConfigApplicationContext(SimpleApplication.class);

		Hello hello = ac.getBean(Hello.class);

		hello.hello();
	}
}
