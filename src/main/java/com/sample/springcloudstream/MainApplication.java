package com.sample.springcloudstream;

import com.sample.springcloudstream.dto.FooDTO;
import com.sample.springcloudstream.source.IFooSourceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 *
 * @author cjrequena
 * @version 1.0
 * @see
 * @since JDK1.8
 */
@Log4j2
@SpringBootApplication
public class MainApplication implements CommandLineRunner {

  @Autowired
  IFooSourceService fooService;

  private static Class<MainApplication> mainApplicationClass = MainApplication.class;

  /**
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    try {
      SpringApplication springApplication = new SpringApplication(mainApplicationClass);
      springApplication.setBannerMode(Banner.Mode.OFF);
      springApplication.run(args);
    } catch (Exception ex) {
      log.error("Error: " + ex);
      throw ex;
    }
  }

  @Override
  public void run(String... strings) {
    FooDTO dto = new FooDTO();
    String uuid = UUID.randomUUID().toString();
    dto.setId(uuid);
    dto.setName("Foo-" + uuid);
    fooService.send(dto);
  }
}

