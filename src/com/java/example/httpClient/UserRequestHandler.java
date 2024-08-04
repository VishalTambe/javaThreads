package com.java.example.httpClient;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author Vishal Tambe
 */
public class UserRequestHandler implements Callable<String> {

  @Override
  public String call() throws Exception {
    // sequentialCall();
    // Thread.sleep(10000);
    // return callImperetiveUsingFuture();
    // Thread.sleep(10000);
    // return virtualThreadConcurrentCall();

    try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()) {
      long startTime = System.currentTimeMillis();
      String outout = CompletableFuture.supplyAsync(this::dbCall, service)
          .thenCombine(CompletableFuture.supplyAsync(this::restCall, service),
              (result1, result2) -> {
                return result1 + result2;
              })
          .thenApply(result -> {
            String res = externalCall();
            return result + res;
          }).join();
      long endTime = System.currentTimeMillis();
      // System.out.println("Sequential call end " + endTime);
      long diff = (endTime - startTime) / 1000;
      System.out.println("CompletableFuture call total Time to execute : " + diff);

      // System.out.println("Output" + result);
      return outout;

    }
  }

  private String virtualThreadConcurrentCall() throws InterruptedException {
    try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()) {
      long startTime = System.currentTimeMillis();
      String result =
          service.invokeAll(Arrays.asList(this::dbCall, this::restCall)).stream().map(f -> {
            try {
              return (String) f.get();
            } catch (InterruptedException | ExecutionException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
              return null;
            }
          }).collect(Collectors.joining(","));
      long endTime = System.currentTimeMillis();
      // System.out.println("End Main " + endTime);
      long diff = (endTime - startTime) / 1000;
      System.out.println("Functional programming execution Time : " + diff);
      return result;
    }
  }

  private String callImperetiveUsingFuture() throws InterruptedException, ExecutionException {
    try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()) {
      long startTime = System.currentTimeMillis();
      Future<String> dbFuture = service.submit(this::dbCall);
      Future<String> restFuture = service.submit(this::restCall);
      String result = dbFuture.get() + restFuture.get();
      long endTime = System.currentTimeMillis();
      // System.out.println("End Main " + endTime);
      long diff = (endTime - startTime) / 1000;
      System.out.println("Total Time to execute : " + diff);
      return result;
    }
  }

  private String sequentialCall() throws Exception {
    long startTime = System.currentTimeMillis();
    String result1 = dbCall(); // 2 Sec
    String result2 = restCall(); // 5 sec
    // String result = String.format("[%s,%s]", result1, result2);
    // System.out.println(result);
    long endTime = System.currentTimeMillis();
    // System.out.println("Sequential call end " + endTime);
    long diff = (endTime - startTime) / 1000;
    System.out.println("Sequential call total Time to execute : " + diff);

    return result1 + result2;
  }

  private String restCall() {
    System.out.println("RestCall: " + Thread.currentThread());
    NetworkClient client = new NetworkClient("restCall");
    try {
      return client.call(5);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  private String dbCall() {
    System.out.println("dbCall: " + Thread.currentThread());
    NetworkClient client = new NetworkClient("dbCall");
    try {
      return client.call(3);
    } catch (Exception e) {
      System.err.println("We are facing error while calling network DB");
      e.printStackTrace();
    }
    return null;
  }

  private String externalCall() {
    System.out.println("externalCall: " + Thread.currentThread());
    NetworkClient client = new NetworkClient("externalCall");
    try {
      return client.call(2);
    } catch (Exception e) {
      System.err.println("We are facing error while calling externalCall");
      e.printStackTrace();
    }
    return null;
  }

}
