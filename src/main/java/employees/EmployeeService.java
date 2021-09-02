package employees;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeService {
    public EmployeeService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private ModelMapper modelMapper;
    private List<Employee> employeeList = Collections.synchronizedList(new ArrayList<>(List.of(
            new Employee(1l,"John Doe"),
            new Employee(2l,"Jack Doe")
    )));

    public  List<EmployeeDto> getEmployeeList(){
        Type targetListType = new TypeToken<List<EmployeeDto>>(){}.getType();
        return modelMapper.map(employeeList,targetListType);
    }
}
