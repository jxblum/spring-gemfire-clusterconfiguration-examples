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
