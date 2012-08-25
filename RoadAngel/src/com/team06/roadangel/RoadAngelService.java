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


    private XMLParser xmlParser;

    public RoadAngelService(Context appContext) {
        this.appContext = appContext;
        xmlParser = new XMLParser();
        serverUrl = appContext.getString(R.string.server_url);
        serverPollInterval = appContext.getResources().getInteger(R.integer.alert_poll_interval_seconds);
        serverConnectionTimeout = appContext.getResources().getInteger(R.integer.server_connection_timeout_seconds);
    }

    public int getAlertCount(String serverKey) {
        String requestUrl = buildAlertRequestUrl(serverKey);
        String checkAlertResponse = xmlParser.getXmlFromUrl(requestUrl, serverConnectionTimeout,
                serverPollInterval);
        if (stringIsNotEmpty(checkAlertResponse)) {
            return getAlertCount(checkAlertResponse);
        }
        return 0;
    }

    public String register(String licensePlate, String state) {
        String requestUrl = buildRegisterRequestUrl(licensePlate, state);
        String registerResponse = xmlParser.getXmlFromUrl(requestUrl, serverConnectionTimeout,
                serverPollInterval);
        if (stringIsNotEmpty(registerResponse)) {
            return getKeyFromResponse(registerResponse);
        }
        return "";
    }

    private String buildAlertRequestUrl(String serverKey) {
        return serverUrl + "/" + appContext.getString(R.string.alert_request_url) + "?" +
                appContext.getString(R.string.alert_request_key_param) + "=" + serverKey;
    }

    private String buildRegisterRequestUrl(String licensePlate, String state) {
        return serverUrl + "/" + appContext.getString(R.string.register_request_url) + "?" +
                appContext.getString(R.string.register_request_license_plate_param) + "=" + licensePlate +
                "&" + appContext.getString(R.string.register_request_state_param) + "=" + state;
    }

    private int getAlertCountFromResponse(String checkAlertResponse) {
        try {
            return Integer.parseInt(checkAlertResponse);
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }

    private String getKeyFromResponse(String registerResponse) {
        return registerResponse;
    }

    private boolean stringIsNotEmpty(String test) {
        return test != null && !test.isEmpty();
    }
}
