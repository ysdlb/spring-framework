package ysdlb.learn.springcontext.simple;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("ysdlb.learn.springcontext.simple")
public class SimpleApplication {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ac = new
				AnnotationConfigApplicationContext(SimpleApplication.class);

		Hello hello = ac.getBean(Hello.class);

		hello.hello();
	}
}
