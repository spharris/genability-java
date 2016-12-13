package io.github.spharris.electricity.actions;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.genability.client.api.request.GetCalculatedCostRequest;
import com.genability.client.types.Tariff;

@Path("/calculator")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CalculatorAction {
  
  @POST
  public Tariff getCalculation(GetCalculatedCostRequest request) {
    return Tariff.builder()
        .setMasterTariffId(request.getMasterTariffId())
        .build();
  }
}
