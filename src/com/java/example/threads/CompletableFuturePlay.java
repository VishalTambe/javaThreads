package com.java.example.threads;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class CompletableFuturePlay {

  private String id;

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
    Supplier<Integer> task2 = () -> CompletableFuturePlay.doSimpleTask("task2", 5);
    try {
      CompletableFuture future = CompletableFuture.supplyAsync(task1)
          .thenCombine(CompletableFuture.supplyAsync(task2),
              (result1, result2) -> result1 + result2)
          .thenApply(data -> data + ":: Handle Apply").thenAccept(data -> {
            System.out.println(data + ":: Handle accept");
          });
      future.get();
    } catch (Exception e) {
      System.out.println("Error in main");
    } finally {

    }
  }

  private static void getName() {
    System.out.println(
        "My Name is vishal and I am responding in between thread executorService execution");

  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    return true;
  }



}
