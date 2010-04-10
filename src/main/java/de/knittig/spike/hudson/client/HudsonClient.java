package de.knittig.spike.hudson.client;

import java.util.List;

import de.knittig.spike.hudson.Job;

public interface HudsonClient {

    void createJob(Job job);

    void buildJob(Job job);

}
