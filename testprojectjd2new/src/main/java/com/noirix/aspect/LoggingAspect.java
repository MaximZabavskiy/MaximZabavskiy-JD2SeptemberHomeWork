package com.noirix.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.*;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger log = Logger.getLogger(LoggingAspect.class);

    private Map<Integer, String> methodMap = new HashMap();

    {
        methodMap.put(1, "search");
        methodMap.put(2, "save");
        methodMap.put(3, "findAll");
        methodMap.put(4, "findById");
        methodMap.put(5, "findOne");
        methodMap.put(6, "update");
        methodMap.put(7, "delete");
    }

    private String nameSearchMethod = methodMap.get(1);
    private String nameSaveMethod = methodMap.get(2);
    private String nameFindAllMethod = methodMap.get(3);
    private String nameFindByIdMethod = methodMap.get(4);
    private String nameFindOneMethod = methodMap.get(5);
    private String nameUpdateMethod = methodMap.get(6);
    private String nameDeleteMethod = methodMap.get(7);

    @Pointcut("execution(* com.noirix.repository.impl.UserRepositoryJdbcTemplateImpl.*(..))")
    public void aroundRepositoryPointcut() {
    }

    @Around("aroundRepositoryPointcut()")
    public Object logAroundMethods(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();


        log.info("Method " + joinPoint.getSignature().getName() + " start");
        Object proceed = joinPoint.proceed();


        int count = 0;
        if (nameSearchMethod.equalsIgnoreCase(joinPoint.getSignature().getName())) {
            count++;
        }
        if (nameSaveMethod.equalsIgnoreCase(joinPoint.getSignature().getName())) {
            count++;
        }
        if (nameFindAllMethod.equalsIgnoreCase(joinPoint.getSignature().getName())) {
            count++;
        }
        if (nameFindByIdMethod.equalsIgnoreCase(joinPoint.getSignature().getName())) {
            count++;
        }
        if (nameFindOneMethod.equalsIgnoreCase(joinPoint.getSignature().getName())) {
            count++;
        }
        if (nameUpdateMethod.equalsIgnoreCase(joinPoint.getSignature().getName())) {
            count++;
        }
        if (nameDeleteMethod.equalsIgnoreCase(joinPoint.getSignature().getName())) {
            count++;
        }

        log.info("The method \"" + joinPoint.getSignature().getName() + "\" was executed " + count + " times");

        log.info("Method " + joinPoint.getSignature().getName() + " finished");


        stopWatch.stop();

        log.info("Execution time of the method \"" + joinPoint.getSignature().getName() + "\" : "
                + stopWatch.getTotalTimeMillis() + " milliseconds");


        return proceed;

    }
}
