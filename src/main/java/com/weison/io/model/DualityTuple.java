package com.weison.io.model;

public class DualityTuple<A, B> {

    public final A first;
    public final B second;

    public DualityTuple(A a, B b) {
        first = a;
        second = b;
    }

    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}

