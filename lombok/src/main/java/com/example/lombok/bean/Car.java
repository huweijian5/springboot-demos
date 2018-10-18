package com.example.lombok.bean;

import lombok.Builder;

@Builder(toBuilder = true)
public class Car {

    private int color;
    private int weight;
}
