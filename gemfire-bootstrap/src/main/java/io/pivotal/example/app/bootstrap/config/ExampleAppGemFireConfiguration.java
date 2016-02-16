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
 * The ExampleAppGemFireConfiguration class...
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
public class ExampleAppGemFireConfiguration {

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
