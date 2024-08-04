package com.java.example.virtual.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author vishal
 */
public class VirtualThreadUsingExecutorService {

  public static void main(String[] args) {

    // Using try catch with resource we make sure that all threads are terminated
    // once get executed

    try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()) {

      service.submit(VirtualThreadCreation::handleRequest);
      service.submit(VirtualThreadCreation::handleRequest);

    } catch (Exception e) {

    }

  }

}
