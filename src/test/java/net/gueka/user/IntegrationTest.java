package net.gueka.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.cql.CqlIdentifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import net.gueka.user.configuration.CassandraConfig;
import net.gueka.user.model.Location;
import net.gueka.user.model.User;
import net.gueka.user.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CassandraConfig.class)
public class IntegrationTest {

    @Autowired
	UserRepository repository;
	
	@Autowired
	CassandraAdminOperations adminTemplate;

	private static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE tutorial WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };";
	private static final String KEYSPACE_ACTIVATE_QUERY = "USE tutorial;";
	private static final String LOCATION_TYPE_CREATION_QUERY = "CREATE TYPE LOCATION (city TEXT, zip_code TEXT, address TEXT);";
	private static final String DATA_TABLE_NAME = "users";

	@BeforeAll
    public static void startCassandraEmbedded() throws InterruptedException, TTransportException, ConfigurationException, IOException {
		EmbeddedCassandraServerHelper.startEmbeddedCassandra();
		final Cluster cluster = Cluster.builder()
			.addContactPoints("127.0.0.1")
			.withPort(9142)
			.withoutMetrics()
			.build();
		final Session session = cluster.connect();
        session.execute(KEYSPACE_CREATION_QUERY);
		session.execute(KEYSPACE_ACTIVATE_QUERY);
		session.execute(LOCATION_TYPE_CREATION_QUERY);
		
    }

    @BeforeEach
    public void createTable() throws InterruptedException, TTransportException, ConfigurationException, IOException {
        adminTemplate.createTable(true, CqlIdentifier.of(DATA_TABLE_NAME, true), User.class, new HashMap<String, Object>());
    }

    @Test
    public void whenSavingBook_thenAvailableOnRetrieval() {
		final User user = User.builder()
			.id(UUID.randomUUID())
			.name("asd")
			.location(Location.builder().city("test").build())
			.build();
        repository.save(user);
        final Iterable<User> users = repository.findAll();
        assertEquals(user.getId(), users.iterator().next().getId());
	}

	@AfterEach
	public void dropTable() {
		adminTemplate.dropTable(CqlIdentifier.of(DATA_TABLE_NAME, true));
	}

	@AfterAll
	public static void stopCassandraEmbedded() {
		EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
	}
	
}