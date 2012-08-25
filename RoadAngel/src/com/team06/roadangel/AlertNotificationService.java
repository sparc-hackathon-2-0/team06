package com.team06.roadangel;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import com.team06.roadangel.util.XMLParser;

/**
 * Created with IntelliJ IDEA.
 * User: ericwood
 * Date: 8/25/12
 * Time: 10:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class AlertNotificationService extends IntentService {

    private static final int HELLO_ID = 1;

    private boolean bRunning = false;

    private String serverUrl;
    private int serverPollInterval;
    private int serverConnectionTimeout;


    private String serverTag;

    private XMLParser xmlParser;

    private NotificationManager notificationManager;

    public AlertNotificationService() {
        super (AlertNotificationService.class.getName());
        xmlParser = new XMLParser();
    }

    @Override
    public void onCreate() {
        try {
            super.onCreate();    //To change body of overridden methods use File | Settings | File Templates.
            serverUrl = getString(R.string.server_url);
            serverPollInterval = getResources().getInteger(R.integer.alert_poll_interval_seconds);
            serverConnectionTimeout = getResources().getInteger(R.integer.server_connection_timeout_seconds);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String tmp = intent.getExtras().getString(getString(R.string.server_key_varname));
        if (stringIsNotEmpty(tmp)) {
            serverTag = tmp;
            bRunning = true;
            Thread alertNotificationThread = new Thread(new AlertNotificationThread());
            alertNotificationThread.run();
        }
    }

    @Override
    public void onDestroy() {
        bRunning = false;
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    private boolean stringIsNotEmpty(String test) {
        return test != null && !test.isEmpty();
    }

    private void createNotification() {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
        int icon = R.drawable.icon;
        CharSequence tickerText = "Hello";
        long when = System.currentTimeMillis();

        Notification notification = new Notification(icon, tickerText, when);

        Context context = getApplicationContext();
        CharSequence contentTitle = "My notification";
        CharSequence contentText = "Hello World!";
        Intent notificationIntent = new Intent(this, RoadAngelActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

        mNotificationManager.notify(HELLO_ID, notification);

    }

    private class AlertNotificationThread implements Runnable {

        public void run() {
           while (bRunning) {
               try {
                   String checkAlertResponse = xmlParser.getXmlFromUrl(buildAlertRequestUrl(serverTag), serverConnectionTimeout, serverPollInterval);
                   if (stringIsNotEmpty(checkAlertResponse)) {
                       getAlertCount(checkAlertResponse);
                   }

               } catch (Exception e) {
                   e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
               }
           }
        }



        private String buildAlertRequestUrl(String serverTag) {
            return serverUrl + "/" + getString(R.string.alert_request_url) + "?" +
                   getString(R.string.alert_request_key_param) + "=" + serverTag;  //To change body of created methods use File | Settings | File Templates.
        }

        private int getAlertCount(String checkAlertResponse) {
            try {
                return Integer.parseInt(checkAlertResponse);
            }
            catch (NumberFormatException e) {
                return 0;
            }
        }


    }
}
