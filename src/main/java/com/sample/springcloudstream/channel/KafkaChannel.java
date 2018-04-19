package com.sample.springcloudstream.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface KafkaChannel {

  String FOO_CHANNEL_INPUT = "foo_channel_input";
  String FOO_CHANNEL_OUTPUT = "foo_channel_output";

  @Output(FOO_CHANNEL_OUTPUT)
  MessageChannel fooChannelOutput();

  @Input(FOO_CHANNEL_INPUT)
  MessageChannel fooChannelInput();

}
