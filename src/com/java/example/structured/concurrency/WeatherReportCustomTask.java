package com.java.example.structured.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.concurrent.TimeoutException;

import com.java.example.structured.concurrency.RuningTask.TaskResponse;

public class WeatherReportCustomTask {

  public static void main(String[] args)
      throws InterruptedException, TimeoutException, ExecutionException, CustomException {
    System.out.println("main : started");

    // Step 1:
    executeTaskAndSubTask();

    System.out.println("main : completed");
  }

  private static void executeTaskAndSubTask()
      throws InterruptedException, TimeoutException, ExecutionException, CustomException {
    try (var scope = new CustomTaskScope()) {

      // Define the tasks
      var weatherTask1 = new RuningTask("weather1", 3, "28", true);
      var weatherTask2 = new RuningTask("weather2", 2, "30", true);
      var weatherTask3 = new RuningTask("weather3", 4, "33", false);
      var weatherTask4 = new RuningTask("weather4", 7, "31", false);
      var weatherTask5 = new RuningTask("weather5", 3, "32", false);
      var weatherTask6 = new RuningTask("weather6", 7, "34", false);

      // Start running the task in parallel
      scope.fork(weatherTask1);
      scope.fork(weatherTask2);
      scope.fork(weatherTask3);
      scope.fork(weatherTask4);
      scope.fork(weatherTask5);
      scope.fork(weatherTask6);


      // Wait for task to complete(failure or success)
      scope.join();
      // get the result from scope
      // TaskResponse resonse = scope.result();
      // If we want to throw custome exception we can use below code
      TaskResponse resonse = scope.resonse();

      // handle the child task result
      System.out.println(resonse);

    }
  }

}
