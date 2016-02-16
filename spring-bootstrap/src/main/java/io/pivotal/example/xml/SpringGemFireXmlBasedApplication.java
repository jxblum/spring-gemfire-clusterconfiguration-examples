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
