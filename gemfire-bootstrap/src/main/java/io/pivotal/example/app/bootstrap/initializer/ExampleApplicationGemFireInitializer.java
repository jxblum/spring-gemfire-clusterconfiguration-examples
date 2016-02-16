package io.pivotal.example.app.bootstrap.initializer;

import org.springframework.data.gemfire.support.SpringContextBootstrappingInitializer;

import com.gemstone.gemfire.internal.ClassPathLoader;

import io.pivotal.example.app.bootstrap.config.ExampleAppGemFireConfiguration;

/**
 * The ExampleApplicationGemFireInitializer class...
 *
 * @author John Blum
 * @see org.springframework.data.gemfire.support.SpringContextBootstrappingInitializer
 * @see io.pivotal.example.app.bootstrap.config.ExampleAppGemFireConfiguration
 * @see com.gemstone.gemfire.internal.ClassPathLoader
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class ExampleApplicationGemFireInitializer extends SpringContextBootstrappingInitializer {

	public ExampleApplicationGemFireInitializer() {
		setBeanClassLoader(ClassPathLoader.getLatestAsClassLoader());
		register(ExampleAppGemFireConfiguration.class);
	}

}
