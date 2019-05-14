package com.sample.springcloudstream.source.service;

import com.sample.springcloudstream.source.channel.KafkaChannel;
import com.sample.springcloudstream.source.dto.FooDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
public class FooSourceService {

  MessageChannel fooChannelOutput;

  @Autowired
  public FooSourceService(@Qualifier(KafkaChannel.FOO_OUTPUT_CHANNEL1) MessageChannel fooChannelOutput) {
    this.fooChannelOutput = fooChannelOutput;
  }

  public void send(FooDTO dto) {
    log.debug("Output channel 1 message {}", dto);
    Map<String, String> headers = new HashMap<>();
    headers.put("x-test-header", "OK");
    fooChannelOutput.send(MessageBuilder.withPayload(dto).copyHeaders(headers).build());
  }

}
