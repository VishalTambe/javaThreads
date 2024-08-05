package com.java.example.threadLocal;

import com.java.example.threadLocal.user.User;

public class ThreadLocalPlay {

  public static final ThreadLocal<User> user = new ThreadLocal<>();

  public static void main(String[] args) throws InterruptedException {

    print("user => " + user.get());
    
    user.set(new User("main"));

    print("modified user => " + user.get());
    
    Thread vThread = Thread.ofVirtual().start(() -> {
      
      Thread.currentThread().setName("vishal-thread");
      print("V Thread user => "+user.get());
      user.set(new User("Vishal"));
      print("Modified V Thread user => "+user.get());
    });
    
    vThread.join();
    print("user => "+user.get());
    
  }

  private static void print(String m) {
    System.out.printf("[%s] %s\n", Thread.currentThread().getName(), m);
  }

}
