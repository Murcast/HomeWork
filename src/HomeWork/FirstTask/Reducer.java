package HomeWork.FirstTask;

public interface Reducer<T> {
    T apply(T t, T y);
}
