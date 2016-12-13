package io.github.spharris.electricity.server;

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
    return ImmutableList.of(
      GenabilityClientModule.builder(
        "3c53fcf0-7601-4a60-9a8e-a6f383d94763", "05e50099-4d3c-449a-bbc8-e27ea52737a4")
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
}
