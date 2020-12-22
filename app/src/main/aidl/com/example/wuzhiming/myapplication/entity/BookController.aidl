// BookController.aidl
package com.example.wuzhiming.myapplication.entity;
import com.example.wuzhiming.myapplication.entity.Book;

interface BookController {
    List<Book> getBookList();
    void addBookInOut(inout Book book);

    void addBookIn(in Book book);

        void addBookOut(out Book book);
}
