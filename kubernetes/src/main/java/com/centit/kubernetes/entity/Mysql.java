package com.centit.kubernetes.entity;

public class Mysql {
    
    private String name;
    private int port;
    private String password;
    private boolean ready;
    private String version;

    public Mysql() {
    }

    public Mysql(String name, int port, String password) {
        this.name = name;
        this.port = port;
        this.password = password;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean isReady) {
        this.ready = isReady;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    
}
