package com.java.example.virtual.threads;

import java.lang.Thread.Builder.OfVirtual;

/**
 * @author vishal
 */
public class VirtualThreadCreation {

  public static void main(String[] args) throws InterruptedException {

    // Using virtual Thread builder.. we can also specify name of thread by mentioning prefix and
    // thread start number
    OfVirtual ofVirtual = Thread.ofVirtual().name("userthread", 0);

    // Limitation builder object is not threadsafe

    // Creating 2 threads
    Thread vThread1 = ofVirtual.start(VirtualThreadCreation::handleRequest);
    Thread vThread2 = ofVirtual.start(VirtualThreadCreation::handleRequest);

    // Make sure we are terminating the threads
    vThread1.join();
    vThread2.join();

  }

  public static void handleRequest() {
    System.out.println("Start : " + Thread.currentThread());
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("End : " + Thread.currentThread());
  }


}

