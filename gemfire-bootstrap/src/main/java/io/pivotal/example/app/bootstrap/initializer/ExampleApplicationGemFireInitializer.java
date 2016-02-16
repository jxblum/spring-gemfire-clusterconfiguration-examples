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

package io.pivotal.example.app.bootstrap.initializer;

import org.springframework.data.gemfire.support.SpringContextBootstrappingInitializer;

import com.gemstone.gemfire.internal.ClassPathLoader;

import io.pivotal.example.app.bootstrap.config.ExampleApplicationGemFireConfiguration;

/**
 * The ExampleApplicationGemFireInitializer class...
 *
 * @author John Blum
 * @see org.springframework.data.gemfire.support.SpringContextBootstrappingInitializer
 * @see ExampleApplicationGemFireConfiguration
 * @see com.gemstone.gemfire.internal.ClassPathLoader
 * @see <a href="http://docs.spring.io/spring-data-gemfire/docs/current/reference/html/#gemfire-bootstrap">Spring Context Bootstrapping Initializer</a>
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class ExampleApplicationGemFireInitializer extends SpringContextBootstrappingInitializer {

	public ExampleApplicationGemFireInitializer() {
		setBeanClassLoader(ClassPathLoader.getLatestAsClassLoader());
		register(ExampleApplicationGemFireConfiguration.class);
	}

}
