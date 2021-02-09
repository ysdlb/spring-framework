package ysdlb.learn.aop.interceptor;

import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

public class MoreAdviceTest {

	@Test
	void chain() {
		// 创建目标对象
		MoreAdvice.HelloService helloService = new MoreAdvice.HelloService();

		// 创建代理工厂，通过代理工厂来创建代理对象
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(helloService);

		// 依次为对象添加通知
		proxyFactory.addAdvice(new MoreAdvice.MyMethodInterceptor());
		proxyFactory.addAdvice(new MoreAdvice.MyAfterReturningAdvice());
		proxyFactory.addAdvice(new MoreAdvice.MyThrowsAdvice());
		proxyFactory.addAdvice(new MoreAdvice.MyMethodBeforeAdvice());

		// 获取代理对象
		MoreAdvice.HelloService proxyService = (MoreAdvice.HelloService) proxyFactory.getProxy();

		// 通过代理对象访问目标方法
		System.out.println(proxyService.say("ysdlb"));
	}

}
