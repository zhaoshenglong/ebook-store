package com.ibook.bookstore;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class OrderMessageCon {
    @KafkaListener(topics = {"order"})
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if(kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            System.out.println(message);
        }
    }
}
