package com.art.demo.one.ui;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.art.demo.one.MainActivity;
import com.art.demo.one.R;
import com.art.demo.one.service.ForegroundService;

public class MessageUiActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;

    @SuppressLint("HandlerLeak")//Suppress 压制   Leak 泄漏
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    showNotificationLight();
                    break;
                case 1:
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
        initListener();
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private void initView() {
        button = findViewById(R.id.button2);
        Button button1 = findViewById(R.id.button3);
        Button button2 = findViewById(R.id.button4);
        Button button3 = findViewById(R.id.button5);
        Button button4 = findViewById(R.id.button6);
        Button button5 = findViewById(R.id.button7);
        Button button6 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button button10 = findViewById(R.id.button10);
        Button button11 = findViewById(R.id.button11);
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button9.setOnClickListener(this);
        button10.setOnClickListener(this);
        button11.setOnClickListener(this);
    }

    private void initListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                showVibrateNotification();
                break;
            case R.id.button3:
                handler.sendEmptyMessageDelayed(0, 2000);
                break;
            case R.id.button4:
                showVoiceNotification();
                break;
            case R.id.button5:
                showSelfVoiceNotification();
                break;
            case R.id.button6:
                showInsistentNotification();
                break;
            case R.id.button7:
                showOnceNotification();
                break;
            case R.id.button8:
                showOnlyCancelNotification();
                break;
            case R.id.button9:
                showForegroundNotification();
                break;
            case R.id.button10:
                showRemotesViewsNotification();
                break;
            case R.id.button11:
                showRemotesProgressViewsNotification();
                break;
        }
    }

    /**
     * 带进度条的自定义
     */
    private void showRemotesProgressViewsNotification() {
        new ProgressAsyncTask().execute();
    }

    /**
     * 普通自定义
     */
    private void showRemotesViewsNotification() {//自定义RemoteViews实现通知
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        remoteViews.setTextViewText(R.id.tvRemoteViewsTitle, "呵呵，我才是真正的表头");
        remoteViews.setTextViewText(R.id.tvRemoteViewsText, "呵呵，我才是真正的尾部");
        Notification notification = new NotificationCompat.Builder(this)
                .setOngoing(true)
                .setSmallIcon(R.mipmap.ic_messsage_icon)
                .setCustomContentView(remoteViews)
                .build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;

        getNotificationManager().notify(8, notification);
    }

    private void showForegroundNotification() {
        Intent intent = new Intent(this, ForegroundService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }

    private void showOnlyCancelNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_messsage_icon))
                .setSmallIcon(R.mipmap.ic_messsage_icon)
                .setContentTitle("默认全部有铃声震动和灯光")
                .setContentTitle("测试不能取消的通知")
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)//用户响应后会自动消失
                .build();
        // notification.flags = Notification.FLAG_NO_CLEAR;
        getNotificationManager().notify(6, notification);
    }

    private void showOnceNotification() {
        Notification notification = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_messsage_icon))
                .setSmallIcon(R.mipmap.ic_messsage_icon)
                .setContentTitle("只有一次的震动和铃声")
                .setContentTitle("测试只有一次的震动和铃声")
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.voice))
                .build();
        notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;
        getNotificationManager().notify(5, notification);
    }

    /**
     * 无限的震动
     */
    private void showInsistentNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_messsage_icon))
                .setSmallIcon(R.mipmap.ic_messsage_icon)
                .setContentTitle("无限的震动和铃声")
                .setContentTitle("测试无限的震动和铃声")
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.voice))
                .build();
        notification.flags = Notification.FLAG_INSISTENT;
        getNotificationManager().notify(4, notification);
    }

    private void showSelfVoiceNotification() {
        Notification notification = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_messsage_icon))
                .setSmallIcon(R.mipmap.ic_messsage_icon)
                .setContentTitle("自定义铃声")
                .setContentTitle("测试自定义铃声")
                .setWhen(System.currentTimeMillis())
                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.voice))
                .build();
        getNotificationManager().notify(4, notification);
    }

    private void showVoiceNotification() {
        Notification notification = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_messsage_icon))
                .setSmallIcon(R.mipmap.ic_messsage_icon)
                .setContentTitle("铃声")
                .setContentTitle("测试铃声")
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND)
                .build();
        getNotificationManager().notify(3, notification);
    }

    private void showNotificationLight() {


        Notification notification = new NotificationCompat.Builder(MessageUiActivity.this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_messsage_icon))
                .setSmallIcon(R.mipmap.ic_messsage_icon)
                .setContentTitle("亮灯")
                .setContentTitle("测试亮灯")
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .build();
        notification.flags = Notification.FLAG_SHOW_LIGHTS;
        getNotificationManager().notify(2, notification);
    }

    private void showVibrateNotification() {
        Notification notification = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_messsage_icon))
                .setSmallIcon(R.mipmap.ic_messsage_icon)
                .setContentTitle("震动一下")
                .setContentTitle("测试震动")
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .build();

        getNotificationManager().notify(1, notification);
    }

    private class ProgressAsyncTask extends AsyncTask<Void, Integer, Void> {

        private RemoteViews remoteViews;
        private Notification notification;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            remoteViews = new RemoteViews(getPackageName(), R.layout.layout_progress_notification);
            remoteViews.setProgressBar(R.id.progress, 100, 0, false);
            notification = new NotificationCompat.Builder(MessageUiActivity.this)
                    .setSmallIcon(R.mipmap.ic_messsage_icon)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setOnlyAlertOnce(true)
                    .setCustomContentView(remoteViews)
                    .build();
           // notification.flags = Notification.FLAG_ONGOING_EVENT;

            getNotificationManager().notify(8, notification);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                int i = 0;
                while (i <= 100) {
                    Thread.sleep(2000);
                    publishProgress(i);
                    i = i + 10;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            remoteViews.setProgressBar(R.id.progress, 100, progress[0].intValue(), false);
            getNotificationManager().notify(8, notification);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
