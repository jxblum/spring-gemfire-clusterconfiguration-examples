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

package io.pivotal.example;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;

import com.gemstone.gemfire.cache.Region;

/**
 * The AbstractSpringGemFireApplication class...
 *
 * @author John Blum
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class AbstractSpringGemFireApplication implements CommandLineRunner {

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public void run(final String... strings) throws Exception {
		for (Map.Entry<String, Region> regionBean : applicationContext.getBeansOfType(Region.class).entrySet()) {
			String beanName = regionBean.getKey();
			Region region = regionBean.getValue();

			System.out.printf("Found Region [%1$s] registered with bean id [%2$s]%n", region.getFullPath(), beanName);
		}
	}

}
