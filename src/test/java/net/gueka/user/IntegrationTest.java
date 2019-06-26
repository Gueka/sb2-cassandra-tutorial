package net.gueka.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.InetAddress;

import com.datastax.driver.core.Session;
import com.github.nosan.boot.test.autoconfigure.cassandra.DataCassandraTest;
import com.github.nosan.embedded.cassandra.Settings;
import com.github.nosan.embedded.cassandra.test.TestCassandra;
import com.github.nosan.embedded.cassandra.test.spring.EmbeddedCassandra;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cassandra.ClusterBuilderCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import net.gueka.user.repository.UserRepository;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataCassandraTest
@EmbeddedCassandra(scripts = "/schema.cql", port = "0", rpcPort = "0", storagePort = "0", sslStoragePort = "0", jmxLocalPort = "0")
public class IntegrationTest {
    
    // Credits for https://github.com/nosan/cassandra-test-spring-boot

    @Autowired
    UserRepository repository;

    @TestConfiguration
	static class EmbeddedClusterBuilderCustomizerConfiguration {

		@Bean
		ClusterBuilderCustomizer embeddedClusterCustomizer(TestCassandra cassandra) {
			return builder -> {
				Settings settings = cassandra.getSettings();
				Integer port = settings.portOrSslPort().orElse(null);
				InetAddress address = settings.address().orElse(null);
				if (port != null && address != null) {
					builder.withPort(port).addContactPoints(address);
				}
			};
		}

	}
    @Autowired
	private Session session;

	@Test
	public void findAll() {
		long size = repository.count();
		assertThat(this.session.execute("SELECT * FROM tutorial.users")).hasSize(1);
	}
	
}