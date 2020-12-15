package ysdlb.learn.springbeans.extend.after;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("ysdlb.learn.springbeans.extend.after")
public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
		System.out.println("Main end");
	}
}
