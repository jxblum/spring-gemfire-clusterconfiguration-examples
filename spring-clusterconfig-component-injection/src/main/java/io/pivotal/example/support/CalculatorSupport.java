/*
 * Copyright 2014-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
