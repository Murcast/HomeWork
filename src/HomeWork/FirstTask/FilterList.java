package HomeWork.FirstTask;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class FilterList<E> {

    /**
     * Shared empty array instance used for empty instances.
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * Empty predicate array.
     */
    private static final Object[] EMPTY_PREDICATEDATA = {};

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
     * The predicate data array.
     */
    private transient Object[] predicateData;

    /**
     * The size of the ArrayList (the number of elements it contains).
     * @serial
     */
    private int size = 0;

    /**
     * Constructs an empty list with the specified initial capacity and predicate data array.
     *
     * @param initialCapacity the initial capacity of the list
     * @param predicateData predicate data array
     * @throws IllegalArgumentException if the specified initial capacity
     *         is negative or predicateData argument equals null.
     */
    public FilterList(int initialCapacity, E[] predicateData) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        }

        if (predicateData != null) {
            this.predicateData = predicateData;
        } else {
            throw new IllegalArgumentException("Illegal predicateData argument");
        }
    }

    /**
     * Constructs an empty list with the specified initial capacity and empty predicate data array.
     *
     * @param initialCapacity the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity is negative
     */
    public FilterList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        }
        this.predicateData = EMPTY_PREDICATEDATA;
    }

    /**
     * Constructs an empty list with the initial capacity of ten and specified predicate data array.
     *
     * @param predicateData predicate data array
     * @throws IllegalArgumentException if the specified predicateData argument equals null
     */
    public FilterList(E[] predicateData) {
        if (predicateData != null) {
            this.predicateData = predicateData;
        } else {
            throw new IllegalArgumentException("Illegal predicateData argument");
        }
        this.elementData = EMPTY_ELEMENTDATA;
    }

    /**
     * Constructs a list containing the elements of the specified array and specified predicate data array.
     *
     * @param elementData the array whose elements are to be placed into this list
     * @param predicateData predicate data array
     * @throws IllegalArgumentException if the specified elementData or predicateData argument equals null
     */
    public FilterList(E[] elementData, E[] predicateData) {
        if (predicateData != null) {
            this.predicateData = predicateData;
        } else {
            throw new IllegalArgumentException("Illegal predicateData argument");
        }
        if(elementData != null) {
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
        this.elementData = EMPTY_ELEMENTDATA;
        this.predicateData = EMPTY_PREDICATEDATA;
    }

    /**
     * Appends the specified element to the end of this list.
     * @param element element to be appended to this list
     */
    public void add(E element) {
        if(notContains(element)){
            if(size >= elementData.length - 1) {
                resize(elementData.length * 2);
            }
            elementData[size++] = element;
        }
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throw IndexOutOfBoundsException if index is negative or not in range
     */
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
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public void remove(int index) {
        rangeCheck(index);

        if (notContains((E) elementData[index])) {
            for (int i = index; i < size; i++) {
                elementData[i] = elementData[i+1];
            }
            elementData[size] = null;
            size--;
            if (elementData.length > DEFAULT_CAPACITY && size < elementData.length / 4) {
                resize(elementData.length/2);
            }
        }
    }

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.  If the list does not contain the element, it is
     * unchanged.
     *
     * @param o element to be removed from this list, if present
     * @return <tt>true</tt> if this list contained the specified element
     */
    public boolean remove(E o) {
        if(notContains(o)) {
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

    /*
     * Private remove method that skips bounds checking and does not
     * return the value removed.
     */
    private void fastRemove(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                    numMoved);
        elementData[--size] = null;
    }

    /*
     * Private method that resizes elementData array if needed.
     */
    private void resize(int newLength) {
        Object[] newArray = new Object[newLength];
        System.arraycopy(elementData, 0, newArray, 0, size);
        elementData = newArray;
    }

    /*
     * Private method that checks whether an element is not contained in predicate array.
     * If not, returns true.
     */
    private boolean notContains(E element) {
        for(int i = 0; i < predicateData.length; i++) {
            if(predicateData[i].equals(element)) {
                return false;
            }
        }
        return true;
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
        return "Index: "+index+", Size: "+size;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in this list in proper sequence
     */
    public Iterator<E> iterator() {
        return new Iter();
    }

    private class Iter implements Iterator<E> {
        private int currentIndex = 0; // index of next element to return
        private int lastRet = -1; // index of last element returned; -1 if no such

        public Iter() {
        }

        /**
         * Method that checks for the presence of the next element
         *
         * @return true if the next element is present and not contained in predicateData array
         */
        public boolean hasNext() {
            while(currentIndex < size) {
                if(FilterList.this.notContains((E)elementData[currentIndex])) {
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
        public E next() {
            if(hasNext()) {
                currentIndex++;
                return (E) elementData[lastRet = currentIndex - 1];
            } else {
                throw new NoSuchElementException();
            }
        }

        /**
         * Method that removes element at currentIndex position
         *
         * @throws IllegalStateException if method remove was called without calling method next at least one time
         * @throws ConcurrentModificationException
         */
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
                FilterList.this.remove(lastRet);
                currentIndex = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    public static void main(String[] args) {

        Integer[] intArray = {1, 2, 3, 4, 5, 6, 7, 3}; //массив элементов
        Integer[] array = {1, 2, 3}; //массив предикатов
        FilterList<Integer> filterList = new FilterList<>(intArray, array);

        Iterator<Integer> iter = filterList.iterator();

        System.out.println("Вывод элементов массива итератором\n");

        while(iter.hasNext()){
            Integer a = iter.next();
            System.out.print(a + " ");
        }
        System.out.print("\nsize: " + filterList.size());

        System.out.println("\n\nПробуем добавить 5 элементов, 3 из которых находятся в списке предикатов\n");

        filterList.add(1); //не добавится
        filterList.add(2); //не добавится
        filterList.add(3); //не добавится
        filterList.add(4); //добавится
        filterList.add(7); //добавится

        Iterator<Integer> iter2 = filterList.iterator();

        while(iter2.hasNext()){
            Integer a = iter2.next();
            System.out.print(a + " ");
        }
        System.out.print("\nsize: " + filterList.size());

        System.out.println("\n\nПробуем удалить элементы по индексу. Все 3 элемента находятся в списке предикатов\n");

        filterList.remove(0); //не удалится
        filterList.remove(1); //не удалится
        filterList.remove(2); //не удалится

        Iterator<Integer> iter3 = filterList.iterator();

        while(iter3.hasNext()){
            Integer a = iter3.next();
            System.out.print(a + " ");
        }

        System.out.print("\nsize: " + filterList.size());

        System.out.println("\n\nУдаляем 3 элемента с конца. Один из них в списке предикатов\n");

        filterList.remove(9); //удалится
        filterList.remove(8); //удалится
        filterList.remove(7); //не удалится

        Iterator<Integer> iter4 = filterList.iterator();

        while(iter4.hasNext()){
            Integer a = iter4.next();
            System.out.print(a + " ");
        }

        System.out.print("\nsize: " + filterList.size());

        System.out.println("\n\nУдаляем 3 элемента по значению. Один из них в списке предикатов\n");

        filterList.remove(Integer.valueOf(3)); //не удалится
        filterList.remove(Integer.valueOf(4)); //удалится
        filterList.remove(Integer.valueOf(7)); //удалится

        Iterator<Integer> iter5 = filterList.iterator();
        while(iter5.hasNext()){
            Integer a = iter5.next();
            System.out.print(a + " ");
        }

        System.out.print("\nsize: " + filterList.size()+ "\n");

        try {
            FilterList<Integer> filterList2 = new FilterList<>(null);
        } catch (Exception e) {
            System.out.println("\n" + e.getMessage());
        }

        try {
            FilterList<Integer> filterList3 = new FilterList<>(-5);
        } catch (Exception e) {
            System.out.println("\n" + e.getMessage());
        }

        try {
            FilterList<Integer> filterList4 = new FilterList<>(null, array);
        } catch (Exception e) {
            System.out.println("\n" + e.getMessage());
        }
    }
}