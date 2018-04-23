package com.sample.springcloudstream.sink.impl;

import com.sample.springcloudstream.channel.KafkaChannel;
import com.sample.springcloudstream.dto.FooDTO;
import com.sample.springcloudstream.sink.IFooSinkService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Log4j2
@Service
public class FooSinkService implements IFooSinkService {

  @StreamListener(KafkaChannel.FOO_INPUT_CHANNEL1)
  @SendTo(KafkaChannel.FOO_OUTPUT_CHANNEL2)
  public synchronized FooDTO processMessage(Message<?> message) throws InterruptedException {
    log.debug("Input message -> {}", message);
    FooDTO dto = (FooDTO) message.getPayload();
    dto.setName(dto.getName() + "-" + LocalDate.now());
    Thread.sleep(1000);
    return dto;
  }

  @StreamListener(KafkaChannel.FOO_INPUT_CHANNEL2)
  public synchronized void listener(Message<?> message) throws InterruptedException {
    log.debug("Input message -> {}", message);
    FooDTO dto = (FooDTO) message.getPayload();
    Thread.sleep(1000);
  }

}
