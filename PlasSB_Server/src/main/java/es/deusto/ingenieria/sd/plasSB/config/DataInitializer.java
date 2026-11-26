package es.deusto.ingenieria.sd.plasSB.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.deusto.ingenieria.sd.plasSB.dao.CapacityRepository;
import es.deusto.ingenieria.sd.plasSB.entity.Capacity;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initCapacities(CapacityRepository capacityRepository) {
        return args -> {
            if (capacityRepository.count() == 0) {
                LocalDate today = LocalDate.now();
                for (int i = 0; i < 30; i++) {
                    capacityRepository.save(new Capacity(today.plusDays(i), 50.0));
                }
            }
        };
    }
}