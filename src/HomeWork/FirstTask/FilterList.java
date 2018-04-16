package HomeWork.FirstTask;
import java.util.Iterator;



public class FilterList<E> {
    private static final Object[] EMPTY_ELEMENTDATA = {};
    private static final Object[] EMPTY_PREDICATEDATA = {};
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elementData;
    private Object[] predicateData;
    private int size = 0;

    public FilterList(int initialCapacity, E[] predicateData) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        }

        if (predicateData.length > 0) {
            this.predicateData = predicateData;
        } else if (predicateData.length == 0) {
            this.predicateData = EMPTY_PREDICATEDATA;
        } else {
            throw new IllegalArgumentException("Illegal argument in predicateData");
        }
    }

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

    public FilterList(Object[] predicateData) {
        if (predicateData == null) {
            throw new IllegalArgumentException("PredicateData argument can't be null");
        } else if (predicateData.length > 0) {
            this.predicateData = predicateData;
        } else if (predicateData.length == 0) {
            this.predicateData = EMPTY_PREDICATEDATA;
        } else {
            throw new IllegalArgumentException("Illegal argument in predicate array");
        }
        this.elementData = EMPTY_ELEMENTDATA;
    }

    public FilterList(E[] elementData, Object[] predicateData) {
        if (predicateData == null) {
            throw new IllegalArgumentException("PredicateData argument can't be null");
        } else if (predicateData.length > 0) {
            this.predicateData = predicateData;
        } else if (predicateData.length == 0) {
            this.predicateData = EMPTY_PREDICATEDATA;
        } else {
            throw new IllegalArgumentException("Illegal argument in predicate array");
        }
        if(elementData == null) {
            throw new IllegalArgumentException("ElementData argument can't be null");
        } else {
            this.elementData = elementData;
            this.size = elementData.length;
        }
    }

    public FilterList() {
        this.elementData = EMPTY_ELEMENTDATA;
        this.predicateData = EMPTY_PREDICATEDATA;
    }

    public void add(E element) {
        if(notContains(element)){
            if(size >= elementData.length - 1) {
                resize(elementData.length * 2);
            }
            elementData[size++] = element;
        }
    }

    public E get(int index) {
        return (E) elementData[index];
    }

    public void remove(int index) {
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
    public boolean remove(Object o) {
        if(notContains((E) o)) {
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

    private void fastRemove(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                    numMoved);
        elementData[--size] = null;
    }
    */
    private void resize(int newLength) {
        Object[] newArray = new Object[newLength];
        System.arraycopy(elementData, 0, newArray, 0, size);
        elementData = newArray;
    }

    private boolean notContains(E element) {
        for(int i = 0; i < predicateData.length; i++) {
            if(predicateData[i].equals(element)) {
                return false;
            }
        }
        return true;
    }

    public int size() {
        return size;
    }

    public Iterator<E> iterator() {
        return new Iter();
    }

    private class Iter implements Iterator<E> {
        private int currentIndex = 0;

        public boolean hasNext() {
            while(currentIndex < size) {
                if(notContains((E) elementData[currentIndex])) {
                    return true;
                }
                currentIndex++;
            }
            return false;
        }

        public E next() {
            if(hasNext()) {
                return (E) elementData[currentIndex++];
            } else {
                throw new ArrayIndexOutOfBoundsException();
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        //FilterList<Integer> filterList = new FilterList<>(5, null);
        System.out.println("----------------------------------------------");
        Integer[] intArray = {1, 2, 3, 4, 5, 6, 7, 3};
        Integer[] array = {1, 2, 3};
        FilterList<Integer> filterList2 = new FilterList<Integer>(intArray, array);
        filterList2.add(1);
        filterList2.add(4);
        filterList2.add(7);
        filterList2.add(19);
        filterList2.add(3);
        filterList2.add(4);
        filterList2.add(27);
        filterList2.add(2);
        filterList2.add(4);
        filterList2.add(79);

        Iterator<Integer> iter = filterList2.iterator();
        while(iter.hasNext()){
            Integer a = iter.next();
            System.out.print(a + " ");
        }

        System.out.println();
        System.out.println("----------------------------------------------");

        filterList2.remove(filterList2.size());
        filterList2.remove(0);
        filterList2.remove(1);
        filterList2.remove(2);
        filterList2.remove(3);
        filterList2.remove(4);

        Iterator<Integer> iter2 = filterList2.iterator();
        while(iter2.hasNext()){
            Integer a = iter2.next();
            System.out.print(a + " ");
        }

        System.out.println();
        System.out.println("----------------------------------------------");

        System.out.println(filterList2.size());
    }
}