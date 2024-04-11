package com.app.backend;

import java.math.BigDecimal;



import com.app.backend.services.transactions.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import javax.sql.DataSource;

@SpringBootApplication
public class BackendApplication {

	public static BigDecimal scanTicketCost = new BigDecimal(0);
	public static void main(String[] args) {

		scanTicketCost = TransactionService.getScanTransactionAmount();
		SpringApplication.run(BackendApplication.class, args);
	}
    @Autowired
    private DataSource dataSource;
	@Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setEnabled(true);
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    private ResourceDatabasePopulator databasePopulator() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("schema.sql"));
        return populator;
    }
}
