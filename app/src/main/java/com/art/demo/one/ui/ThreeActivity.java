package com.art.demo.one.ui;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.art.demo.one.R;

public class ThreeActivity extends AppCompatActivity implements View.OnClickListener {

    private GestureDetector.OnGestureListener gestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }
    };

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDB();
    }

    private void initView() {
        final Button button = findViewById(R.id.button);
        button.setOnClickListener(this);

    }

    private void initDB() {
        gestureDetector = new GestureDetector(this, gestureListener);
        gestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });
        gestureDetector.setIsLongpressEnabled(false);//解决长按屏幕后无法拖动的现象
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public void onClick(View view) {
    /*    ContentValues contentValues = new ContentValues();
        contentValues.put("_id", "6");
        contentValues.put("name", "天龙八部");
        Uri uri = Uri.parse("content://com.art.demo.one.provider/book");
        getContentResolver().insert(uri, contentValues);
        Cursor cursor = getContentResolver().query(uri, new String[]{"_id", "name"}, null, null, null);
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String name = cursor.getString(1);
            Log.i("wak", _id + "::::" + name);
        }
        cursor.close();*/
/*        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_messsage_icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_messsage_icon))
                .setContentIntent(pendingIntent)
                .setContentTitle("我是一个标题")
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentText("我是一个内容")
                .setWhen(System.currentTimeMillis())//设置通知时间，一般为系统的当前时间，
                .build();

        getNotificationManager().notify(0, notification);*/
        Intent intent = new Intent(this, MessageUiActivity.class);
        startActivity(intent);

    }
}
