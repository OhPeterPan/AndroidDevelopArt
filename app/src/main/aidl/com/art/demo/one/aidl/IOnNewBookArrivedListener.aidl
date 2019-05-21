// IOnNewBookArrivedListener.aidl
package com.art.demo.one.aidl;
import com.art.demo.one.aidl.Book;
// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {

void onNewBookArrived(in Book book);

}
