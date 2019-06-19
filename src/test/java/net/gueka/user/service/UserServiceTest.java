package net.gueka.user.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.gueka.user.configuration.CassandraConfig;
import net.gueka.user.model.User;
import net.gueka.user.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CassandraConfig.class)
public class UserServiceTest {

    //private static final Log LOGGER = LogFactory.getLog(BookRepositoryIntegrationTest.class);

    // public static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS testKeySpace WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";

    // public static final String KEYSPACE_ACTIVATE_QUERY = "USE testKeySpace;";

    // public static final String DATA_TABLE_NAME = "user";

    // @Autowired
    // private CassandraAdminOperations adminTemplate;

    @Autowired
    private UserRepository repository;

    // @BeforeAll
    // public static void startCassandraEmbedded()
    //         throws ConfigurationException, TTransportException, IOException, InterruptedException {
    //     EmbeddedCassandraServerHelper.startEmbeddedCassandra(); 
    //     Cluster cluster = Cluster.builder().addContactPoints("localhost").withPort(9142).build();
    //     Session session = cluster.connect();
    //     session.execute(KEYSPACE_CREATION_QUERY);
    //     session.execute(KEYSPACE_ACTIVATE_QUERY);
    //     Thread.sleep(5000);
    // }

    // @AfterAll
    // public static void stopCassandraEmbedded() {
    //     EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
    // }

    // @BeforeEach
    // public void createTable() {
    //     adminTemplate.createTable(true, CqlIdentifier.cqlId(DATA_TABLE_NAME), User.class, new HashMap<String, Object>());
    // }

    // @AfterEach
    // public void dropTable() {
    //     adminTemplate.dropTable(CqlIdentifier.cqlId(DATA_TABLE_NAME));
    // }

    @Test
    @Ignore
    public void whenSavingBook_thenAvailableOnRetrieval() {
        final User firstUser = new User();
        firstUser.setName("marco");
        firstUser.setId(UUID.randomUUID());
        firstUser.setSurname("capo");
        repository.save(firstUser);
        final List<User> users = repository.findAll();

        assertNotNull(users);
    }
}