package com.sample.springcloudstream.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FooDTO implements Serializable{
  private String id;
  private String name;
}
