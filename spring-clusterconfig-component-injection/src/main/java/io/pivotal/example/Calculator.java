package io.pivotal.example;

/**
 * The Calculator class...
 *
 * @author John Blum
 * @since 1.0.0
 */
public interface Calculator<T extends Number> {

	T calculate(T operand);

}
