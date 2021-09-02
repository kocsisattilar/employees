package employees;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeesControllerRestTemplateIT {
    @Autowired
    TestRestTemplate template;

    @Autowired
    EmployeeService employeeService;

    @Test
    @RepeatedTest(2)
    void testListEmplyoees(){
        employeeService.DeletaAllEmployees();
        EmployeeDto employeeDto = template.postForObject("/api/employees", new CreateEmployeeCommand("John Doe"), EmployeeDto.class);
        assertEquals("John Doe",employeeDto.getName());
        template.postForObject("/api/employees", new CreateEmployeeCommand("Jane Doe"), EmployeeDto.class);
        List<EmployeeDto> employeeDtoList = template.exchange("/api/employees",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<EmployeeDto>>() {})
                .getBody();
        assertThat(employeeDtoList)
                .extracting(EmployeeDto::getName)
                .containsExactly("John Doe", "Jane Doe");
    }
}
