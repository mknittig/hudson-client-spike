package de.knittig.spike.hudson;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

import de.knittig.spike.hudson.client.HudsonClient;
import de.knittig.spike.hudson.client.HudsonClientImpl;

public class Main {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Job job = new Job("hudson-client-spike");
        job.setXmlConfiguration(IOUtils.toString(Main.class.getResourceAsStream("/hudson-config.xml")));
        HudsonClient hudsonClient = new HudsonClientImpl("http://localhost:8080");
        hudsonClient.createJob(job);
        hudsonClient.buildJob(job);
    }

}
