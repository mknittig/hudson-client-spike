package de.knittig.spike.hudson.client;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.util.URIUtil;


public class HudsonClientImpl implements HudsonClient {

    private final String hudsonUrl;
    private final HttpClient httpClient;

    public HudsonClientImpl(String hudsonUrl) {
        this.hudsonUrl = hudsonUrl;
        this.httpClient = new HttpClient();
    }

    public void createJob(String name, String xmlConfiguration) {
        PostMethod createItemRequest = new PostMethod(hudsonUrl + "/createItem");
        try {
            createItemRequest.setQueryString(URIUtil.encodeQuery("name=" + name));
            createItemRequest.setRequestEntity(new StringRequestEntity(xmlConfiguration, "text/xml", "UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        createItemRequest.setRequestHeader("Content-type", "text/xml; charset=UTF-8");

        executeMethod(createItemRequest);

        if (createItemRequest.getStatusCode() == HttpStatus.SC_OK) {
            System.out.println("Created job!");
        } else {
            System.err.println("Failed to job!");
        }
    }

    private void executeMethod(HttpMethodBase method) {
        try {
            httpClient.executeMethod(method);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            method.releaseConnection();
        }
    }

    public void buildJob(String name) {
        GetMethod buildRequest = new GetMethod(hudsonUrl + "/job/" + name + "/build");
        executeMethod(buildRequest);
    }

}
