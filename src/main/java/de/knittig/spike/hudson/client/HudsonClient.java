package de.knittig.spike.hudson.client;

public interface HudsonClient {

    void createJob(String name, String xmlConfiguration);

    void buildJob(String name);
}
