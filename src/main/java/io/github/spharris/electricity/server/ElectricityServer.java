package io.github.spharris.electricity.server;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.genability.client.api.GenabilityClientModule;
import com.google.common.collect.ImmutableList;
import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provides;

import io.github.spharris.electricity.actions.ElectricityActionsModule;

public class ElectricityServer {

  public static void main(String[] args) throws Exception {
    Injector injector = Guice.createInjector(getModules());
    Server server = new Server(3000);
    
    HandlerCollection collection = new HandlerCollection();
    collection.addHandler(createApi(server, injector));
    
    server.setHandler(collection);
    
    server.start();
    server.join();
  }
  
  private static ServletContextHandler createApi(Server server, Injector injector)
      throws Exception {
    ServletContextHandler handler = new ServletContextHandler(server, "/");
    handler.addEventListener(injector.getInstance(
      GuiceResteasyBootstrapServletContextListener.class));

    ServletHolder sh = new ServletHolder(HttpServletDispatcher.class);
    handler.addServlet(sh, "/*");
    
    return handler;
  }

  private static ImmutableList<Module> getModules() {
    Properties creds = getApiCreds();
    return ImmutableList.of(
      GenabilityClientModule.builder(creds.getProperty("appId"), creds.getProperty("appKey"))
        .build(),
      new ElectricityActionsModule(),
      new AbstractModule() {
        
        @Override
        protected void configure() {}
        
        @Provides
        JacksonJsonProvider provideJacksonJsonProvider(ObjectMapper mapper) {
          return new JacksonJsonProvider(mapper);
        }
      });
  }
  
  private static Properties getApiCreds() {
    CharSource propsFile = Resources.asCharSource(
      Resources.getResource("genability.properties"), StandardCharsets.UTF_8);
    
    try (Reader reader = propsFile.openStream()) {
      Properties properties = new Properties();
      properties.load(reader);
      return properties;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
