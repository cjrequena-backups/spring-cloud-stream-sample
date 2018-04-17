package com.sample.springcloudstream.source.impl;

import com.sample.springcloudstream.channel.KafkaChannel;
import com.sample.springcloudstream.dto.FooDTO;
import com.sample.springcloudstream.source.IFooSourceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class FooSourceService implements IFooSourceService {

  MessageChannel fooChannelOutput;

  @Autowired
  public FooSourceService(@Qualifier(KafkaChannel.FOO_CHANNEL_OUTPUT) MessageChannel fooChannelOutput) {
    this.fooChannelOutput = fooChannelOutput;
  }

  @Override
  public void send(FooDTO dto) {
    Map<String, String> headers = new HashMap<>();
    fooChannelOutput.send(MessageBuilder.withPayload(dto).copyHeaders(headers).build());
  }
}
