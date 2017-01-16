package io.github.spharris.electricity.actions;

import static io.github.spharris.electricity.util.ExceptionUtil.checkBadRequest;

import com.genability.client.api.request.GetCalculatedCostRequest;
import com.genability.client.types.CalculatedCost;
import com.genability.client.types.PropertyData;
import com.genability.client.types.Response;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import io.github.spharris.electricity.calculator.TariffCalculator;
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
  
  private final TariffCalculator calculateService;
  
  @Inject
  public CalculatorAction(TariffCalculator calculateService) {
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
    checkBadRequest(request.getMasterTariffId() != null);
    checkBadRequest(request.getFromDateTime() != null);
    checkBadRequest(request.getToDateTime() != null);
    checkBadRequest(request.getToDateTime().isAfter(request.getFromDateTime()));
    
    return Response.of(calculateService.calculateTariffCost(normalizeRequestInputs(request)));
  }
  
  private static GetCalculatedCostRequest normalizeRequestInputs(GetCalculatedCostRequest request) {
    GetCalculatedCostRequest.Builder builder = request.toBuilder()
        .setRateInputs(MoreObjects.firstNonNull(request.getRateInputs(), ImmutableList.of()));

    if (request.getTariffInputs() != null) {
      ImmutableList.Builder<PropertyData> propertyBuilder = ImmutableList.builder();
      for (PropertyData pd : request.getTariffInputs()) {
        checkBadRequest(pd.getKeyName() != null);
        checkBadRequest(pd.getDataValue() != null);
        
        if (pd.getFromDateTime() == null || pd.getToDateTime() == null) {
          propertyBuilder.add(pd.toBuilder()
            .setFromDateTime(MoreObjects.firstNonNull(pd.getFromDateTime(),
              request.getFromDateTime()))
            .setToDateTime(MoreObjects.firstNonNull(pd.getToDateTime(), request.getToDateTime()))
            .build());
        } else {
          propertyBuilder.add(pd);
        }
      }
      
      builder.setTariffInputs(propertyBuilder.build());
    } else {
      builder.setTariffInputs(ImmutableList.of());
    }
    
    return builder.build();
  }
}
