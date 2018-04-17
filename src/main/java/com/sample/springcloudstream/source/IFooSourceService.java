package com.sample.springcloudstream.source;

import com.sample.springcloudstream.dto.FooDTO;

public interface IFooSourceService {

  void send(FooDTO dto);
}
