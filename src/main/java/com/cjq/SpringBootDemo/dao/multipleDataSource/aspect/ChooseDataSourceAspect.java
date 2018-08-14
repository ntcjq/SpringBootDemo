package com.cjq.SpringBootDemo.dao.multipleDataSource.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.cjq.SpringBootDemo.dao.multipleDataSource.CustomerContextHolder;
import com.cjq.SpringBootDemo.dao.multipleDataSource.annotation.ChooseDataSource;

import java.lang.reflect.Method;


/**
 * 数据源切换的切面
 * 
 * 说明：要想让注解在类上生效，@Pointcut的配置需要修改，修改为拦截具体的类的方法，而不是拦截加了@ChooseDataSource的方法，
 * 如果只拦截加了@ChooseDataSource的方法的话，由于方法上注解的优先级高（只是该类的实现导致，并不是所有），类上的注解并没用。
 * 如果拦截某些方法，这些方法上可能并没有注解，此时再去看类上注解，这个情况下，类上的的注解才能生效。
 */
@Aspect
@Component
public class ChooseDataSourceAspect {
	
	//记录当前的数据源，用于@After操作，还原数据源用
    protected static final ThreadLocal<String> preDatasourceHolder = new ThreadLocal<String>();

    
    /**
     * 切点
     * 触发条件为：注解为ChooseDataSource的方法或类（说明：这边之所以是方法或类，是ChooseDataSource注解本身定义的）
     */
    @Pointcut("@annotation(com.cjq.SpringBootDemo.dao.multipleDataSource.annotation.ChooseDataSource)")
    public void chooseDataSourcePointCut() {

    }

    /**
     * 根据@ChooseDataSource的属性值设置不同的dataSourceKey
     */
    @Before(value="chooseDataSourcePointCut()")
    public void changeDataSourceBeforeMethodExecution(JoinPoint jp) {
    	System.out.println("=============开始进入切点获取anotation中配置的数据源");
        //拿到anotation中配置的数据源的key
        String resultDS = determineDatasource(jp);
        System.out.println("=============获取anotation中配置的数据源"+resultDS);
        //没有配置使用默认数据源
        if (resultDS == null) {
        	CustomerContextHolder.setCustomerType(null);
            return;
        }
        //记录当前数据源类型
        preDatasourceHolder.set(CustomerContextHolder.getCustomerType());
        //将数据源设置到数据源持有者
        CustomerContextHolder.setCustomerType(resultDS);

    }
    
    /**
     * 方法执行完毕以后，数据源切换回之前的数据源。
     * 比如foo()方法里面调用bar()，但是bar()另外一个数据源，
     * bar()执行时，切换到自己数据源，执行完以后，要切换到foo()所需要的数据源，以供
     * foo()继续执行。
     */
    @After(value="chooseDataSourcePointCut()")
    public void restoreDataSourceAfterMethodExecution() {
    	CustomerContextHolder.setCustomerType(preDatasourceHolder.get());
        preDatasourceHolder.remove();
    }

    /**
     * 如果需要修改获取数据源的逻辑，请重写此方法
     * @param jp
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected String determineDatasource(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        Class targetClass = jp.getSignature().getDeclaringType();
        //获取类上的@ChooseDataSource注解
        String dataSourceForTargetClass = resolveDataSourceFromClass(targetClass);
        //获取方法上的@ChooseDataSource注解
        String dataSourceForTargetMethod = resolveDataSourceFromMethod(targetClass, methodName);
        //最终决定用哪个注解（方法上的优先级高于类，方法上有就用方法上的，方法上没有就用类上的）
        String resultDS = determinateDataSource(dataSourceForTargetClass,dataSourceForTargetMethod);
        return resultDS;
    }

    /**
     * 类级别的 @ChooseDataSource的解析
     * @param targetClass
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private String resolveDataSourceFromClass(Class targetClass) {
        ChooseDataSource classAnnotation = (ChooseDataSource) targetClass
                .getAnnotation(ChooseDataSource.class);
        // 直接为整个类进行设置
        return null != classAnnotation ? resolveDataSourcename(classAnnotation)
                : null;
    }
    
    /**
     * 方法级别的 @ChooseDataSource的解析
     * @param targetClass
     * @param methodName
     * @return
     */
    @SuppressWarnings("rawtypes")
    private String resolveDataSourceFromMethod(Class targetClass,String methodName) {
		Method m = findUniqueMethod(targetClass, methodName);
		if (m != null) {
            ChooseDataSource choDs = m.getAnnotation(ChooseDataSource.class);
            return resolveDataSourcename(choDs);
        }
        return null;
    }

    public static Method findUniqueMethod(Class<?> clazz, String name) {
		Assert.notNull(clazz, "Class must not be null");
		Assert.notNull(name, "Method name must not be null");
		Class<?> searchType = clazz;
		while (searchType != null) {
			Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
			for (Method method : methods) {
				if (name.equals(method.getName())) {
					return method;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}
    
    /**
     * 确定最终数据源，如果方法上设置有数据源，则以方法上的为准，如果方法上没有设置，则以类上的为准，如果类上没有设置，则使用默认数据源
     * @param classDS
     * @param methodDS
     * @return
     */
    private String determinateDataSource(String classDS, String methodDS) {
        return methodDS == null ? classDS : methodDS;
    }

    /**
     * 获取注解的value（数据源对应的key）
     * @param ds
     * @return
     */
    private String resolveDataSourcename(ChooseDataSource ds) {
        return ds == null ? null : ds.value();
    }

}
