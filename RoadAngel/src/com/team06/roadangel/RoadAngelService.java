package com.team06.roadangel;

import android.content.Context;
import android.text.StaticLayout;
import com.team06.roadangel.util.XMLParser;

/**
 * Created with IntelliJ IDEA.
 * User: ericwood
 * Date: 8/25/12
 * Time: 4:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class RoadAngelService {

    private Context appContext;

    private String serverUrl;
    private int serverPollInterval;
    private int serverConnectionTimeout;


    private String serverKey;

    private XMLParser xmlParser;

    public RoadAngelService(String serverKey, Context appContext) {
        this.serverKey = serverKey;
        this.appContext = appContext;
        xmlParser = new XMLParser();
        serverUrl = appContext.getString(R.string.server_url);
        serverPollInterval = appContext.getResources().getInteger(R.integer.alert_poll_interval_seconds);
        serverConnectionTimeout = appContext.getResources().getInteger(R.integer.server_connection_timeout_seconds);
    }

    public int getAlertCount() {
        String requestUrl = buildAlertRequestUrl(serverKey);
        String checkAlertResponse = xmlParser.getXmlFromUrl(requestUrl, serverConnectionTimeout,
                serverPollInterval);
        if (stringIsNotEmpty(checkAlertResponse)) {
            return getAlertCount(checkAlertResponse);
        }
        return 0;
    }

    public String register(String licensePlate, String state) {
        return "";
    }

    private String buildAlertRequestUrl(String serverKey) {
        return serverUrl + "/" + appContext.getString(R.string.alert_request_url) + "?" +
                appContext.getString(R.string.alert_request_key_param) + "=" + serverKey;
    }

    private int getAlertCount(String checkAlertResponse) {
        try {
            return Integer.parseInt(checkAlertResponse);
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }

    private boolean stringIsNotEmpty(String test) {
        return test != null && !test.isEmpty();
    }
}
