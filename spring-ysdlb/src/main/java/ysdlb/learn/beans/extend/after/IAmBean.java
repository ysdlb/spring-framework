package ysdlb.learn.beans.extend.after;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class IAmBean implements InitializingBean {

	public int i = createInt();

	public String s = createString();

	public boolean b = true;


	private int createInt() {
		System.out.println("create a int");
		return 66;
	}

	private String createString() {
		System.out.println("create a String");
		return "i am ysdlb";
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("in Initializing Bean");
	}
}
