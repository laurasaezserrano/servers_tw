package es.deusto.ingenieria.sd.ecoembes.facade;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import es.deusto.ingenieria.sd.ecoembes.dto.DumpsterCreationDTO;
import es.deusto.ingenieria.sd.ecoembes.dto.DumpsterDTO;
import es.deusto.ingenieria.sd.ecoembes.dto.DumpsterUsageDTO;
import es.deusto.ingenieria.sd.ecoembes.dto.RecyclingPlantDTO;
import es.deusto.ingenieria.sd.ecoembes.entity.Employee;
import es.deusto.ingenieria.sd.ecoembes.entity.FillLevel;
import es.deusto.ingenieria.sd.ecoembes.service.AssignmentService;
import es.deusto.ingenieria.sd.ecoembes.service.AuthService;
import es.deusto.ingenieria.sd.ecoembes.service.DumpsterService;
import es.deusto.ingenieria.sd.ecoembes.service.RecyclingPlantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/dumpsters")
@Tag(name = "Dumpsters and Recycling Plants", description = "Operations related to dumpsters and recycling plants")
public class DumpstersController {

	private final DumpsterService dumpsterService;
    private final RecyclingPlantService plantService;
    private final AssignmentService assignmentService;
    private final AuthService authService;
	
	public DumpstersController(DumpsterService dumpsterService, RecyclingPlantService plantService, AssignmentService assignmentService, AuthService authService) {
		this.dumpsterService = dumpsterService;
		this.plantService = plantService;
		this.assignmentService = assignmentService;
		this.authService = authService;
	}
	
	@Operation(
	    summary = "Update dumpster status",
	    description = "Updates the fill level and the number of containers of an existing dumpster.",
	    responses = {
	         @ApiResponse(responseCode = "204", description = "Dumpster status updated successfully"),
	         @ApiResponse(responseCode = "400", description = "Bad request - invalid fill level or number of containers"),
	         @ApiResponse(responseCode = "404", description = "Dumpster not found")
	        })
    @PostMapping("/{dumpsterId}/status")
    public ResponseEntity<Void> updateDumpster( 
	    @PathVariable("dumpsterId") long dumpsterId,
        @RequestParam("token") String token,
        @RequestParam("fillLevel") FillLevel fillLevel,
        @RequestParam("numContainers") int numContainers) {
		
		if (authService.getEmployeeByToken(token).isEmpty()) {
		    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		dumpsterService.updateDumpster(dumpsterId, fillLevel, numContainers);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/new-dumpster")
    public ResponseEntity<DumpsterDTO> createDumpster(
            @RequestParam("token") String token,
            @RequestBody DumpsterCreationDTO dto){
    	
    	Optional<Employee> employee = authService.getEmployeeByToken(token);
    	if (employee.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
    	
    	DumpsterDTO newDumpster = dumpsterService.createDumpster(dto);
		return new ResponseEntity<>(newDumpster, HttpStatus.CREATED);
    }

    @GetMapping("/all-dumpsters")
    public ResponseEntity<List<DumpsterDTO>> getAllDumpsters(@RequestParam("token") String token) {
		if (authService.getEmployeeByToken(token).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		List<DumpsterDTO> dumpsters = dumpsterService.getAllDumpsters();
		return new ResponseEntity<>(dumpsters, HttpStatus.OK);
    }

    @GetMapping("/{dumpsterId}/usage")
    public ResponseEntity<List<DumpsterUsageDTO>> getDumpsterUsage(
			@PathVariable("dumpsterId") long dumpsterId,
			@RequestParam("token") String token,
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
 
		if (authService.getEmployeeByToken(token).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		if (startDate.isAfter(endDate)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		List<DumpsterUsageDTO> usage = dumpsterService.getDumpsterUsage(dumpsterId, startDate, endDate);
		if (usage == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(usage, HttpStatus.OK);
	}
    
    @GetMapping("/recycling-plants")
    public ResponseEntity<List<RecyclingPlantDTO>> getRecyclingPlants(@RequestParam("token") String token) {
		if (authService.getEmployeeByToken(token).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(plantService.getAllPlants(), HttpStatus.OK);
	}
    
    @GetMapping("/recycling-plants/{plantId}/capacity")
    public float getRecyclingPlantCapacity(
            @PathVariable("plantId") long plantId,
            @RequestParam("token") String token,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        if (authService.getEmployeeByToken(token).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
        float capacity = plantService.getPlantCapacity(plantId, date);
        if (capacity < 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recycling plant not found");
        }
        return capacity;
    }
    
    @PostMapping("/recycling-plants/{plantId}/dumpsters")
    public ResponseEntity<Void> assignDumpstersToPlant(
            @PathVariable("plantId") long plantId,
            @RequestParam("token") String token,
            @RequestBody List<Long> dumpsterIds) {

        Optional<Employee> employee = authService.getEmployeeByToken(token);
        if (employee.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (dumpsterIds == null || dumpsterIds.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        boolean success = assignmentService.assignDumpstersToPlant(plantId, dumpsterIds, employee.get());
        return new ResponseEntity<>(success ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/status")
    public ResponseEntity<FillLevel> getDumpstersStatus(
            @RequestParam("token") String token,
            @RequestParam("postalCode") String postalCode,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        if (authService.getEmployeeByToken(token).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        FillLevel status = dumpsterService.getDumpstersStatus(postalCode, date);
        if (status == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}