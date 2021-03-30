package eu.interop.federationgateway.controller.postgres;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import eu.interop.federationgateway.controller.UploadControllerTest;

@ActiveProfiles("psql")
@ContextConfiguration(initializers = {UploadControllerPsqlTest.Initializer.class})

public class UploadControllerPsqlTest extends UploadControllerTest {
  @ClassRule
  public static PostgreSQLContainer mySQLContainer = new PostgreSQLContainer("postgres:10")
    .withDatabaseName("fg")
    .withUsername("sa")
    .withPassword("sa");


     static class Initializer
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
              "spring.datasource.url=" + mySQLContainer.getJdbcUrl(),
              "spring.datasource.username=" + mySQLContainer.getUsername(),
              "spring.datasource.password=" + mySQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
