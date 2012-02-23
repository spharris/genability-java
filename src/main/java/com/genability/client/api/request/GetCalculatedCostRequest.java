package com.genability.client.api.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.genability.client.types.PropertyData;
import org.apache.http.NameValuePair;
import org.joda.time.DateTime;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.genability.client.util.DateTimeJsonSerializer;

public class GetCalculatedCostRequest extends AbstractRequest implements Serializable {

	
	/**
	 * To request a results at a totals level.
	 */
	public static final String DETAIL_LEVEL_TOTAL = "TOTAL";
	
	/**
	 * To request a results at a charge type level (e.g. fixed, consumption, demand).
	 */
	public static final String DETAIL_LEVEL_CHARGE_TYPE = "CHARGE_TYPE";
	
	/**
	 * To request a results for each rate.
	 */
	public static final String DETAIL_LEVEL_RATE = "RATE";
	
	/**
	 * To request a results for each rate buck ... i.e. all details.
	 * This is the default response when running a calculation so you don't actually
	 * need to pass this argument in.
	 */
	public static final String DETAIL_LEVEL_ALL = "ALL";
	
	
	/**
	 * Use this constant for the groupBy value when you want to group readings
	 * annually.
	 */
	public static final String GROUP_BY_YEAR = "YEAR";

	/**
	 * Use this constant for the groupBy value when you want to group readings
	 * monthly.
	 */
	public static final String GROUP_BY_MONTH = "MONTH";

	/**
	 * Use this constant for the groupBy value when you want to group readings
	 * daily.
	 */
	// adding next week - public static final String GROUP_BY_DAY = "DAY";

	/**
	 * Use this constant for the groupBy value when you want to group readings
	 * hourly.
	 */
	// adding next week - public static final String GROUP_BY_HOUR = "HOUR";
	
	/**
	 * Use this constant for the groupBy value when you want to group readings
	 * into quarter-hour increments.
	 */
	// adding next week - public static final String GROUP_BY_QTRHOUR = "QTRHOUR";


	/**
	 * Private member variable serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Private member variable fromDate
	 */
	private DateTime fromDateTime;

	/**
	 * Private member variable toDate
	 */
	private DateTime toDateTime;

	/**
	 * Private member variable masterTariffId
	 */
	private Long masterTariffId;

	/**
	 * Private member variable profileId
	 */
	private String profileId;

	/**
	 * Private member variable Inputs
	 */
	private List<PropertyData> inputs;

	/**
	 * Private member variable detailLevel
	 * Possible values are: ALL, TOTAL, CHARGE_TYPE, RATE
	 * Default is ALL if not specified.
	 */
	private String detailLevel;

	/**
	 * Private member variable groupBy
	 * Possible values are: YEAR, MONTH, etc
	 * Default is no grouping if not specified.
	 */
	private String groupBy;
	
	/**
	 * Private member variable estimate_
	 */
	private String accuracy;

	/**
	 * Private member variable accountId
	 */
	private String accountId;
	
	/**
	 * Constructor to initialize instance
	 */
	public GetCalculatedCostRequest() {

	}

	/**
	 * Access method that retrieve fromDateTime
	 * 
	 * @return fromDateTime
	 */
	@JsonSerialize(using=DateTimeJsonSerializer.class) 
	public DateTime getFromDateTime() {
		return fromDateTime;
	}

	/**
	 * Mutator used to set the value of fromDateTime
	 * 
	 * @param fromDate
	 */
	public void setFromDateTime(DateTime fromDate) {
		fromDateTime = fromDate;
	}

	/**
	 * Access method that retrieve toDateTime
	 * 
	 * @return toDateTime
	 */
	@JsonSerialize(using=DateTimeJsonSerializer.class) 
	public DateTime getToDateTime() {
		return toDateTime;
	}

	/**
	 * Mutator used to set the value of toDateTime
	 * 
	 * @param toDate
	 */
	public void setToDateTime(DateTime toDate) {
		toDateTime = toDate;
	}

	
	/**
	 * The ID of the tariff to calculate for. Only needs to be set when
	 * not running against an account, or if you want to override the
	 * accounts tariff.
	 * @return
	 */
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	public Long getMasterTariffId() {
		return masterTariffId;
	}
	
	public void setMasterTariffId(Long masterTariffId) {
		this.masterTariffId = masterTariffId;
	}
	
	
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	/**
	 * Access method that retrieve profileId_
	 * 
	 * @return profileId
	 */
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	public String getProfileId() {
		return profileId;
	}

	/**
	 * Mutator used to set the value of profileId_
	 * 
	 * @param profileId
	 */
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	/**
	 * @return the detailLevel
	 */
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	public String getDetailLevel() {
		return detailLevel;
	}

	/**
	 * @param detailLevel
	 *            the detailLevel to set
	 */
	public void setDetailLevel(String detailLevel) {
		this.detailLevel = detailLevel;
	}

	/**
	 * @return the groupBy
	 */
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	public String getGroupBy() {
		return groupBy;
	}

	/**
	 * @param groupBy
	 *            the groupBy to set
	 */
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	/**
	 * @return the estimate
	 */
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	public String getAccuracy() {
		return accuracy;
	}

	/**
	 * @param estimate
	 *            the estimate to set
	 */
	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}


	/**
	 * Access method that retrieve tariffInputs
	 * 
	 * @return inputs
	 */
	@JsonProperty("tariffInputs")
	public List<PropertyData> getInputs() {
		return inputs;
	}

	/**
	 * Mutator used to set the value of tariffInputs
	 * 
	 * @param propertyDatas
	 */
	public void setInputs(List<PropertyData> propertyDatas) {
		inputs = propertyDatas;
	}
	
<<<<<<< HEAD
=======
	public String getAccountId() {
		return accountId;
	}

>>>>>>> added support for providerAccountId
	/**
	 * Helper method for adding a PropertyData entry into the collection
	 * of inputs.
	 * 
	 * @param input
	 */
	public void addInput(PropertyData input) {
		if (inputs == null || inputs.size() == 0) {
			inputs = new ArrayList<PropertyData>();
		}
		inputs.add(input);
	}
	
	@Override
	@JsonIgnore
	public List<NameValuePair> getQueryParams() {
		
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		addParam(qparams,"masterTariffId",masterTariffId);
		addParam(qparams,"betaPopulateAssumptions",true);
		return qparams;
		
	}	
}
