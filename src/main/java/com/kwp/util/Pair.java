package com.kwp.util;

public class Pair<K, E> {
    private final K fst;
    private final E snd;

    public Pair(K fst, E snd) {
        this.fst = fst;
        this.snd = snd;
    }

    public K fst() {
        return fst;
    }

    public E snd() {
        return snd;
    }
}
