package com.sample.springcloudstream.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface KafkaChannel {

  String FOO_INPUT_CHANNEL = "foo_input_channel";
  String FOO_OUTPUT_CHANNEL = "foo_output_channel";

  @Output(FOO_OUTPUT_CHANNEL)
  MessageChannel fooOutputChannel();

  @Input(FOO_INPUT_CHANNEL)
  MessageChannel fooInputChannel();

}
