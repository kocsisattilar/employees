package employees;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }

    @GetMapping
    public List<EmployeeDto> employeeList(@RequestParam Optional<String> prefix){
        return employeeService.getEmployeeList(prefix);
    }
    @GetMapping("/{id}")
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
    public EmployeeDto createEmployee(@RequestBody CreateEmployeeCommand createEmployeeCommand){
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
}
