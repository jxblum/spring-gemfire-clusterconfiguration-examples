package io.pivotal.example.java;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.config.AutoRegionLookupBeanPostProcessor;
import org.springframework.data.gemfire.server.CacheServerFactoryBean;

import com.gemstone.gemfire.cache.Cache;

import io.pivotal.example.AbstractSpringGemFireApplication;

/**
 * The SpringGemFireJavaBasedApplication class...
 *
 * @author John Blum
 * @since 1.0.0
 */
@ConfigurationProperties
@SpringBootApplication
@SuppressWarnings("unused")
public class SpringGemFireJavaBasedApplication extends AbstractSpringGemFireApplication {

	public static void main(final String[] args) {
		SpringApplication.run(SpringGemFireJavaBasedApplication.class, args);
	}

	@Bean
	Properties gemfireProperties(@Value("${gemfire.log.level:config}") String logLevel,
		@Value("${gemfire.locator.host-port:localhost[10334]}") String locatorHostPort)
	{
		Properties gemfireProperties = new Properties();

		gemfireProperties.setProperty("name", SpringGemFireJavaBasedApplication.class.getSimpleName());
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
		gemfireCache.setUseBeanFactoryLocator(false);
		gemfireCache.setUseClusterConfiguration(true);

		return gemfireCache;
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
	PartitionedRegionFactoryBean<Long, String> springRegion(Cache gemfireCache) {
		PartitionedRegionFactoryBean<Long, String> springRegion = new PartitionedRegionFactoryBean<>();

		springRegion.setCache(gemfireCache);
		springRegion.setName("SpringRegion");
		springRegion.setPersistent(false);

		return springRegion;
	}

	@Bean
	AutoRegionLookupBeanPostProcessor autoRegionLookupBeanPostProcessor() {
		return new AutoRegionLookupBeanPostProcessor();
	}

}
