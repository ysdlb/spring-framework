package ysdlb.learn.aop.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

public class MoreAdvice {

	public static class HelloService {
		public String say(String name) {
			System.out.println("in say()");
			return "hello " + name;
		}
	}

	public static class MyMethodInterceptor implements MethodInterceptor {
		@Nullable
		@Override
		public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
			System.out.println("MethodInterceptor start");
			// 调用 invocation.proceed 执行下一个拦截器
			Object result = invocation.proceed();
			System.out.println("MethodInterceptor end");
			return result;
		}
	}

	public static class MyMethodBeforeAdvice implements MethodBeforeAdvice {

		@Override
		public void before(Method method, Object[] args, Object target) throws Throwable {
			System.out.println("In MethodBeforeAdvice");
		}
	}

	public static class MyAfterReturningAdvice implements AfterReturningAdvice {

		@Override
		public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
			System.out.println("In AfterReturningAdvice");
		}
	}

	public static class MyThrowsAdvice implements ThrowsAdvice {

		public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {
			System.out.println("In ThrowsAdvice");
		}
	}

}
