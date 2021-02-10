package com.wondersgroup.biz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * @author: lizhu@wondesgroup.com
 * @date: 2021/2/10 11:53
 * @description:
 */
@Slf4j
@EnableBinding(value = {Sink.class})
public class StreamConsumer {
    @StreamListener(Sink.INPUT)
    public void consume(Object payload) {
        log.info("message consumed successfully, payload={}", payload);
    }

}
