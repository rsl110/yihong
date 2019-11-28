package com.yh.interceptors;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * <p> Description:           </p>
 *
 * @Author ranshaolin
 * @Date 2019/11/25
 * @Version
 * @修改记录 <pre>
 *
 * 版本号   修改人       修改时间          修改内容描述
 * -------------------------------------------------------------
 * V1.0		  冉绍林	      2019/11/25	       新建文件
 *
 * </pre>
 */
public class MyProducerInterceptor  implements ProducerInterceptor {
    @Override
    public ProducerRecord onSend(ProducerRecord producerRecord) {
        System.out.println("ProducerInterceptor.onSend(...)");
        return null;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        System.out.println("ProducerInterceptor.onAcknowledgement(...)");
    }

    @Override
    public void close() {
        System.out.println("ProducerInterceptor.close(...)");
    }

    @Override
    public void configure(Map<String, ?> map) {
        System.out.println("ProducerInterceptor.configure(...)");
    }
}
