package de.knittig.spike.hudson;

public class Job {

    private final String name;
    private String xmlConfiguration;

    public Job(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getXmlConfiguration() {
        return xmlConfiguration;
    }

    public void setXmlConfiguration(String xmlConfiguration) {
        this.xmlConfiguration = xmlConfiguration;
    }

}
