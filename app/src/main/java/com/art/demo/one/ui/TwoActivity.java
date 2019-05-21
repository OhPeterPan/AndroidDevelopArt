package com.art.demo.one.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.art.demo.one.R;
import com.art.demo.one.aidl.Book;
import com.art.demo.one.aidl.IBookManager;
import com.art.demo.one.aidl.IOnNewBookArrivedListener;
import com.art.demo.one.service.BookManagerService;

import java.util.List;

public class TwoActivity extends AppCompatActivity implements View.OnClickListener {
    IBookManager manager;
    private IOnNewBookArrivedListener.Stub mOnNewBookListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {//运行在线程池中
          //  Log.i("wak", book.name + ":::" + book.author + ":::" + Thread.currentThread());
        }
    };
    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {//在客户端的线程池中被回调
            manager.asBinder().unlinkToDeath(deathRecipient, 0);
            manager = null;
            Intent intent = new Intent(TwoActivity.this, BookManagerService.class);
            startService(intent);
            bindService(intent, conn, Context.BIND_AUTO_CREATE);
        }
    };
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {//UI线程
            manager = IBookManager.Stub.asInterface(iBinder);
            if (manager == null) return;
            try {
                manager.asBinder().linkToDeath(deathRecipient, 0);
                List<Book> bookList = manager.getBookList();
                for (Book book :
                        bookList) {
                    Log.i("wak", book.name + "::::" + book.author);
                }

                manager.registerListener(mOnNewBookListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {//UI线程

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (manager != null && manager.asBinder().isBinderAlive()) {
                manager.unRegisterListener(mOnNewBookListener);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        Intent intent = new Intent(this, BookManagerService.class);
        startService(intent);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, ThreeActivity.class);
        startActivity(intent);
   /*     try {
            manager.addBook(new Book("三国演义", "罗贯中"));

        } catch (RemoteException e) {
            e.printStackTrace();
        }*/
    }

}
