package com.example.android.basicnotifications;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;

/**
 * The entry point to the BasicNotification sample.
 */
public class MainActivity extends Activity {
    /**
     * A numeric value that identifies the notification that we'll be sending.
     * This value needs to be unique within this app, but it doesn't need to be
     * unique system-wide.
     */
    public static final int NOTIFICATION_ID = 1;
    public static final int NOTIFICATION_ID2 = 2;
    public static final int NOTIFICATION_ID3 = 3;
    public static final int NOTIFICATION_ID4 = 4 ;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_layout);

    }

    /**
     * Send a sample notification using the NotificationCompat API.
     */
    public void sendNotification(View view) {

        // BEGIN_INCLUDE(build_action)
        /** Create an intent that will be fired when the user clicks the notification.
         * The intent needs to be packaged into a {@link android.app.PendingIntent} so that the
         * notification service can fire it on our behalf.
         */
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://developer.android.com/reference/android/app/Notification.html"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        // END_INCLUDE(build_action)

        // BEGIN_INCLUDE (build_notification)
        /**
         * Use NotificationCompat.Builder to set up our notification.
         */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        /** Set the icon that will appear in the notification bar. This icon also appears
         * in the lower right hand corner of the notification itself.
         *
         * Important note: although you can use any drawable as the small icon, Android
         * design guidelines state that the icon should be simple and monochrome. Full-color
         * bitmaps or busy images don't render well on smaller screens and can end up
         * confusing the user.
         */
        builder.setSmallIcon(R.drawable.ic_stat_notification);

        // Set the intent that will fire when the user taps the notification.
        builder.setContentIntent(pendingIntent);

        // Set the notification to auto-cancel. This means that the notification will disappear
        // after the user taps it, rather than remaining until it's explicitly dismissed.
        builder.setAutoCancel(true);

        /**
         *Build the notification's appearance.
         * Set the large icon, which appears on the left of the notification. In this
         * sample we'll set the large icon to be the same as our app icon. The app icon is a
         * reasonable default if you don't have anything more compelling to use as an icon.
         */
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));

        /**
         * Set the text of the notification. This sample sets the three most commononly used
         * text areas:
         * 1. The content title, which appears in large type at the top of the notification
         * 2. The content text, which appears in smaller text below the title
         * 3. The subtext, which appears under the text on newer devices. Devices running
         *    versions of Android prior to 4.2 will ignore this field, so don't use it for
         *    anything vital!
         */
        builder.setContentTitle("BasicNotifications Sample");
        builder.setContentText("Time to learn about notifications!");
        builder.setSubText("Tap to view documentation about notifications.");

        //build an inten for an action to view a mapIntent mapIntent = new Intent(Intent.ACTION_VIEW);
//        Uri geoUri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
//        mapIntent.setData(geoUri);
//        PendingIntent mapPendingIntent = PendingIntent.getActivity(this, 0, mapIntent, 0);
//
//        builder.addAction(R.drawable.ic_map, getString(R.string.map), mapPendingIntent);
//

        //add more pages to the wearable notification
        NotificationCompat.BigTextStyle secondPageStyle = new NotificationCompat.BigTextStyle();
        secondPageStyle.setBigContentTitle("Page 2")
                .bigText("A lot of text");

        Notification secondPageNotification = new NotificationCompat.Builder(this)
                .setStyle(secondPageStyle)
                .build();

        //add a third page
        NotificationCompat.BigTextStyle thirdPageStyle = new NotificationCompat.BigTextStyle();
        thirdPageStyle.setBigContentTitle("Page 3")
                .bigText("A lot of text3");

        Notification thirdPageNotification = new NotificationCompat.Builder(this)
                .setStyle(thirdPageStyle)
                .build();

        Notification notification = builder
                .extend(new NotificationCompat.WearableExtender()
                        .addPage(secondPageNotification)
                        .addPage(thirdPageNotification))
                .build();



        // END_INCLUDE (build_notification)

        // BEGIN_INCLUDE(send_notification)
        /**
         * Send the notification. This will immediately display the notification icon in the
         * notification bar.
         */
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(NOTIFICATION_ID, notification);
        // END_INCLUDE(send_notification)
    }

    public void sendStackingNotification(View v) {
        final String GROUP_KEY = "group_key";
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("New mail from bob")
                .setContentText("subject")
                .setSmallIcon(R.drawable.ic_stat_notification)
                .setGroup(GROUP_KEY)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID2, notification);

        Notification notification2 = new NotificationCompat.Builder(this)
                .setContentTitle("New mail from noah")
                .setContentText("subject2")
                .setSmallIcon(R.drawable.ic_stat_notification)
                .setGroup(GROUP_KEY)
                .build();

        notificationManager.notify(NOTIFICATION_ID3, notification2);

        //also build a summary notification for handhelds

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_stat_notification);

        Notification notificationSummary = new NotificationCompat.Builder(this)
                .setContentTitle("2 new messages")
                .setSmallIcon(R.drawable.ic_stat_notification)
                .setLargeIcon(largeIcon)
                .setStyle(new NotificationCompat.InboxStyle()
                    .addLine("Bob message")
                    .addLine("Noah message")
                    .setBigContentTitle("2 new messages")
                    .setSummaryText("email@email.com"))
                .setGroup(GROUP_KEY)
                .setGroupSummary(true)
                .build();

        notificationManager.notify(NOTIFICATION_ID4, notificationSummary);
    }


}
