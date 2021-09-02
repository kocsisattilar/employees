package employees;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.config.RestAssuredMockMvcConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.with;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeesControllerRestAssuredIT {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    EmployeeService employeeService;

    @BeforeEach
    void init()
    {
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.requestSpecification =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON);
        employeeService.DeletaAllEmployees();
    }
    @Test
    void testListEmployees(){
        String name="John Doe";
        with().body(new CreateEmployeeCommand(name))
                .post("/api/employees")
                .then()
                .statusCode(201)
                .body("name", equalTo(name))
                .log();

        with()
                .get("/api/employees")
                .then()
                .statusCode(200)
                .body("[0].name",equalTo(name))
                .body("size()", equalTo(1));
    }
}
