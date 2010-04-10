package de.knittig.spike.hudson.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.io.IOUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.filter.ElementFilter;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import de.knittig.spike.hudson.Job;


public class HudsonClientImpl implements HudsonClient {

    private final String hudsonUrl;
    private final HttpClient httpClient;

    public HudsonClientImpl(String hudsonUrl) {
        this.hudsonUrl = hudsonUrl;
        this.httpClient = new HttpClient();
    }

    @Override
    public void createJob(Job job) {
        PostMethod createItemRequest = new PostMethod(hudsonUrl + "/createItem");
        try {
            createItemRequest.setQueryString(URIUtil.encodeQuery("name=" + job.getName()));
            createItemRequest.setRequestEntity(new StringRequestEntity(job.getXmlConfiguration(), "text/xml", "UTF-8"));
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

    private String executeMethod(HttpMethodBase method) {
        try {
            httpClient.executeMethod(method);
            return method.getResponseBodyAsString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            method.releaseConnection();
        }
    }

    @Override
    public void buildJob(Job job) {
        GetMethod buildRequest = new GetMethod(hudsonUrl + "/job/" + job.getName() + "/build");
        executeMethod(buildRequest);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Job> getJobs() {
        GetMethod jobsRequest = new GetMethod(hudsonUrl + "/api/xml");
        String response = executeMethod(jobsRequest);
        List<Job> result = new ArrayList<Job>();

        try {
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(IOUtils.toInputStream(response));
            for (Iterator it = (Iterator<Element>) document.getDescendants(new ElementFilter("job")); it.hasNext();) {
                result.add(new Job(((Element) it.next()).getChildText("name")));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

}
