package com.java.example.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class FuturesPlay {

	public static Integer doSimpleTask() {
		System.out.println("Start of Thread: " + Thread.currentThread().getName());
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO: handle exception
			System.out.println("Task interrupted");
		}
		System.out.println("End of Thread: " + Thread.currentThread().getName());
		return 1;
	}

	public static void main(String[] args) {
		
		CompletableFuturePlay c = new CompletableFuturePlay();
		CompletableWithCompose c1 = new CompletableWithCompose();
		System.out.println(c.equals(c1));
		
		ExecutorService service = null;
		try {
			service = Executors.newSingleThreadExecutor();
			Future<?> future = service.submit(FuturesPlay::doSimpleTask);
			getName();
			System.out.println(future.get());
		} catch (Exception e) {
			System.out.println("Error in main");
		} finally {
			if (service != null)
				service.shutdown();

		}
	}

	private static void getName() {
		System.out.println("My Name is vishal and I am responding in between thread executorService execution");

	}
}
