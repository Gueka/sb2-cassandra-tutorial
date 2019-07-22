package net.gueka.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.cql.CqlIdentifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import net.gueka.user.model.Location;
import net.gueka.user.model.User;
import net.gueka.user.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @Autowired
	UserRepository repository;
	
	@Autowired
	CassandraAdminOperations adminTemplate;

	TestRestTemplate restTemplate = new TestRestTemplate();
	
	@LocalServerPort
	Integer port;

	private static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE tutorial WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };";
	private static final String KEYSPACE_ACTIVATE_QUERY = "USE tutorial;";
	private static final String LOCATION_TYPE_CREATION_QUERY = "CREATE TYPE LOCATION (city TEXT, zip_code TEXT, address TEXT);";
	private static final String DATA_TABLE_NAME = "users";

	@BeforeAll
    public static void startCassandraEmbedded() throws InterruptedException, TTransportException, ConfigurationException, IOException {
		EmbeddedCassandraServerHelper.startEmbeddedCassandra();
		final Cluster cluster = Cluster.builder()
			.addContactPoints("localhost")
			.withPort(9142)
			.withoutMetrics()
			.build();
		final Session session = cluster.connect();
        session.execute(KEYSPACE_CREATION_QUERY);
		session.execute(KEYSPACE_ACTIVATE_QUERY);
		session.execute(LOCATION_TYPE_CREATION_QUERY);
		
	}
	
    
    @Test
    public void saveTest() throws Exception {
       
        HttpEntity<User> entity = new HttpEntity<User>(getRequest("test"));

		ResponseEntity<User> response = restTemplate.exchange(
				"http://localhost:" + port + "/user",
				HttpMethod.POST, entity, new ParameterizedTypeReference<User>() {
				});

		assertTrue(response.getBody() != null, "Has to return at leas a message");
        assertEquals("test", response.getBody().getName(), "Has to return test username");
    }

    private User getRequest(String name) {
		return User.builder()
			.id(UUID.randomUUID())
			.name(name)
			.location(Location.builder()
				.city("Gotham")
				.build())
			.build();
    }

    @BeforeEach
    public void createTable() throws InterruptedException, TTransportException, ConfigurationException, IOException {
        adminTemplate.createTable(true, CqlIdentifier.of(DATA_TABLE_NAME, true), User.class, new HashMap<String, Object>());
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