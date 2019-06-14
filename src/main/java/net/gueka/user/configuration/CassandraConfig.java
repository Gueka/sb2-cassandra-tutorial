package net.gueka.user.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "net.gueka.user.model")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${cassandra.keyspace}")
    public String keyspace;

    @Value("${cassandra.contactpoints}")
    private String contactPoints;

    @Value("${cassandra.port}")
    private int port;

    @Value("${cassandra.metrics.enabled:false}")
    private boolean metricsEnabled;

    @Override
    protected String getKeyspaceName() {
        return keyspace;
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    protected boolean getMetricsEnabled() { 
        return metricsEnabled; 
    }
}