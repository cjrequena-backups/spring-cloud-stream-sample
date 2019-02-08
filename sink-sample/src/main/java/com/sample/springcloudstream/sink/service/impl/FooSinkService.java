package com.sample.springcloudstream.sink.service.impl;

import com.sample.springcloudstream.sink.dto.FooDTO;
import com.sample.springcloudstream.sink.channel.KafkaChannel;
import com.sample.springcloudstream.sink.messageconverter.FooMessageConverter;
import com.sample.springcloudstream.sink.service.IFooSinkService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.stereotype.Service;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 * @author cjrequena
 * @version 1.0
 * @since JDK1.8
 * @see
 *
 */
@Log4j2
@Service
public class FooSinkService implements IFooSinkService {

  @Bean
  public MessageConverter customMessageConverter() {
    return new FooMessageConverter();
  }

  @StreamListener(value = KafkaChannel.FOO_INPUT_CHANNEL1, condition = "headers['x-test-header']=='OK'")
  public synchronized void listener(Message<FooDTO> message, FooDTO fooDTO) throws InterruptedException {
    log.debug("Channel 1 Input message -> {}", message);
    FooDTO dto = message.getPayload();
    wait(1000);
  }



}
