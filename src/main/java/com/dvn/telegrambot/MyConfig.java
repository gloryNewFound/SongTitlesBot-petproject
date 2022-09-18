package com.dvn.telegrambot;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.dvn.telegrambot")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MyConfig {
}
