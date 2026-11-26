package es.deusto.ingenieria.sd.ecoembes.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.deusto.ingenieria.sd.ecoembes.dao.DumpsterRepository;
import es.deusto.ingenieria.sd.ecoembes.dao.EmployeeRepository;
import es.deusto.ingenieria.sd.ecoembes.dao.RecyclingPlantRepository;
import es.deusto.ingenieria.sd.ecoembes.entity.Dumpster;
import es.deusto.ingenieria.sd.ecoembes.entity.Employee;
import es.deusto.ingenieria.sd.ecoembes.entity.FillLevel;
import es.deusto.ingenieria.sd.ecoembes.entity.RecyclingPlant;

@Configuration
public class DataInitializer {
    
    @Bean
    CommandLineRunner initData(EmployeeRepository employeeRepo, DumpsterRepository dumpsterRepo, RecyclingPlantRepository plantRepo) {
        return args -> {
            if (employeeRepo.count() == 0) {
                employeeRepo.save(new Employee("Laura", "laura@gmail.com", "1234"));
                employeeRepo.save(new Employee("Carmen", "carmen@gmail.com", "1234"));
                employeeRepo.save(new Employee("Zaira", "zaira@gmail.com", "1234"));
                employeeRepo.save(new Employee("Jan", "jan@gmail.com", "1234"));
            }

            if (plantRepo.count() == 0) {
                plantRepo.save(new RecyclingPlant(1, "PlasSB Ltd.", "Bilbao"));
                plantRepo.save(new RecyclingPlant(2, "ContSocket Ltd.", "Vitoria"));
            }

            if (dumpsterRepo.count() == 0) {
                Dumpster d1 = new Dumpster("Gran Via 1, Bilbao", "48001", 10);
                d1.update(LocalDate.now(), 3, FillLevel.GREEN);
                dumpsterRepo.save(d1);

                Dumpster d2 = new Dumpster("Calle Mayor 5, Bilbao", "48001", 10);
                d2.update(LocalDate.now(), 7, FillLevel.ORANGE);
                dumpsterRepo.save(d2);
            }
        };
    }
}