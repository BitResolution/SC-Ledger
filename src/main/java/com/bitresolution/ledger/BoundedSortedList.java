package com.bitresolution.ledger;

import java.util.*;

class BoundedSortedList<E> extends AbstractList<E> {

    private final List<E> backingList;
    private final Comparator<E> comparator;
    private final int maxSize;

    BoundedSortedList(int maxSize, Comparator<E> comparator) {
        this.comparator = comparator;
        this.maxSize = maxSize;
        backingList = new ArrayList<E>();
    }

    @Override
    public E get(int i) {
        return backingList.get(i);
    }

    public boolean add(E e) {
        boolean added = backingList.add(e);
        Collections.sort(backingList, comparator);
        if(backingList.size() > maxSize) {
            backingList.remove(maxSize);
        }
        return added;
    }

    @Override
    public int size() {
        return backingList.size();
    }

    @Override
    public int hashCode() {
        return backingList.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return backingList.equals(obj);
    }
}
