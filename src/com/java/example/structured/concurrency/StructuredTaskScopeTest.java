package com.java.example.structured.concurrency;

import java.time.Duration;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.concurrent.StructuredTaskScope.Subtask.State;
import java.util.concurrent.TimeoutException;

import com.java.example.structured.concurrency.RuningTask.TaskResponse;

public class StructuredTaskScopeTest {

  public static void main(String[] args) throws InterruptedException, TimeoutException {
    System.out.println("main : started");

    // Step 10
    interruptMain();

    // Step 1:
    executeTaskAndSubTask();

    System.out.println("main : completed");
  }

  private static void interruptMain() {
    Thread mainThread = Thread.currentThread();

    Thread.ofPlatform().start(() -> {
      try {
        Thread.sleep(Duration.ofSeconds(4));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      mainThread.interrupt();
    });

  }

  private static void executeTaskAndSubTask() throws InterruptedException, TimeoutException {
    try (var scope = new StructuredTaskScope<TaskResponse>()) {

      // Start running the task in parallel
      var expTask = new RuningTask("ExpTask", 3, "100$", false);
      var hotTask = new RuningTask("HotTask", 10, "120$", false);
      Subtask<TaskResponse> expSubTask = scope.fork(expTask);
      Subtask<TaskResponse> hotSubTask = scope.fork(hotTask);

      // Wait for task to complete(failure or success)
      scope.join();
      // scope.joinUntil(Instant.now().plusSeconds(2));

      // handle the child task result
      State expState = expSubTask.state();
      if (expState == State.SUCCESS) {
        System.out.println(expSubTask.get());
      } else if (expState == State.FAILED) {
        System.out.println(expSubTask.exception());
      }

      State hotState = hotSubTask.state();
      if (hotState == State.SUCCESS) {
        System.out.println(hotSubTask.get());
      } else if (hotState == State.FAILED) {
        System.out.println(hotSubTask.exception());
      }
    }
  }

}
