package HomeWork.FirstTask;

/**
 * Represents a function that accepts one argument and produces a result.
 *
 * This is a functional interface whose functional method is apply(Object).
 *
 * @param <T> the type of the input to the function
 * @param <U> the type of the result of the function
 */
@FunctionalInterface
public interface Maper<T, U> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     */
    U apply(T t);
}
