package employees;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class EmployeeService {
    //private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private EmployeeDao employeeDao;
    private ModelMapper modelMapper;
//    private AtomicLong isGenerator = new AtomicLong();
//
//
//    private List<Employee> employeeList = Collections.synchronizedList(new ArrayList<>(List.of(
//            new Employee(isGenerator.incrementAndGet(),"John Doe"),
//            new Employee(isGenerator.incrementAndGet(),"Jack Doe")
//    )));

    public  List<EmployeeDto> listEmployees(Optional<String> prefix){
        return employeeDao.findAll().stream()
                .map(e -> modelMapper.map(e, EmployeeDto.class))
                .collect(Collectors.toList());
    }
//    public  List<EmployeeDto> getEmployeeList(Optional<String> prefix){
//        Type targetListType = new TypeToken<List<EmployeeDto>>(){}.getType();
//        List<Employee> filtered = employeeList.stream()
//                .filter(e -> prefix.isEmpty() || e.getName().toLowerCase().startsWith(prefix.get().toLowerCase()))
//                .collect(Collectors.toList());
//        return modelMapper.map(filtered,targetListType);
//    }

    public EmployeeDto findEmployeeById(long id)  {
        return modelMapper.map(employeeDao.findById(id),
                EmployeeDto.class);
    }

    public EmployeeDto createEmployee(CreateEmployeeCommand createEmployeeCommand) {
        Employee employee = new Employee(createEmployeeCommand.getName());
        employeeDao.createEmployee(employee);
        log.info("Employee has been created");
        log.debug("Employee has been created with name {}", createEmployeeCommand.getName() );
        return modelMapper.map(employee,EmployeeDto.class);
    }

    public EmployeeDto updateEmployee(long id,UpdateEmployeeCommand updateEmployeeCommand) {
        Employee employee =new Employee(id,updateEmployeeCommand.getName());
        employeeDao.updateEmployee(employee);
        return modelMapper.map(employee,EmployeeDto.class);
    }

    public void deleteEmployee(long id) {
        employeeDao.deleteEmployee(id);
    }

    public void DeletaAllEmployees() {
        employeeDao.deleteAll();
    }
}
