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

package io.pivotal.example.xml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import io.pivotal.example.AbstractSpringGemFireApplication;

/**
 * The SpringGemFireXmlBasedApplication class...
 *
 * @author John Blum
 * @since 1.0.0
 */
@SuppressWarnings("all")
@SpringBootApplication
@ImportResource("spring-gemfire-no-cluster-config-context.xml")
//@ImportResource("spring-gemfire-using-cluster-config-context.xml")
//@ImportResource("spring-gemfire-with-cluster-config-conflict-context.xml")
//@ImportResource("spring-gemfire-ignore-cluster-config-conflict-context.xml")
//@ImportResource("spring-gemfire-cluster-config-lookup-context.xml")
//@ImportResource("spring-gemfire-cluster-config-auto-lookup-context.xml")
public class SpringGemFireXmlBasedApplication extends AbstractSpringGemFireApplication {

	public static void main(final String[] args) {
		SpringApplication.run(SpringGemFireXmlBasedApplication.class, args);
	}

}
