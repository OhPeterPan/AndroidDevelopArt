package com.art.demo.one;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.art.demo.one.service.MyService;
import com.art.demo.one.ui.AnimationActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {

        }
    };
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            Messenger messenger = new Messenger(iBinder);
            Messenger clientMessenger = new Messenger(handler);
            Message message = Message.obtain();
            message.replyTo = clientMessenger;
            Bundle bundle = new Bundle();
            bundle.putString("client", "我发送一个消息");
            message.setData(bundle);
            try {
                messenger.send(message);
                iBinder.linkToDeath(deathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        TextView textView = findViewById(R.id.textView);
        ImageView imageView = findViewById(R.id.imageView);
        TransitionDrawable drawable = (TransitionDrawable) button.getBackground();
        drawable.startTransition(1000);
        // drawable.reverseTransition(1000);
        ScaleDrawable scaleDrawable = (ScaleDrawable) textView.getBackground();
        scaleDrawable.setLevel(1);
        ClipDrawable clipDrawable = (ClipDrawable) imageView.getBackground();
        clipDrawable.setLevel(9000);

        button.setOnClickListener(this);
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View view) {
//        Intent intent = new Intent(this, TwoActivity.class);
        Intent intent = new Intent(this, AnimationActivity.class);

        startActivity(intent);
    }
}
