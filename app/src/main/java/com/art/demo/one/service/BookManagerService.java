package com.art.demo.one.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.art.demo.one.aidl.Book;
import com.art.demo.one.aidl.IBookManager;
import com.art.demo.one.aidl.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookManagerService extends Service {
    private CopyOnWriteArrayList bookList = new CopyOnWriteArrayList<Book>();
    private CopyOnWriteArrayList<IOnNewBookArrivedListener> listenerList = new CopyOnWriteArrayList<IOnNewBookArrivedListener>();
    private RemoteCallbackList<IOnNewBookArrivedListener> remoteCallbackList = new RemoteCallbackList<>();//已经实现线程同步

    private class BookBinder extends IBookManager.Stub {

        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            bookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            remoteCallbackList.register(listener);
        }

        @Override
        public void unRegisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            remoteCallbackList.unregister(listener);
        }
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        bookList.add(book);
       /* for (IOnNewBookArrivedListener listener : listenerList) {
            listener.onNewBookArrived(book);
        }*/
        try {
            int m = remoteCallbackList.beginBroadcast();
            for (int i = 0; i < m; i++) {
                IOnNewBookArrivedListener listener = remoteCallbackList.getBroadcastItem(i);
                listener.onNewBookArrived(book);//此方法运行在客户端的binder线程池中
            }
            remoteCallbackList.finishBroadcast();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bookList.add(new Book("大话西游", "六学家"));
        bookList.add(new Book("水浒传", "施耐庵"));
        new Thread(new WorkThread()).start();
    }

    private class WorkThread implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Book book = new Book("hahah", "测试吧");
            try {
                onNewBookArrived(book);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        int i = checkCallingOrSelfPermission("com.wak.aidl");
        if (i == PackageManager.PERMISSION_DENIED)
            return null;
        return new BookBinder().asBinder();
    }
}
