package io.github.spharris.electricity.actions;

import static io.github.spharris.electricity.util.ExceptionUtil.checkBadRequest;

import com.genability.client.api.request.GetCalculatedCostRequest;
import com.genability.client.types.CalculatedCost;
import com.genability.client.types.Response;
import io.github.spharris.electricity.services.TariffCalculatorService;
import java.util.concurrent.ExecutionException;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/calculator")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CalculatorAction {
  
  private final TariffCalculatorService calculateService;
  
  @Inject
  public CalculatorAction(TariffCalculatorService calculateService) {
    this.calculateService = calculateService;
  }
  
  @POST
  @Path("/{masterTariffId}")
  public Response<CalculatedCost> getCalculation(
      @PathParam("masterTariffId") long masterTariffId, GetCalculatedCostRequest request)
      throws ExecutionException, InterruptedException {
    return getCalculation(request.toBuilder().setMasterTariffId(masterTariffId).build());
  }
  
  @POST
  @Path("/")
  public Response<CalculatedCost> getCalculation(GetCalculatedCostRequest request)
      throws ExecutionException, InterruptedException {
    checkBadRequest(request.getFromDateTime() != null);
    checkBadRequest(request.getToDateTime() != null);
    checkBadRequest(request.getToDateTime().isAfter(request.getFromDateTime()));
    
    return Response.of(calculateService.calculateTariffCost(request));
  }
     
}
