package com.selfyclubb;

import android.content.Intent;
import androidx.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;

//import com.onesignal.OSNotification;
//import com.onesignal.OSNotificationOpenResult;
//import com.onesignal.OneSignal;

import org.json.JSONException;

/**
 * Created by Jiten on 13-04-2018.
 */

public class MMSApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }
        // One sigla Integration
////        OneSignal.startInit(this)
////                .autoPromptLocation(false)
////                .setNotificationReceivedHandler(new OneNotificationReceivedHandler())
////                .setNotificationOpenedHandler(new OneNotificationOpenedHandler())
////                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
////                .init();
//    }
//
//    private class OneNotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {
//        @Override
//        public void notificationReceived(OSNotification notification) {
//
//        }
//    }
//
//    private class OneNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
//
//        @Override
//        public void notificationOpened(OSNotificationOpenResult result) {
//
//            Log.e("", "result::" + result.toJSONObject().toString());
//            String url = "";
//            if (result != null && result.notification != null
//                    && result.notification.payload != null
//                    && result.notification.payload.additionalData != null
//                    && result.notification.payload.additionalData.has("url")) {
//
//                try {
//                    url = result.notification.payload.additionalData.getString("url");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            Log.e("notificationOpened", "url::"+url);
//            if (!TextUtils.isEmpty(url)) {
//
//                Intent resultIntent = new Intent(TutorialActivity.FILTER_GET_URL);
//                resultIntent.putExtra(TutorialActivity.BNDL_URL, url);
//                sendBroadcast(resultIntent);
//
//            } else {
//                Intent resultIntent = new Intent(getApplicationContext(), TutorialActivity.class);
//                resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(resultIntent);
//            }
//
//
//        }
//    }
}
