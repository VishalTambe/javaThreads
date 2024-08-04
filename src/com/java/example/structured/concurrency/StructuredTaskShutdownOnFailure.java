package com.java.example.structured.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.concurrent.TimeoutException;

import com.java.example.structured.concurrency.RuningTask.TaskResponse;

public class StructuredTaskShutdownOnFailure {

  public static void main(String[] args)
      throws InterruptedException, TimeoutException, ExecutionException, CustomException {
    System.out.println("main : started");

    // Step 1:
    executeTaskAndSubTask();

    System.out.println("main : completed");
  }

  private static void executeTaskAndSubTask()
      throws InterruptedException, TimeoutException, ExecutionException, CustomException {
    try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

      // Start running the task in parallel
      var dataTask = new RuningTask("dataTask", 3, "row", false);
      var restTask = new RuningTask("restTask", 10, "json", false);
      Subtask<TaskResponse> dataSubTask = scope.fork(dataTask);
      Subtask<TaskResponse> restSubTask = scope.fork(restTask);

      // Wait for task to complete(failure or success)
      scope.join();
      // This will throw execution exception.
      /* scope.throwIfFailed(); */
      // If we want to throw custome exception we can use below code
      scope.throwIfFailed(t -> new CustomException("service_error", t.getMessage()));

      // handle the child task result
      System.out.println(dataSubTask.get());
      System.out.println(restSubTask.get());
    }
  }

}
