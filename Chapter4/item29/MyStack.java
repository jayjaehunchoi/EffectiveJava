package item29;

import java.util.Arrays;
import java.util.EmptyStackException;

public class MyStack<E> {
    private E[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    @SuppressWarnings("unchecked")
    public MyStack(){
        elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(E e){
        ensureCapacity();
        elements[size++] = e;
    }

    public E pop(){
        if(size == 0){
            throw new EmptyStackException();
        }
        E element = elements[--size];
        elements[size] = null; // 참조해제
        return element;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    private void ensureCapacity(){
        if(elements.length == size){
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }

    public static void main(String[] args) {
        MyStack<Integer> stack = new MyStack();
        stack.push(1);
        stack.push(2);

        System.out.println((int)stack.pop() + (int)stack.pop());
    }
}
