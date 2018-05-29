package com.sample.springcloudstream.sink.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface KafkaChannel {

  String FOO_INPUT_CHANNEL1 = "foo_input_channel_1";

  @Input(FOO_INPUT_CHANNEL1)
  MessageChannel fooInputChannel1();

}
