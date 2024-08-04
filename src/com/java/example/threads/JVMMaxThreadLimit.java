package com.java.example.threads;

import java.util.ArrayList;

/**
 * @author vishal
 */
public class JVMMaxThreadLimit {

  // Set jvm argumment -Xss136K -Xmx1G before running
  public static void handleRequest() {
    // System.out.println("Start : " + Thread.currentThread());
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    // System.out.println("End : " + Thread.currentThread().getName());
  }

  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    System.out.println("Start Main : " + startTime);
    var threads = new ArrayList<Thread>();
    for (int i = 0; i < 80000; i++) {
      Thread thread = startThread();
      thread.start();
      threads.add(thread);

      /// new Thread(() -> handleRequest()).start();
    }

    // for (Thread thread : threads) {
    // try {
    // thread.join();
    // } catch (InterruptedException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
    long endTime = System.currentTimeMillis();
    System.out.println("End Main :  " + endTime);
    long diff = (endTime - startTime) / 100;
    System.out.println("Total Time toexecute : " + diff);
  }

  private static Thread startThread() {
    return new Thread(() -> handleRequest());
  }

}
