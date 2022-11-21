package com.noetic.gwpartner.timwe.Model;

import com.noetic.gwpartner.timwe.constants.Constants;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class HTTPSDPMT {

    private String msisdn;
    private String shortcode;
    private String data;
    private String transId;
    private String connectionPointId;
    private String username = "sashalab";
    private String password = "sashalab@2022";
    private String serviceid;

    private static final Logger log = LoggerFactory.getLogger(HTTPSDPMT.class);

    //private static HttpClient httpclient = null;
    private final String CHARSET = "utf-8";

    private HashMap<String, String> parameterMap = new HashMap<String, String>();
    private HashMap<String, String> postParametersMap = new HashMap<String, String>();

    public HTTPSDPMT(String msisdn, String shortcode, String data) {

        this.msisdn = msisdn;
        this.shortcode = shortcode;
        this.data = data;
        Integer scint = Integer.valueOf(shortcode);

        if(scint==3444){ this.serviceid = "1167"; }
        log.info("The values are MSISDN"+ msisdn + "Shortcode" + shortcode + "Data" + data + "ServiceID"+serviceid);
    }

    public HttpResponse sendRequest(boolean requiresAuthorization) throws ClientProtocolException, IOException {
		/*//#################################################
		PostMethod postMethod = new PostMethod();
		//#################################################*/

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpRequest = getHttpRequest(requiresAuthorization);
        // execute the post request
        HttpResponse httpResponse = httpclient.execute(httpRequest);
        //httpclient.getConnectionManager().shutdown();  //must close the connection resources
        return httpResponse;
    }


    private void addPostParameters() {
        this.postParametersMap.put("username", this.username);
        this.postParametersMap.put("password", this.password);
        this.postParametersMap.put("shortcode", this.shortcode);
        this.postParametersMap.put("data", this.data);
        this.postParametersMap.put("serviceid", this.serviceid);
        this.postParametersMap.put("msisdn", this.msisdn);
    }

    private HttpPost getHttpRequest(boolean requiresAuthorization) throws UnsupportedEncodingException {
        addPostParameters();
        String queryString = "";
        // This is the entry set retrieved from parameter map
        Set<Map.Entry<String, String>> getPairs = this.parameterMap.entrySet();

        // add the name-value pairs to the queryString
        for (Map.Entry<String, String> entry : getPairs) {
            queryString += URLEncoder.encode(entry.getKey(), this.CHARSET) + "=" + URLEncoder.encode(entry.getValue(), this.CHARSET) + "&";
        }

        // add ? at the start of the query string
        if (!queryString.isEmpty()) {
            queryString = "?" + queryString;
        }

        //log.info("QUERY GENERATED| "+queryString +"| FOR URL | "+ Constants.TIMWE_MO_URL);
        HttpPost httpRequest = new HttpPost(Constants.SDPMTURL + queryString);

        // log.debug("The queryString is " + queryString);

        // This represents the list of nameValuePairs that will be send through
        // the request
        List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();

        // This is the entry set retrieved from parameter map
        Set<Map.Entry<String, String>> postPairs = this.postParametersMap.entrySet();

        for(Map.Entry<String, String> entry : postPairs) {
            // Each name value pair being added to nameValuePairs ArrayList
            BasicNameValuePair pair = new BasicNameValuePair(entry.getKey(),
                    entry.getValue());
            nameValuePairs.add(pair);
        }

        // Attach the nameValuePairs with the HttpPost request
        UrlEncodedFormEntity parameters = new UrlEncodedFormEntity(
                nameValuePairs, this.CHARSET);
        httpRequest.setEntity(parameters);

        return httpRequest;
    }



}
