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

import java.util.Properties;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.LazyWiringDeclarableSupport;
import org.springframework.data.gemfire.LookupRegionFactoryBean;
import org.springframework.data.gemfire.server.CacheServerFactoryBean;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheLoader;
import com.gemstone.gemfire.cache.CacheLoaderException;
import com.gemstone.gemfire.cache.LoaderHelper;
import com.gemstone.gemfire.cache.Region;

import io.pivotal.example.support.CalculatorSupport;

/**
 * The SpringGemFireApplication class...
 *
 * @author John Blum
 * @since 1.0.0
 */
@SpringBootApplication
@SuppressWarnings("all")
public class SpringGemFireApplication implements CommandLineRunner {

	@Resource(name = "calculatorRegion")
	private Region<Long, Long> calculator;

	public static void main(final String[] args) {
		SpringApplication.run(SpringGemFireApplication.class, args);
	}

	@Override
	public void run(final String... args) throws Exception {
		for (String arg : args) {
			System.out.printf("factorial(%1$s) == %2$d%n", arg, calculator.get(Long.valueOf(arg)));
		}
	}

	@Bean
	Properties gemfireProperties(@Value("${gemfire.log.level:config}") String logLevel,
		@Value("${gemfire.locator.host-port:localhost[10334]}") String locatorHostPort)
	{
		Properties gemfireProperties = new Properties();

		gemfireProperties.setProperty("name", SpringGemFireApplication.class.getSimpleName());
		gemfireProperties.setProperty("mcast-port", "0");
		gemfireProperties.setProperty("log-level", logLevel);
		gemfireProperties.setProperty("locators", locatorHostPort);

		return gemfireProperties;
	}

	@Bean
	CacheFactoryBean gemfireCache(@Qualifier("gemfireProperties") Properties gemfireProperties) {
		CacheFactoryBean gemfireCache = new CacheFactoryBean();

		gemfireCache.setClose(true);
		gemfireCache.setProperties(gemfireProperties);
		gemfireCache.setUseBeanFactoryLocator(true);
		gemfireCache.setUseClusterConfiguration(true);

		return gemfireCache;
	}

	@Bean
	LookupRegionFactoryBean<Long, Long> calculatorRegion(Cache gemfireCache) {
		LookupRegionFactoryBean<Long, Long> calculatorRegion = new LookupRegionFactoryBean<>();

		calculatorRegion.setCache(gemfireCache);
		calculatorRegion.setName("CalculatorRegion");

		return calculatorRegion;
	}

	@Bean
	CacheServerFactoryBean gemfireCacheServer(Cache gemfireCache,
		@Value("${gemfire.cache.server.bind-address:localhost}") String bindAddress,
		@Value("${gemfire.cache.server.hostname-for-clients:localhost}") String hostnameForClients,
		@Value("${gemfire.cache.server.port:40404}") int port)
	{
		CacheServerFactoryBean gemfireCacheServer = new CacheServerFactoryBean();

		gemfireCacheServer.setAutoStartup(true);
		gemfireCacheServer.setCache(gemfireCache);
		gemfireCacheServer.setBindAddress(bindAddress);
		gemfireCacheServer.setHostNameForClients(hostnameForClients);
		gemfireCacheServer.setPort(port);
		gemfireCacheServer.setMaxConnections(10);

		return gemfireCacheServer;
	}

	@Bean
	Calculator<Long> additionCalculator() {
		return CalculatorSupport.addition();
	}

	@Bean
	Calculator<Long> factorialCalculator() {
		return CalculatorSupport.factorial();
	}

	@Bean
	Calculator<Long> multiplicationCalculator() {
		return CalculatorSupport.multiplication();
	}

	public static final class CalculatorCacheLoader extends LazyWiringDeclarableSupport implements CacheLoader<Long, Long> {

		@Autowired
		@Qualifier("factorialCalculator")
		private Calculator<Long> calculator;

		public Long load(final LoaderHelper<Long, Long> loaderHelper) throws CacheLoaderException {
			Long key = loaderHelper.getKey();
			return calculator.calculate(key);
		}

		public void close() {
		}
	}

}
