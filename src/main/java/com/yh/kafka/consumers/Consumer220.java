package com.yh.kafka.consumers;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;


import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * Kafka消息消费者逻辑应当具备以下步骤：
 *
 * 配置消费者参数，创建KafkaConsumer实例；
 * 订阅至少一个主题；
 * 拉取消息获得ConsumerRecords，遍历ConsumerRecords获取ConsumerRecord对象，从中提取消息的内容；
 * 程序退出或者无需消费消息时关闭KafkaConsumer。
 */
public class Consumer220 {
    public static void main(String[] args) {
        //配置消费者参数
        Properties props = new Properties() ;
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG ,  "192.168.1.31:9092,192.168.1.32:9092,192.168.1.33:9092") ; //Broker列表
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG , StringDeserializer.class.getName()) ;
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG , StringDeserializer.class.getName()) ;
        props.put(ConsumerConfig.GROUP_ID_CONFIG , "group.1") ;////消费者组
        props.put(ConsumerConfig.CLIENT_ID_CONFIG , "consumer.client.id.demo") ;//Client ID
        //创建 消费者实例 KafkaConsumer
        KafkaConsumer<String , String> consumer = new KafkaConsumer<String, String>(props) ;
        //订阅至少一个主题
        consumer.subscribe(Collections.singletonList("topic-test"));//订阅主题topic-test

        while (true) {
            //拉取消息获得ConsumerRecords，遍历ConsumerRecords获取ConsumerRecord对象，从中提取消息的内容；
            ConsumerRecords<String, String> record = consumer.poll(Duration.ofSeconds(5)) ; //拉取消息，阻塞时间5秒
            if(record.isEmpty()){
                break ;
            }
            //遍历消息并打印value
            record.forEach(rec -> System.out.println("topic:" + rec.topic() + " val:" + rec.value()));

        }
        //程序退出或者无需消费消息时关闭KafkaConsumer。
        consumer.close();
    }

}
