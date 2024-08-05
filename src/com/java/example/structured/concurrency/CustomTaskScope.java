package com.java.example.structured.concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;
import com.java.example.structured.concurrency.RuningTask.TaskResponse;

/**
 * @author Vishal Tambe
 */
public class CustomTaskScope extends StructuredTaskScope<TaskResponse> {


  // create synchornized successSubTaskList
  private final List<Subtask<? extends TaskResponse>> successSubTask =
      Collections.synchronizedList(new ArrayList<>());


  /**
   * Override handleComplete method ,add custom implementation as per our need
   */
  @Override
  protected void handleComplete(Subtask<? extends TaskResponse> subtask) {
    if (subtask.state() == Subtask.State.SUCCESS)
      add(subtask);
  }

  /**
   * Add subtask in successSubTask list
   * @param subtask
   */
  private void add(Subtask<? extends TaskResponse> subtask) {
    int numSuccessful = 0;
    synchronized (successSubTask) {
      successSubTask.add(subtask);
      numSuccessful = successSubTask.size();
    }

    if (numSuccessful == 2) {
      this.shutdown();
    }
  }

  /**
   * 
   */
  public CustomTaskScope join() throws InterruptedException {
    super.join();
    return this;
  }


  /**
   * Return custom response
   * @return
   */
  public TaskResponse resonse() {
    super.ensureOwnerAndJoined();

    if (successSubTask.size() != 2) {
      throw new RuntimeException("Atleast two subtasks must be successful");
    }

    TaskResponse r1 = successSubTask.get(0).get();
    TaskResponse r2 = successSubTask.get(1).get();
    Integer temp1 = Integer.valueOf(r1.response());
    System.err.println(r1);
    System.err.println(r2);
    Integer temp2 = Integer.valueOf(r2.response());

    return new TaskResponse("weather", "" + (temp1 + temp2) / 2, (r1.time() + r2.time()) / 2);
  }
}
