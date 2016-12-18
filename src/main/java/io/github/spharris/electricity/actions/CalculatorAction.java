package io.github.spharris.electricity.actions;

import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.genability.client.api.request.GetCalculatedCostRequest;
import com.genability.client.api.service.CalculateService;
import com.genability.client.types.CalculatedCost;
import com.genability.client.types.Response;

@Path("/calculator")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CalculatorAction {
  
  private final CalculateService calculateService;
  
  @Inject
  public CalculatorAction(CalculateService calculateService) {
    this.calculateService = calculateService;
  }
  
  @POST
  public Response<CalculatedCost> getCalculation(GetCalculatedCostRequest request)
      throws ExecutionException, InterruptedException {
    return calculateService.getCalculatedCost(request).get();
  }
}
