package com.java.example.threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class CompletableWithCompose {

  public static Integer doSimpleTask(String task, Integer delay) {
    System.out.println("Start of task : " + task);
    System.out.println("Start of Thread: " + Thread.currentThread().getName());
    try {
      TimeUnit.SECONDS.sleep(delay);
    } catch (InterruptedException e) {
      // TODO: handle exception
      System.out.println("Task interrupted");
    }
    System.out.println("End of Thread: " + Thread.currentThread().getName());
    return delay;
  }

  public static void main(String[] args) {

    Supplier<Integer> task1 = () -> CompletableFuturePlay.doSimpleTask("task1", 5);
    Supplier<Integer> task2 = () -> CompletableFuturePlay.doSimpleTask("task2", 3);
    Supplier<Integer> task3 = () -> CompletableFuturePlay.doSimpleTask("task2", 2);
    Supplier<Integer> task4 = () -> CompletableFuturePlay.doSimpleTask("task2", 1);
    try {

      var future1 = CompletableFuture.supplyAsync(task1);
      var future2 = CompletableFuture.supplyAsync(task2);
      CompletableFuture future =
          future1.thenCombine(future2, (result1, result2) -> fuze(result1, result2))
              .thenApply(s -> s + ":: glue").thenCompose(s -> {
                var future3 = CompletableFuture.supplyAsync(task3);
                var future4 = CompletableFuture.supplyAsync(task4);
                return future3.thenCombine(future4,
                    (result3, result4) -> s + "::" + fuze(result3, result4));
              }).thenAccept(data -> {
                System.out.println(data + ":: handle Accept");
              });
      future.get();
    } catch (Exception e) {
      System.out.println("Error in main");
    } finally {

    }
  }

  private static String fuze(Integer result3, Integer result4) {

    return result3.toString() + ":::" + result4.toString();
  }

  private static CompletableFuture<String> handleTaskResult(Integer result, Integer result2) {
    System.out.println(
        "My Name is vishal and I am responding in between thread executorService execution");

    return CompletableFuture.supplyAsync(() -> {
      return result.toString() + ":: handle compose";
    });
  }

}
