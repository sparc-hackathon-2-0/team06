package com.team06.roadangel.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ericwood
 * Date: 8/25/12
 * Time: 7:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class Alert {

    String licensePlate;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    String state;
    String reason;
    Date time;
    String fromKey;

    public String getFromKey() {
        return fromKey;
    }

    public void setFromKey(String fromKey) {
        this.fromKey = fromKey;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}

