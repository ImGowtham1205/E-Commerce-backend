package com.example.ecommerce.Aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {
	
	private static final Logger log = LoggerFactory.getLogger("PERFORMANCE_LOGGER");
	
	@Pointcut("execution (* com.example.ecommerce.controller.*.*(..))")
	private void controllerPointCut() {}
	
	@Around("controllerPointCut()")
	public Object performanceCheck(ProceedingJoinPoint jointpoint ) throws Throwable {
		long start = System.currentTimeMillis();
		Object result = jointpoint.proceed();
		long end = System.currentTimeMillis();
		long actual = end - start;
		
		log.info("⏱️ " +jointpoint.getSignature().toShortString() + "took "+ actual 
				+"ms to complete the task");
		
		if(actual > 500)
			log.warn("⚠️ Slow API : {} took {} ms ",jointpoint.getSignature().toShortString(),actual);
		
		return result;
	}
	
}
