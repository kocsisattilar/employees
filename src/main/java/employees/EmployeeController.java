package employees;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
@Tag(name="Operations on employees")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<EmployeeDto> employeeList(@RequestParam Optional<String> prefix){
        return employeeService.getEmployeeList(prefix);
    }
    @GetMapping(value="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EmployeeDto findEmployeeById(@PathVariable("id") long id)  {
        return (employeeService.findEmployeeById(id));
    }

//    @ExceptionHandler(IllegalAccessException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<Problem> handleNotFound(IllegalAccessException iea){
//        Problem problem = Problem.builder()
//                .withType(URI.create("emplyoees/not-found"))
//                .withTitle("Not found")
//                .withStatus(Status.NOT_FOUND)
//                .withDetail(iea.getMessage())
//                .build();
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
//                .body(problem);
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity findEmployeeById(@PathVariable("id") long id) {
//        try {
//            return ResponseEntity.ok(employeeService.findEmployeeById(id));
//        } catch (IllegalAccessException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates an employee")
    @ApiResponse(responseCode = "201", description = "employee was created")
    public EmployeeDto createEmployee(@Valid @RequestBody CreateEmployeeCommand createEmployeeCommand){
        return employeeService.createEmployee(createEmployeeCommand);
    }
    @PutMapping("/{id}")
    public EmployeeDto updateEmployee(@PathVariable("id") long id,@RequestBody UpdateEmployeeCommand updateEmployeeCommand) throws IllegalAccessException {
        return employeeService.updateEmployee(id,updateEmployeeCommand);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployee(id);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Problem> handleNotFound(MethodArgumentNotValidException manve){
        List<Violation> violations = manve.getBindingResult().getFieldErrors().stream()
                .map(fe -> new Violation(fe.getField(),fe.getDefaultMessage()))
                .collect(Collectors.toList());

        Problem problem = Problem.builder()
                .withType(URI.create("emplyoees/not-vilad"))
                .withTitle("Validation error")
                .withStatus(Status.BAD_REQUEST)
                .withDetail(manve.getMessage())
                .with("violations",violations)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }
}
