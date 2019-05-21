// IBookManager.aidl
package com.art.demo.one.aidl;

// Declare any non-default types here with import statements
import com.art.demo.one.aidl.Book;
import com.art.demo.one.aidl.IOnNewBookArrivedListener;
interface IBookManager {
  List<Book> getBookList();
  void addBook(in Book book);
void registerListener(IOnNewBookArrivedListener listener);
void unRegisterListener(IOnNewBookArrivedListener listener);
}
