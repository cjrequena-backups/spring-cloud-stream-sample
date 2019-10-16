package com.sample.springcloudstream.sink.messageconverter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.springcloudstream.sink.dto.FooDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

import java.io.IOException;

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
public class FooMessageConverter extends AbstractMessageConverter {

  @Autowired
  private ObjectMapper objectMapper;

  public FooMessageConverter() {
    super(MimeType.valueOf("application/json"));
  }

  @Override
  protected boolean supports(Class<?> clazz) {
    return clazz.equals(FooDTO.class);
  }

  @Override
  protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
    try {
      return objectMapper.readValue(message.getPayload().toString(), new TypeReference<FooDTO>() {
      });
    } catch (IOException e) {
      return null;
    }
  }
}
