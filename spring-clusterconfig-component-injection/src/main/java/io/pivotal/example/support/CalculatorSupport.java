package io.pivotal.example.support;

import io.pivotal.example.Calculator;

/**
 * The CalculatorSupport class...
 *
 * @author John Blum
 * @see io.pivotal.example.Calculator
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class CalculatorSupport {

	public static Calculator<Long> addition() {
		return (Long operand) -> operand * 2;
	}

	public static Calculator<Long> factorial() {
		return (Long operand) -> {
			assert operand != null && operand >= 0 : String.format("factorial(%1$s) is not valid", operand);

			if (operand < 3) {
				return (operand < 2l ? 1l : 2l);
			}

			long result = operand;

			while (operand-- > 1) {
				result *= operand;
			}

			return result;
		};
	}

	public static Calculator<Long> multiplication() {
		return (Long operand) -> operand * operand;
	}

}
