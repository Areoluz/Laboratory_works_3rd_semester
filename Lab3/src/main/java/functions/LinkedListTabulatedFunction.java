package functions;
import exceptions.DifferentLengthOfArraysException;
import exceptions.ArrayIsNotSortedException;
import exceptions.InterpolationException;

import java.io.Serial;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction implements TabulatedFunction, Serializable {

    @Serial
    private static final long serialVersionUID = 2649483009277234910L;

    static class Node {

        public Node next;
        public Node prev;
        public double x;
        public double y;

        public Node(final double x, final double y) {
            this.x = x;
            this.y = y;
        }

        public Node(double x, double y, Node next, Node prev) {
            this(x, y);
            this.next = next;
            this.prev = prev;
        }
    }

    private Node head;  // Голова списка

    private void addNode(double x, double y) {
        Node newNode = new Node(x, y);
        if (head == null) {
            head = newNode;
            head.next = head;
            head.prev = head;
        } else {
            Node last = head.prev;
            last.next = newNode;
            newNode.prev = last;
            newNode.next = head;
            head.prev = newNode;
        }
        count++;
    }

    protected Node getNode(int index) {
        if (index < 0 || index >= count) {
           throw new IllegalArgumentException("Index can't be less than zero and more than length");
        }

        Node current;
        if (index < count / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = head.prev;
            for (int i = count - 1; i > index; i--) {
                current = current.prev;
            }
        }

        return current;
    }


    protected Node floorNodeOfX(double x) {
        if (x < head.x)
            throw new IllegalArgumentException("x less than left bound of the list");
        Node cur = head;
        int i = 0;
        do {
            if (cur.x >= x)
                return i == 0 ? getNode(0) : getNode(i - 1);
            ++i;
            cur = cur.next;
        } while (cur != head);
        return getNode(count);
    }


    // Конструктор с массивами xValues и yValues
    public LinkedListTabulatedFunction(double[] xValues, double[] yValues) {
        checkLengthIsTheSame(xValues, yValues);
        if (xValues.length < 2) throw new IllegalArgumentException("The count of the X points must be 2 at least");
        checkSorted(xValues);
        for (int i = 0; i < xValues.length; ++i) {
            addNode(xValues[i], yValues[i]);
        }
    }

    public LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (count < 2) {
            throw new IllegalArgumentException("Count must be at least 2.");
        }

        if (xFrom > xTo) {
            double temp = xFrom;
            xFrom = xTo;
            xTo = temp;
        }
        if (xFrom == xTo) {
            for (int i = 0; i < count; i++) {
                addNode(xFrom, source.apply(xFrom));
            }
        }

        double step = (xTo - xFrom) / (count - 1);

        for (int i = 0; i < count; i++) {
            double x = xFrom + i * step;
            addNode(x, source.apply(x));
        }
        if (head != null)
            addNode(xTo, source.apply(xTo));
    }


    @Override
    public int getCount() {
        return count;
    }

    @Override
    public double getX(int index) {
        return getNode(index).x;
    }

    @Override
    public double getY(int index) {
        return getNode(index).y;
    }

    @Override
    public void setY(int index, double value) {
        getNode(index).y = value;
    }

    @Override
    public double leftBound() {
        return head.x;
    }

    @Override
    public double rightBound() {
        return head.prev.x;
    }

    @Override
    public int indexOfX(double x) {
        Node current = head;
        for (int i = 0; i < count; i++) {
            if (current.x == x) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        Node current = head;
        for (int i = 0; i < count; i++) {
            if (current.y == y) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    @Override
    public int floorIndexOfX(double x) {
        if (x < head.x)
            throw new IllegalArgumentException("x less than left bound of the list");
        Node cur = head;
        int i = 0;
        do {
            if (cur.x >= x)
                return i == 0 ? 0 : i - 1;
            ++i;
            cur = cur.next;
        } while (cur != head);
        return count;
    }

    @Override
    public double extrapolateLeft(double x) {
        return interpolate(x, head.x, head.next.x, head.y, head.next.y);
    }

    @Override
    public double extrapolateRight(double x) {
        return interpolate(x, head.prev.prev.x, head.prev.x, head.prev.prev.y, head.prev.y);
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        Node floorNode = getNode(floorIndex);
        if (x < floorNode.x || x > floorNode.next.x) {
            throw new InterpolationException("Failed interpolation with 2 parameters");
        }
        return interpolate(x, floorNode.x, floorNode.next.x, floorNode.y, floorNode.next.y);
    }

    @Override
    public Iterator<Point> iterator() {
        return new Iterator<Point>() {
            private Node node = head;

            @Override
            public boolean hasNext() {
                return !(node == null);
            }

            @Override
            public Point next() {
                if (hasNext()) {
                    Point p = new Point(node.x, node.y);
                    if (node.next == head)
                        node = null;
                    else
                        node = node.next;
                    return p;
                }
                throw new NoSuchElementException();
            }
        };
    }

    @Override
    public double apply(double x) {
        if (getX(0) > x)
            return extrapolateLeft(x);
        else if (getX(count - 1) < x)
            return extrapolateRight(x);
        else if (indexOfX(x) != -1)
            return getY((indexOfX(x)));
        Node floorNode = floorNodeOfX(x);
        return interpolate(x, floorNode.x, floorNode.next.x, floorNode.y, floorNode.next.y);
    }

}
