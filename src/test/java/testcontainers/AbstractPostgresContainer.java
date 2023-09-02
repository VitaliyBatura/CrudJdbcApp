package testcontainers;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public abstract class AbstractPostgresContainer {

    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(new FileReader("src/test/resources/datasource.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER;

    private static final int CONTAINER_PORT = 5432;
    private static final int HOST_PORT = Integer.parseInt(properties.getProperty("port"));
    private static final PortBinding PORT_BINDING = new PortBinding(Ports.Binding.bindPort(HOST_PORT),
            new ExposedPort(CONTAINER_PORT));

    static {
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:14-alpine")
                .withDatabaseName(properties.getProperty("databaseName"))
                .withUsername(properties.getProperty("user"))
                .withPassword(properties.getProperty("password"))
                .withExposedPorts(CONTAINER_PORT)
                .withInitScript("db.sql")
                .withCreateContainerCmdModifier(cmd ->
                        cmd.withHostConfig(new HostConfig().withPortBindings(PORT_BINDING)));
        POSTGRE_SQL_CONTAINER.start();
    }
}
