package com.sample.springcloudstream.sink.impl;

import com.sample.springcloudstream.channel.KafkaChannel;
import com.sample.springcloudstream.dto.FooDTO;
import com.sample.springcloudstream.sink.IFooSinkService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class FooSinkService implements IFooSinkService{

  @StreamListener(KafkaChannel.FOO_CHANNEL_INPUT)
  public synchronized void listener(Message<?> message) {
    FooDTO dto = (FooDTO) message.getPayload();
    log.debug("GET MESSAGE {}", message);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
