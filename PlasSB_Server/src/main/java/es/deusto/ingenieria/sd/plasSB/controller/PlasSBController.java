package es.deusto.ingenieria.sd.plasSB.controller;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.ingenieria.sd.plasSB.dto.CapacityDTO;
import es.deusto.ingenieria.sd.plasSB.dto.NotificationDTO;
import es.deusto.ingenieria.sd.plasSB.service.PlasSBService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/plassb")
public class PlasSBController {

    private final PlasSBService service;

    public PlasSBController(PlasSBService service) { this.service = service; }

    @GetMapping("/capacity")
    public ResponseEntity<CapacityDTO> getCapacity(@RequestParam LocalDate date) {
        return service.getCapacity(date)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/notifications")
    public ResponseEntity<NotificationDTO> notifyAssignment(@RequestBody NotificationDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveNotification(dto));
    }
}
