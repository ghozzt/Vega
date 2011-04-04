package com.subgraph.vega.impl.scanner.requests;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;

import com.subgraph.vega.api.model.web.IWebPath;

public class GetParameterRequestBuilder extends AbstractParameterRequestBuilder {

	
	public GetParameterRequestBuilder(IWebPath path, List<NameValuePair> parameters, int index) {
		super(path, parameters, index);
	}

	@Override
	public HttpUriRequest createBasicRequest() {
		return createRequestFromQuery(formatDefaultParameters());
	}

	@Override
	public HttpUriRequest createAlteredRequest(String value, boolean append) {
		return createRequestFromQuery(formatParametersWithFuzz(value, append, false));
	}

	@Override
	public HttpUriRequest createAlteredParameterNameRequest(String name) {
		return createRequestFromQuery(formatParametersWithFuzz(name, false, true));
	}
	
	
	private String formatDefaultParameters() {
		return formatParameterList(parameters);
	}

	private String formatParametersWithFuzz(String fuzzValue, boolean append, boolean fuzzName) {
		return formatParameterList( getAlteredParameters(fuzzValue, append, fuzzName));
	}
	
	private String formatParameterList(List<NameValuePair> plist) {
		final StringBuilder sb = new StringBuilder();
		for(int i = 0; i < plist.size(); i++) {
			NameValuePair p = plist.get(i);
			if(i != 0)
				sb.append("&");
			sb.append(p.getName());
			if(p.getValue() != null) {
				sb.append("=");
				sb.append(p.getValue());
			}
		}
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return "GET "+ getBasePath() + "?" + formatDefaultParameters() + " (idx="+ parameterFuzzIndex +")";
	}
}
