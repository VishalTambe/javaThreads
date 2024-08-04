package com.java.example.httpClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.IntStream;

/**
 * @author Vishal Tambe
 */
public class HttpPlayer {

  private static Integer NUM_OF_USERS = 1500;

  public static void main(String[] args) {
    // long startTime = System.currentTimeMillis();
    ThreadFactory factory = Thread.ofVirtual().name("request-handler-", 0).factory();

    try (ExecutorService service = Executors.newThreadPerTaskExecutor(factory)) {
      IntStream.range(0, NUM_OF_USERS).forEach(j -> {
        service.submit(new UserRequestHandler());
      });
    } catch (Exception e) {
      System.out.println("HttpPlayer Error");
      e.printStackTrace();
    }

  }
}
