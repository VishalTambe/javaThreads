package com.java.example.structured.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.concurrent.TimeoutException;

import com.java.example.structured.concurrency.RuningTask.TaskResponse;

public class StructuredTaskShutdownOnSuccess {

  public static void main(String[] args)
      throws InterruptedException, TimeoutException, ExecutionException, CustomException {
    System.out.println("main : started");

    // Step 1:
    executeTaskAndSubTask();

    System.out.println("main : completed");
  }

  private static void executeTaskAndSubTask()
      throws InterruptedException, TimeoutException, ExecutionException, CustomException {
    try (var scope = new StructuredTaskScope.ShutdownOnSuccess<TaskResponse>()) {

      // Define the tasks
      var weatherTask1 = new RuningTask("weather1", 3, "30", false);
      var weatherTask2 = new RuningTask("weather2", 7, "32", false);

      // Start running the task in parallel
      scope.fork(weatherTask1);
      scope.fork(weatherTask2);

      // Wait for task to complete(failure or success)
      scope.join();
      // get the result from scope
      // TaskResponse resonse = scope.result();
      // If we want to throw custome exception we can use below code
      TaskResponse resonse =
          scope.result(t -> new CustomException("service_error", t.getMessage()));

      // handle the child task result
      System.out.println(resonse);

    }
  }

}
