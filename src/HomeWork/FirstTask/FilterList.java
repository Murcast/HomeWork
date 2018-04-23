package HomeWork.FirstTask;

import java.util.*;

public class FilterList<E> implements List<E> {

    /**
     * Shared empty array instance used for empty instances.
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer.
     */
    private transient Object[] elementData;

    /**
     * Predicate limits the addition and removal of elements that
     * contained in predicateData array from elementData array.
     */
    private Predicate predicate;

    /**
     * The size of the ArrayList (the number of elements it contains).
     *
     * @serial
     */
    private int size = 0;

    /**
     * Constructs an empty list with the specified initial capacity and predicate.
     *
     * @param initialCapacity the initial capacity of the list
     * @param predicate predicate
     * @throws IllegalArgumentException if the specified initial capacity
     *                                  is negative or predicate argument equals null.
     */
    public FilterList(int initialCapacity, Predicate predicate) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }

        if (predicate != null) {
            this.predicate = predicate;
        } else {
            throw new IllegalArgumentException("Illegal predicate argument");
        }
    }

    /**
     * Constructs an empty list with the specified initial capacity and empty predicate data array.
     *
     * @param initialCapacity the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity is negative
     */
    public FilterList(int initialCapacity) {
        this(initialCapacity, new Predicate());
    }

    /**
     * Constructs an empty list with the initial capacity of ten and specified predicate.
     *
     * @param predicate predicate
     * @throws IllegalArgumentException if the specified predicate argument equals null
     */
    public FilterList(Predicate predicate) {
        this(0, predicate);
    }

    /**
     * Constructs a list containing the elements of the specified array and specified predicate.
     *
     * @param elementData   the array whose elements are to be placed into this list
     * @param predicate predicate
     * @throws IllegalArgumentException if the specified elementData or predicate argument equals null
     */
    public FilterList(E[] elementData, Predicate predicate) {
        if (predicate != null) {
            this.predicate = predicate;
        } else {
            throw new IllegalArgumentException("Illegal predicate argument");
        }
        if (elementData != null) {
            this.elementData = elementData;
            this.size = elementData.length;
        } else {
            throw new IllegalArgumentException("Illegal elementData argument");
        }
    }

    /**
     * Constructs an empty list with an initial capacity of ten and empty predicate data array.
     */
    public FilterList() {
        this(DEFAULT_CAPACITY, new Predicate());
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param element element to be appended to this list
     */
    @Override
    public boolean add(E element) {
        if (predicate.notContains(element)) {
            if (size >= elementData.length - 1) {
                resize(elementData.length * 2);
            }
            elementData[size++] = element;
            return true;
        }
        return false;
    }

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.  If the list does not contain the element, it is
     * unchanged.
     *
     * @param o element to be removed from this list, if present
     * @return <tt>true</tt> if this list contained the specified element
     */
    @Override
    public boolean remove(Object o) {
        if (predicate.notContains(o)) {
            if (o == null) {
                for (int index = 0; index < size; index++)
                    if (elementData[index] == null) {
                        fastRemove(index);
                        return true;
                    }
            } else {
                for (int index = 0; index < size; index++)
                    if (o.equals(elementData[index])) {
                        fastRemove(index);
                        return true;
                    }
            }
            return false;
        }
        return false;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if index is negative or not in range
     */
    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        rangeCheck(index);

        return (E) elementData[index];
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     *
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if rangeCheck method throws it
     */
    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        rangeCheck(index);
        Object obj = elementData[index];

        if (predicate.notContains(elementData[index])) {
            for (int i = index; i < size; i++) {
                elementData[i] = elementData[i + 1];
            }
            elementData[size] = null;
            size--;
            if (elementData.length > DEFAULT_CAPACITY && size < elementData.length / 4) {
                resize(elementData.length / 2);
            }
            return (E) obj;
        }
        throw new IllegalArgumentException("You cat't remove element at position " + index);
    }

    private void fastRemove(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index + 1, elementData, index,
                    numMoved);
        elementData[--size] = null;
    }

    /*
     * Private remove method that skips bounds checking and does not
     * return the value removed.
     */

    /*
     * Private method that resizes elementData array if needed.
     */
    private void resize(int newLength) {
        Object[] newArray = new Object[newLength];
        System.arraycopy(elementData, 0, newArray, 0, size);
        elementData = newArray;
    }

    /**
     * Checks if the given index is in range.  If not, throws an appropriate
     * runtime exception.  This method does *not* check if the index is
     * negative: It is always used immediately prior to an array access,
     * which throws an ArrayIndexOutOfBoundsException if index is negative.
     */
    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    /**
     * Constructs an IndexOutOfBoundsException detail message.
     * Of the many possible refactorings of the error handling code,
     * this "outlining" performs best with both server and client VMs.
     */
    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    /**
     * Applies the function described in the specified mapper to each element of the list.
     * The type of elements of the returned list may differ from the original.
     *
     * @param mapper functional interface instance
     * @param <T> parameter type of returned list
     * @return new transformed list
     */
    @SuppressWarnings("unchecked")
    public <T> FilterList<T> map(Maper<E, T> mapper) {
        FilterList<T> list = new FilterList<>(this.size(), predicate);
        for (int i = 0; i < this.size(); i++) {
            Object obj = mapper.apply((E) elementData[i]);
            list.add((T) obj);
        }
        return list;
    }

    /**
     * Applies the function described in the specified reducer to the list.
     *
     * @param reducer functional interface instance
     * @return single value as a result of reducer function
     */
    @SuppressWarnings("unchecked")
    public E reduce(Reducer<E> reducer) {
        E obj = null;

        for (int i = 0; i < elementData.length - 1; i++) {
            if (obj == null) {
                obj = reducer.apply((E) elementData[i], (E) elementData[i + 1]);
            } else {
                obj = reducer.apply(obj, (E) elementData[i + 1]);
            }
        }

        if (obj != null) {
            return obj;
        } else {
            throw new NullPointerException("The result of reduction is null!");
        }
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in this list in proper sequence
     */
    @Override
    public Iterator<E> iterator() {
        return new Iter();
    }

    private class Iter implements Iterator<E> {
        private int currentIndex = 0; // index of next element to return
        private int lastRet = -1; // index of last element returned; -1 if no such

        Iter() {
        }

        /**
         * Method that checks for the presence of the next element
         *
         * @return true if the next element is present and not contained in predicate array
         */
        @Override
        public boolean hasNext() {
            while (currentIndex < size) {
                if (predicate.notContains(elementData[currentIndex])) {
                    return true;
                }
                currentIndex++;
            }
            return false;
        }

        /**
         * Method that returns the next element
         *
         * @return the element pointed to by currentIndex
         * @throws NoSuchElementException if method hasNext returned false and method next was called
         */
        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            if (hasNext()) {
                currentIndex++;
                return (E) elementData[lastRet = currentIndex - 1];
            } else {
                throw new NoSuchElementException();
            }
        }

        /**
         * Method that removes element at currentIndex position
         *
         * @throws IllegalStateException           if method remove was called without calling method next at least one time
         * @throws ConcurrentModificationException if method remove was called two consecutive times
         */
        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }

            try {
                FilterList.this.remove(lastRet);
                currentIndex = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
