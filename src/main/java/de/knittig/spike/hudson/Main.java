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
        HudsonClient hudsonClient = new HudsonClientImpl("http://localhost:8080");
        hudsonClient.createJob("hudson-client-spike", IOUtils.toString(Main.class.getResourceAsStream("/hudson-config.xml")));
    }

}
