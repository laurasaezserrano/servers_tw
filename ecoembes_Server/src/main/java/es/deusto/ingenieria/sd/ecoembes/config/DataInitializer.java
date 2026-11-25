package es.deusto.ingenieria.sd.ecoembes.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.deusto.ingenieria.sd.ecoembes.dao.EmployeeRepository;
import es.deusto.ingenieria.sd.ecoembes.dao.RecyclingPlantRepository;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initData(EmployeeRepository employeeRepo, RecyclingPlantRepository plantRepo) {
        return args -> {
            // Save initial employees, dumpsters, and plants here
        };
    }
}