package com.zhang.hrm.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Value("${rabbitmq.queueName.routingKey}")
    private String routingKey;

    public static final String EXCHANGE_DIRECT_INFORM = RabbitmqConstant.EXCHANGE_DIRECT_INFORM;
    public static final String HRM_COMMON_QUEUE = RabbitmqConstant.HRM_COMMON_QUEUE;

    /**
     * 交换机配置
     * ExchangeBuilder提供了fanout、direct、topic、header交换机类型的配置
     *
     * @return the exchange
     */
    @Bean(EXCHANGE_DIRECT_INFORM)
    public Exchange EXCHANGE_DIRECT_INFORM() {
    //durable(true)持久化，消息队列重启后交换机仍然存在
        return ExchangeBuilder.directExchange(EXCHANGE_DIRECT_INFORM).durable(true).build();
    }

    //声明队列
    @Bean(HRM_COMMON_QUEUE)
    public Queue HRM_COMMON_QUEUE() {
        Queue queue = new Queue(HRM_COMMON_QUEUE);
        return queue;
    }

    /**
     * 绑定队列到交换机 .
     * @Qualifier:表示按名称注入对象
     * @autowird:表示的是按类型注入对象
     * @param queue    the queue
     * @param exchange the exchange
     * @return the binding
     */
    @Bean
    public Binding BINDING_QUEUE_INFORM_SMS(@Qualifier(HRM_COMMON_QUEUE) Queue queue,
                                            @Qualifier(EXCHANGE_DIRECT_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }
}