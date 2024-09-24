package com.clarence.test;

import java.util.UUID;

public class targetData {
    private final UUID uuid;
    private final String name;
    private final String reason;
    private int numberOfReports;

    public targetData(UUID uuid, String name, String reason) {
        this.uuid = uuid;
        this.name = name;
        this.reason = reason;
    }

    public String getPlayerName() {
        return name;
    }

    public UUID getPlayerUuid() {
        return uuid;
    }
    public void increaseNumberOfReport() {numberOfReports++;}
    public int getNumberOfReports() {return numberOfReports; }
    public String getReason() {return reason; }
}
