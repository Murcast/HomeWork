package HomeWork.FirstTask;

/**
 * Represents an operation upon two operands of the same type, producing a result
 * of the same type as the operands.
 *
 * This is a functional interface whose functional method is apply(Object, Object).
 * Operands and the result are all of the same type.
 *
 * @param <T> the type of the operands and result of the operator
 */
@FunctionalInterface
public interface Reducer<T> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param y the second function argument
     * @return the function result
     */
    T apply(T t, T y);
}
