package com.sample.springcloudstream.source.impl;

import com.sample.springcloudstream.MainApplication;
import com.sample.springcloudstream.dto.FooDTO;
import com.sample.springcloudstream.source.IFooSourceService;
import lombok.extern.log4j.Log4j2;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FooSourceServiceIT {

  @Autowired
  IFooSourceService fooService;

  @Test
  public void send() throws Exception {
    for (int i=0; i<10; i++){
      FooDTO dto = new FooDTO();
      dto.setId(UUID.randomUUID().toString());
      dto.setName("TEST_" + i + "_" + UUID.randomUUID().toString());
      fooService.send(dto);
    }
  }

}
