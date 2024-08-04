package com.java.example.structured.concurrency;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import com.java.example.structured.concurrency.RuningTask.TaskResponse;

public class RuningTask implements Callable<TaskResponse> {

  private final String name;
  private final int time;
  private final String output;
  private final boolean fail;

  public RuningTask(String name, int time, String output, boolean fail) {
    super();
    this.name = name;
    this.time = time;
    this.output = output;
    this.fail = fail;
  }

  record TaskResponse(String name, String response, long time) {

  }

  @Override
  public TaskResponse call() throws Exception {
    long startTime = System.currentTimeMillis();
    print("Started");
    int numSec = 0;
    while (numSec++ < time) {

      if (Thread.interrupted()) {
        throwInterruptedException();
      }

      print("Working.... " + numSec);
      try {
        Thread.sleep(Duration.ofSeconds(1));
      } catch (InterruptedException e) {
        throwInterruptedException();
      }
    }
    if (fail) {
      throwExceptionOnFailure();
    }
    long endTime = System.currentTimeMillis();
    print("Completed");
    TaskResponse response = new TaskResponse(this.name, this.output, (endTime - startTime) / 1000);
    return response;
  }

  private void throwExceptionOnFailure() {
    print("Failed");
    throw new RuntimeException(name + "failed");

  }

  private void throwInterruptedException() throws InterruptedException {
    print("Interrupted");
    throw new InterruptedException(name + "Interrupted");

  }

  private void print(String msg) {
    System.out.printf("> %s : %s ", name, msg);
    System.out.println("");
  }

  public static void main(String[] args) throws InterruptedException {
    System.out.println("> main : started");
    RuningTask program = new RuningTask("MyRunningTak", 10, "Test Running Task", true);
    try (ExecutorService service = Executors.newFixedThreadPool(2)) {
      Future<TaskResponse> future = service.submit(program);
      Thread.sleep(Duration.ofSeconds(8));
      future.cancel(true);
    }
    System.out.println("");
    System.out.println("> main : Completed");
  }

  private void handleBusinessLogic() {
    ExecutorService pool = ForkJoinPool.commonPool();
    Future<String> future = pool.submit(() -> {
      System.out.println(">> started worker thread");
      return "done";
    });
  }

}
