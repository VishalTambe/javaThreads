package com.java.example.virtual.threads;

import java.lang.Thread.Builder.OfVirtual;
import java.util.concurrent.ThreadFactory;

public class VirtualThreadCreationBuilderFactory {

  public static void main(String[] args) throws InterruptedException {

    ThreadFactory threadFactory = Thread.ofVirtual().name("userthread", 0).factory();

    // Creating 2 threads
    Thread vThread1 = threadFactory.newThread(VirtualThreadCreation::handleRequest);
    vThread1.start();

    Thread vThread2 = threadFactory.newThread(VirtualThreadCreation::handleRequest);
    vThread2.start();

    // Make sure we are terminating the threads
    vThread1.join();
    vThread2.join();

  }

}
