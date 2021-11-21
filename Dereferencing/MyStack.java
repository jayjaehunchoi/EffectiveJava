package Dereferencing;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Stack;

public class MyStack {

    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public MyStack(){
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e){
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop(){
        if(size == 0){
            throw new EmptyStackException();
        }
        Object element = elements[--size];
        elements[size] = null;
        return element;
    }

    private void ensureCapacity(){
        if(elements.length == size){
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}

