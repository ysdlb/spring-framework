package ysdlb.learn.core.cglib.simple;

import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

@SuppressWarnings("DuplicatedCode")
public class OriginalTest {

	@Test
	public void version_final() {
		Enhancer firstEnhancer = new Enhancer();
		firstEnhancer.setSuperclass(Original.class);
		firstEnhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
			System.out.println("METHOD INTERCEPTOR AT FIRST PROXY");
			return methodProxy.invokeSuper(o, objects);
		});
		Original firstProxy = (Original) firstEnhancer.create();

		//Create Second Proxy
		Enhancer secondEnhancer = new Enhancer();
		secondEnhancer.setSuperclass(firstProxy.getClass().getSuperclass());
		// secondEnhancer.setSuperclass(Original.class);
		secondEnhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
			System.out.println("METHOD INTERCEPTOR AT SECOND PROXY");
			return methodProxy.invoke(firstProxy, objects);
		});

		//Getting Exception on this line
		Original secondProxy = (Original) secondEnhancer.create();

		//Call
		secondProxy.doSomething();

	}

	@Test
	public void version_two() {
		//Create First Proxy
		Enhancer firstEnhancer= new Enhancer();
		firstEnhancer.setSuperclass(Original.class);
		firstEnhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
			System.out.println("METHOD INTERCEPTOR AT FIRST PROXY");
			return methodProxy.invokeSuper(o, objects);
		});
		Original firstProxy = (Original) firstEnhancer.create();
		//Call
		firstProxy.doSomething();

		//Create Second Proxy
		Enhancer secondEnhancer= new Enhancer();
		secondEnhancer.setSuperclass(firstProxy.getClass().getSuperclass());
		secondEnhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
			System.out.println("METHOD INTERCEPTOR AT SECOND PROXY");
			// todo 这里 methodProxy invoke 入参 o 会导致无线循环调用, StackOverFlowError
			// todo o 其实就是加强过的对象 secondEnhancer 自己, o 用 proxy 表示更合适
			return methodProxy.invoke(o, objects);
		});

		//Getting Exception on this line
		Original secondProxy = (Original) secondEnhancer.create();
		//Call
		secondProxy.doSomething();
	}

	@Test
	public void version_one() {
		        //Create First Proxy
        Enhancer firstEnhancer= new Enhancer();
        firstEnhancer.setSuperclass(Original.class);
        firstEnhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            System.out.println("METHOD INTERCEPTOR AT FIRST PROXY");
            return methodProxy.invokeSuper(o, objects);
        });
        Original firstProxy = (Original) firstEnhancer.create();

        //Create Second Proxy
        Enhancer secondEnhancer= new Enhancer();
        // todo enhancer setSuperclass 一个 enhancer 会报错
        secondEnhancer.setSuperclass(firstProxy.getClass());
        secondEnhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            System.out.println("METHOD INTERCEPTOR AT SECOND PROXY");
            return methodProxy.invokeSuper(o, objects);
        });

        //Getting Exception on this line
        Original secondProxy = (Original) secondEnhancer.create();

        //Call
        secondProxy.doSomething();
	}
}
