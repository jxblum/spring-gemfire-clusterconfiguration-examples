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

package io.pivotal.example.app.bootstrap.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;

import com.gemstone.gemfire.cache.Cache;

/**
 * The ExampleApplicationGemFireConfiguration class...
 *
 * @author John Blum
 * @see org.springframework.context.annotation.Bean
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.context.annotation.ImportResource
 * @see org.springframework.context.support.PropertySourcesPlaceholderConfigurer
 * @since 1.0.0
 */
@Configuration
@ImportResource("io/pivotal/example/app/bootstrap/config/spring-gemfire-context.xml")
@SuppressWarnings("unused")
public class ExampleApplicationGemFireConfiguration {

	@Bean
	PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public Properties gemfireProperties(@Value("${gemfire.log.level:config}") String logLevel,
		@Value("${gemfire.locator.host-port:localhost[10334}") String locatorHostPort)
	{
		Properties gemfireProperties = new Properties();

		gemfireProperties.setProperty("name", "ExampleGemFireSpringJavaApplication");
		gemfireProperties.setProperty("mcast-port", "0");
		gemfireProperties.setProperty("log-level", "config");
		gemfireProperties.setProperty("locators", locatorHostPort);

		return gemfireProperties;
	}

	@Bean
	CacheFactoryBean gemfireCache(@Qualifier("gemfireProperties") Properties gemfireProperties) {
		CacheFactoryBean gemfireCache = new CacheFactoryBean();

		gemfireCache.setClose(true);
		gemfireCache.setProperties(gemfireProperties);
		gemfireCache.setUseBeanFactoryLocator(false);

		return gemfireCache;
	}

	@Bean(name = "JavaBasedRegion")
	ReplicatedRegionFactoryBean<Long, String> javaBasedRegion(Cache gemfireCache) {
		ReplicatedRegionFactoryBean<Long, String> javaBasedRegion = new ReplicatedRegionFactoryBean<>();

		javaBasedRegion.setCache(gemfireCache);
		javaBasedRegion.setPersistent(false);

		return javaBasedRegion;
	}

}
