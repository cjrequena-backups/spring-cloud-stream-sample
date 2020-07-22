package com.cjrequena.sample.sinksample.service;

import brave.Tracer;
import brave.Tracing;
import com.cjrequena.sample.sinksample.channel.KafkaChannel;
import com.cjrequena.sample.sinksample.dto.FooDTO;
import com.cjrequena.sample.sinksample.messageconverter.FooMessageConverter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FooSinkService {

  @Autowired
  private Tracing tracing;
  @Autowired
  private Tracer tracer;

  @Bean
  public MessageConverter customMessageConverter() {
    return new FooMessageConverter();
  }

  @StreamListener(value = KafkaChannel.FOO_INPUT_CHANNEL1, condition = "headers['x-test-header']=='OK'")
  public synchronized void listener(Message<FooDTO> message, FooDTO fooDTO) throws InterruptedException {
    log.debug("Channel 1 Input message -> {}", message);
    String traceID = tracer.currentSpan().context().traceIdString();
    String spanID = tracer.currentSpan().context().spanIdString();
    log.debug("traceID: {}, spainID: {} ", traceID, spanID);

    FooDTO dto = message.getPayload();
    wait(1000);
  }

}
