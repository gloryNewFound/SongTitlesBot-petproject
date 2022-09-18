package com.dvn.telegrambot.aspects;

import com.dvn.telegrambot.SongTitlesBot;
import org.aspectj.bridge.Message;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    @Before("execution(public String getAnswer(*))")
    //@Before("com.dvn.telegrambot.aspects.Pointcuts.getAnswerPointcut()")
    private void beforeGetAnswerAdvice(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] arguments = joinPoint.getArgs();
        Message message = (Message) arguments[0];
        System.out.println("--------------------------------------------");
        System.out.println("beforeGetAnswerAdvice: Message \"" + message.getMessage());
        //+ "\" was received from " + message.getFrom().getUserName());
    }
}
