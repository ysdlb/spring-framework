package ysdlb.learn.beans.dependency.cycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ysdlb.learn.beans.dependency.cycle.MulMul.C;
import ysdlb.learn.beans.dependency.cycle.SinMul.A;

@ComponentScan("ysdlb.learn.beans.dependency.cycle")
public class CycleDependencyApp {
	public static void main (String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CycleDependencyApp.class);

		System.out.println("try to get Bean: A");
		context.getBean(A.class);
		System.out.println("success!");


		System.out.println("try to get Bean: C");
		context.getBean(C.class);
		System.out.println("success!");

	}
}
