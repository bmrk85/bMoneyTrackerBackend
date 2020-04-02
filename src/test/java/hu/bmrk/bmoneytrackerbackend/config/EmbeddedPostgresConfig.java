package hu.bmrk.bmoneytrackerbackend.config;


import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import com.p6spy.engine.spy.P6DataSource;
import org.postgresql.Driver;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.UncheckedIOException;

@Component
public class EmbeddedPostgresConfig {

    static EmbeddedPostgres postgres;

    static {
        try {
            postgres = EmbeddedPostgres.start();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    @Bean
    DataSource dataSource() throws IOException {
        DataSource ds = DataSourceBuilder.create().
                driverClassName(Driver.class.getName()).
                url(postgres.getJdbcUrl("postgres", "postgres")).build();
        return new P6DataSource(ds);
    }

    @PreDestroy
    public void destroy() throws IOException {
        postgres.close();
    }
}
