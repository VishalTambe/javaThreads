package com.java.example.threadLocal;

import com.java.example.threadLocal.user.User;
import com.java.example.threadLocal.user.UserHandler;

public class ThreadLocalSimplePlay {

  public static ThreadLocal<User> user = new ThreadLocal<User>();

  public static void main(String[] args) {

    print("User => " + user.get());

    // Main thread sets the user
    user.set(new User("anonymous"));
    print("User => " + user.get());

    handleUser();
  }

  private static void handleUser() {

    UserHandler handler = new UserHandler();
    handler.handle();
  }

  public static void print(String m) {
    System.out.printf("[%s] %s\n", Thread.currentThread().getName(), m);
  }

}
