package neetcode.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class MinStack {
    Deque<Integer> deque;
    Deque<Integer> minStack;

    public MinStack() {
        deque = new ArrayDeque<>();
        minStack = new ArrayDeque<>();
    }

    public void push(int val) {
        deque.push(val);
        if (minStack.isEmpty()) {
            minStack.push(val);
        } else {
            int minValue = Math.min(minStack.peek(), val);
            minStack.push(minValue);
        }
    }

    public void pop() {
        deque.pop();
        minStack.pop();
    }

    public int top() {
        return deque.peek();
    }

    public int getMin() {
        return minStack.peek();
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.top());
        System.out.println(minStack.getMin());
        minStack.push(2);
        minStack.push(-4);
        minStack.push(3);
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.top());
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.top());
        System.out.println(minStack.getMin());
    }
}
