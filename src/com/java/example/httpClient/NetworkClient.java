package com.java.example.httpClient;

import java.io.InputStream;
import java.net.URI;

/**
 * @author Vishal Tambe
 */
public class NetworkClient {

  private String callName;


  public NetworkClient(String callName) {
    this.callName = callName;
  }

  public String call(final Integer delay) throws Exception {
    // System.out.println(callName + ": Start call : "+Thread.currentThread());
    URI uri = new URI("https://httpbin.org/delay/" + delay);
    try (InputStream stream = uri.toURL().openStream()) {
      return new String(stream.readAllBytes());
    } catch (Exception e) {
      e.printStackTrace();

      return null;
    } finally {

    }
  }
}
