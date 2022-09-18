package com.dvn.telegrambot.aspects;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    @Pointcut("execution(public String getAnswer(..))")
    public void getAnswerPointcut(){}
}
