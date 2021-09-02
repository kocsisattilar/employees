package employees;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private ModelMapper modelMapper;
    private AtomicLong isGenerator = new AtomicLong();

    public EmployeeService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    private List<Employee> employeeList = Collections.synchronizedList(new ArrayList<>(List.of(
            new Employee(isGenerator.incrementAndGet(),"John Doe"),
            new Employee(isGenerator.incrementAndGet(),"Jack Doe")
    )));

    public  List<EmployeeDto> getEmployeeList(Optional<String> prefix){
        Type targetListType = new TypeToken<List<EmployeeDto>>(){}.getType();
        List<Employee> filtered = employeeList.stream()
                .filter(e -> prefix.isEmpty() || e.getName().toLowerCase().startsWith(prefix.get().toLowerCase()))
                .collect(Collectors.toList());
        return modelMapper.map(filtered,targetListType);
    }

    public EmployeeDto findEmployeeById(long id)  {
        return modelMapper.map(employeeList.stream()
                .filter(e -> e.getId().equals(id)).findAny()
                .orElseThrow(() ->new EmployeeNotFoundException(+ id)),
                EmployeeDto.class);
    }

    public EmployeeDto createEmployee(CreateEmployeeCommand createEmployeeCommand) {
        Employee employee = new Employee(isGenerator.incrementAndGet(),createEmployeeCommand.getName());
        employeeList.add(employee);
        return modelMapper.map(employee,EmployeeDto.class);
    }

    public EmployeeDto updateEmployee(long id,UpdateEmployeeCommand updateEmployeeCommand) {
        Employee employee = employeeList.stream()
                .filter(e-> e.getId()==id)
                .findFirst().orElseThrow(() ->new EmployeeNotFoundException( id));
        employee.setName(updateEmployeeCommand.getName());
        return modelMapper.map(employee,EmployeeDto.class);
    }

    public void deleteEmployee(long id) {
        Employee employee = employeeList.stream()
                .filter(e-> e.getId()==id)
                .findFirst().orElseThrow(() ->new EmployeeNotFoundException( id));
        employeeList.remove(employee);
    }
}
