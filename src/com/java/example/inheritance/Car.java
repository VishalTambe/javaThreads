package com.java.example.inheritance;

public class Car extends Vehicle {

  public static void main(String[] args) {
    Car c = new Car();
    c.setName("XUV");
    Vehicle v = new Vehicle();
    v.setName("XUV");

    System.out.println(c.equals(v));
  }
}
