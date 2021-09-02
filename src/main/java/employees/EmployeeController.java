package employees;

import org.springframework.web.bind.annotation.*;

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
    public EmployeeDto findEmployeeById(@PathVariable("id") long id) throws IllegalAccessException {
        return employeeService.findEmployeeById(id);
    }

    @PostMapping
    public EmployeeDto createEmployee(@RequestBody CreateEmployeeCommand createEmployeeCommand){
        return employeeService.createEmployee(createEmployeeCommand);
    }
    @PutMapping("/{id}")
    public EmployeeDto updateEmployee(@PathVariable("id") long id,@RequestBody UpdateEmployeeCommand updateEmployeeCommand) throws IllegalAccessException {
        return employeeService.updateEmployee(id,updateEmployeeCommand);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable long id) throws IllegalAccessException {
        employeeService.deleteEmployee(id);
    }
}
