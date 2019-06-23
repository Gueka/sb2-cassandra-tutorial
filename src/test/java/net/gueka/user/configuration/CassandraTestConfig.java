package net.gueka.user.configuration;

import java.net.InetAddress;

import com.github.nosan.embedded.cassandra.Settings;
import com.github.nosan.embedded.cassandra.test.TestCassandra;

import org.springframework.boot.autoconfigure.cassandra.ClusterBuilderCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

//@TestConfiguration
public class CassandraTestConfig {

    // @Bean
    // ClusterBuilderCustomizer embeddedClusterCustomizer(TestCassandra cassandra) {
    //     return builder -> {
    //         Settings settings = cassandra.getSettings();
    //         Integer port = settings.portOrSslPort().orElse(null);
    //         InetAddress address = settings.address().orElse(null);
    //         if (port != null && address != null) {
    //             builder.withPort(port).addContactPoints(address);
    //         }
    //     };
    // }

}