package ysdlb.learn.expression.test;

import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodTest {

	/**
	 * 简单计算，取值
	 */
	@Test
	void calculate() {
		String expressionStr = "1 + 2";

		// SpelExpressionParser是Spring内部对ExpressionParser的唯一最终实现类
		ExpressionParser parser = new SpelExpressionParser();

		// 把该表达式，解析成一个Expression对象：SpelExpression
		Expression exp = parser.parseExpression(expressionStr);

		// 方式一：直接计算
		Object value = exp.getValue();
		System.out.println(value); //3

		// 方式二：定义求值上下文，在上下文中内计算拿值
		// 但是这里并没有往上下文中塞任何值
		EvaluationContext context = new StandardEvaluationContext();
		System.out.println(exp.getValue(context)); //3

		// 塞个 9 进去
		context.setVariable("3", 9);
		context.setVariable("3_", 9);

		// 下面这些只能得到 3
		System.out.println(exp.getValue(context)); //3
		System.out.println(parser.parseExpression("3").getValue(context));
		System.out.println(parser.parseExpression("'3'").getValue(context));
		System.out.println(parser.parseExpression("T(String).valueOf(3)").getValue(context));

		// 下面这些都报错
		System.out.println(parser.parseExpression("3_").getValue(context));
		System.out.println(parser.parseExpression("\"3\"").getValue(context));

	}

	@Test
	void staticMethod() {
		ExpressionParser parser = new SpelExpressionParser();

		// 若你在@Value中或者xml使用此表达式，请使用#{}包裹~~~~~~~~~~~~~~~~~
		Object object = parser.parseExpression("T(System).getProperty('user.dir')").getValue();
		System.out.println(object);
		System.out.println(System.getProperty("user.dir"));

		System.out.println(parser.parseExpression("T(java.lang.Math).random() * 100.0").getValue());
	}


	@Test
	public void main() {
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setRootObject(new Person("fsx", 18)); // 这个就是最终#root取出来的对象  若没有设置  就不能使用#root


		System.out.println(parser.parseExpression("#root").getValue(context) instanceof Person); // true
		System.out.println(parser.parseExpression("#root").getValue(context)); //Person{name='fsx', age=18}
		System.out.println(parser.parseExpression("#root.name").getValue(context)); //fsx
		System.out.println(parser.parseExpression("#root.age").getValue(context)); // 18


		// 若单纯的想获取属性值，请不要使用#  直接使用name即可
		// #root代表把root当作key先去查找出对象，再导航查找。。。。。而不用#类似全文查找（这个做法非常非常像JSP的el表达式的写法）
		System.out.println(parser.parseExpression("#name").getValue(context)); // null
		System.out.println(parser.parseExpression("name").getValue(context)); // fsx


		// el参与计算时，取值方式也可以是#root 或者直接name属性的方式 都是可以的
		System.out.println(parser.parseExpression("name=='孙悟空'").getValue(context)); //false
		System.out.println(parser.parseExpression("name=='fsx'").getValue(context)); //true
		System.out.println(parser.parseExpression("#root.name=='fsx'").getValue(context)); //true

		System.out.println(parser.parseExpression("name.equals('fsx')").getValue(context)); //true
		//org.springframework.expression.spel.SpelEvaluationException: EL1004E: Method call: Method equalsXXX(java.lang.String) cannot be found on type java.lang.String
		System.out.println(parser.parseExpression("name.equalsXXX('fsx')").getValue(context)); // 报错

	}

	@Test
	public void expression() {
		String greetingExp = "Hello, #{#user} ---> #{T(System).getProperty('user.home')}";
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("user", "fsx");

        Expression expression = parser.parseExpression(greetingExp, new TemplateParserContext());
		// Hello, fsx ---> C:\Users\fangshixiang
        System.out.println(expression.getValue(context, String.class));
	}

	@Test
	public void setVariable() {
		       ExpressionParser parser = new SpelExpressionParser();

        Person person = new Person("fsx", 30);
        List<String> list = new ArrayList<String>() {{
            add("fsx");
            add("周杰伦");
        }};
        Map<String, Integer> map = new HashMap<String, Integer>() {{
            put("fsx", 18);
            put("周杰伦", 40);
        }};

        EvaluationContext ctx = new StandardEvaluationContext();
        //把list和map都放进环境变量里面去
        ctx.setVariable("myPerson", person);
        ctx.setVariable("myList", list);
        ctx.setVariable("myMap", map);


        //============================================
        System.out.println(parser.parseExpression("#myPerson").getValue(ctx)); //Person{name='fsx', age=30}
        System.out.println(parser.parseExpression("#myPerson.name").getValue(ctx)); //fsx
        // setVariable方式取值不能像root一样，前缀不可省略~~~~~
        System.out.println(parser.parseExpression("#name").getValue(ctx)); //null 显然找不到这个key就返回null呗~~~
        // 不写前缀默认去root找，找出一个null。再访问name属性那可不报错了吗
        //System.out.println(parser.parseExpression("name").getValue(ctx)); // Property or field 'name' cannot be found on null


        System.out.println(parser.parseExpression("#myList").getValue(ctx)); // [fsx, 周杰伦]
        System.out.println(parser.parseExpression("#myList[1]").getValue(ctx)); // 周杰伦

        // 请注意对Map取值两者的区别：中文作为key必须用''包起来   当然['fsx']也是没有问题的
        System.out.println(parser.parseExpression("#myMap[fsx]").getValue(ctx)); //18
        System.out.println(parser.parseExpression("#myMap['周杰伦']").getValue(ctx)); //40

        // =========若采用#key引用的变量不存在，返回的是null，并不会报错哦==============
        System.out.println(parser.parseExpression("#map").getValue(ctx)); //null

        // 黑科技：SpEL内直接可以使用new方式创建实例  能创建数组、List  但不能创建普通的实例对象（难道是我还不知道）~~~~
        System.out.println(parser.parseExpression("new String[]{'java','spring'}").getValue()); //[Ljava.lang.String;@30b8a058
        System.out.println(parser.parseExpression("{'java','c语言','PHP'}").getValue()); //[java, c语言, PHP] 创建List
        System.out.println(parser.parseExpression("new Person()").getValue()); //A problem occurred whilst attempting to const
	}

	@Test
	public void learnRoot() {
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setRootObject(new Person("fsx", 18)); // 这个就是最终#root取出来的对象  若没有设置  就不能使用#root


		System.out.println(parser.parseExpression("#root").getValue(context) instanceof Person); // true
		System.out.println(parser.parseExpression("#root").getValue(context)); //Person{name='fsx', age=18}
		System.out.println(parser.parseExpression("#root.name").getValue(context)); //fsx
		System.out.println(parser.parseExpression("#root.age").getValue(context)); // 18


		// 若单纯的想获取属性值，请不要使用#  直接使用name即可
		// #root代表把root当作key先去查找出对象，再导航查找。。。。。而不用#类似全文查找（这个做法非常非常像JSP的el表达式的写法）
		System.out.println(parser.parseExpression("#name").getValue(context)); // null
		System.out.println(parser.parseExpression("name").getValue(context)); // fsx


		// el参与计算时，取值方式也可以是#root 或者直接name属性的方式 都是可以的
		System.out.println(parser.parseExpression("name=='孙悟空'").getValue(context)); //false
		System.out.println(parser.parseExpression("name=='fsx'").getValue(context)); //true
		System.out.println(parser.parseExpression("#root.name=='fsx'").getValue(context)); //true

		System.out.println(parser.parseExpression("name.equals('fsx')").getValue(context)); //true
		//org.springframework.expression.spel.SpelEvaluationException: EL1004E: Method call: Method equalsXXX(java.lang.String) cannot be found on type java.lang.String
		System.out.println(parser.parseExpression("name.equalsXXX('fsx')").getValue(context)); // 报错
	}

	private static class Person {
		public String name;
		public Integer age;

		public Person(String name, Integer age) {
			this.name = name;
			this.age = age;
		}
	}
}
