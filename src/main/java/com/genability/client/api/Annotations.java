package com.genability.client.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

final class Annotations {

  @Qualifier
  @Retention(RetentionPolicy.RUNTIME)
  @interface AppId {}
  
  @Qualifier
  @Retention(RetentionPolicy.RUNTIME)
  @interface AppKey {}
  
  @Qualifier
  @Retention(RetentionPolicy.RUNTIME)
  @interface ServerAddress {}
  
  @Qualifier
  @Retention(RetentionPolicy.RUNTIME)
  @interface RequestCompression {}
}
