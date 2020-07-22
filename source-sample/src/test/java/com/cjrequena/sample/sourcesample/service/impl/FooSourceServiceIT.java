package com.cjrequena.sample.sourcesample.service.impl;

import com.cjrequena.sample.sourcesample.SourceMainApplication;
import com.cjrequena.sample.sourcesample.dto.FooDTO;
import com.cjrequena.sample.sourcesample.service.FooSourceService;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

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
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SourceMainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FooSourceServiceIT {

  @Autowired
  FooSourceService fooSourceService;

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void send() throws Exception {
    for (int i = 0; i < 10; i++) {
      FooDTO dto = new FooDTO();
      dto.setId(UUID.randomUUID().toString());
      dto.setName("FOO_TEST_" + i);
      fooSourceService.send(dto);
    }
  }

}
