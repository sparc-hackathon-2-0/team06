package com.team06.roadangel;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.widget.Toast;
import com.team06.roadangel.util.XMLParser;
import org.w3c.dom.Document;

/**
 * Created with IntelliJ IDEA.
 * User: ericwood
 * Date: 8/25/12
 * Time: 10:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class AlertNotificationService extends IntentService {

    private static final int ALERT_ID = 1;

    private boolean bRunning = false;

    private RoadAngelService roadAngelService;

    private NotificationManager notificationManager;

    public AlertNotificationService() {
        super (AlertNotificationService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String serverKey = intent.getExtras().getString(getString(R.string.server_key_varname));
        if (stringIsNotEmpty(serverKey)) {
            roadAngelService = new RoadAngelService(serverKey, this.getApplicationContext());
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

    private void createNotification(int alertCount) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
        int icon = R.drawable.icon;
        CharSequence tickerText = getString(R.string.alert_ticker_text);
        long when = System.currentTimeMillis();

        Notification notification = new Notification(icon, tickerText, when);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        Context context = getApplicationContext();
        CharSequence contentTitle = getString(R.string.alert_title);
        CharSequence contentText = getString(R.string.alert_text_prefix) + " " + String.valueOf(alertCount) +
                                   " " + getString(R.string.alert_text_suffix);
        Intent notificationIntent = new Intent(this, RoadAngelActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

        mNotificationManager.notify(ALERT_ID, notification);

    }

    private class AlertNotificationThread implements Runnable {

        @Override
        public void run() {
           while (bRunning) {
               int alertCount = roadAngelService.getAlertCount();
               if (alertCount > 0) {
                   createNotification(alertCount);
               }
           }
        }

    }
}
