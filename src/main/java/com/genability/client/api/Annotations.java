package com.genability.client.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

public class Annotations {

  @Qualifier
  @Retention(RetentionPolicy.RUNTIME)
  public @interface AppId {}
  
  @Qualifier
  @Retention(RetentionPolicy.RUNTIME)
  public @interface AppKey {}
  
  @Qualifier
  @Retention(RetentionPolicy.RUNTIME)
  public @interface ServerAddress {}
}
