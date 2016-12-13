package io.github.spharris.electricity.actions;

import com.google.inject.AbstractModule;

public class ElectricityActionsModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(CalculatorAction.class);
  }
}
