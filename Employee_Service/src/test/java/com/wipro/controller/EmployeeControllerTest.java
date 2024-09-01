package com.wipro.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.dto.AllApiResponnseDto;
import com.wipro.dto.ApiResponseDto;
import com.wipro.dto.EmployeeDto;
import com.wipro.dto.PerformanceApiResponseDto;
import com.wipro.dto.TaskApiResponseDto;
import com.wipro.service.EmployeeService;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

//    @Test
//    public void testCreateEmployee() throws Exception {
//        EmployeeDto employeeDto = new EmployeeDto();
//        employeeDto.setId(1L);
//        employeeDto.setName("John Doe");
//
//        // Mock service method
//        when(employeeService.createEmployee(any(EmployeeDto.class))).thenReturn(employeeDto);
//
//        // Convert DTO to JSON
//        String employeeDtoJson = new ObjectMapper().writeValueAsString(employeeDto);
//
//        // Perform the POST request
//        mockMvc.perform(post("/api/employees")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(employeeDtoJson))
//                .andExpect(status().isCreated())
//                .andExpect(content().json(employeeDtoJson))
//                .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString())); // Debug line
//    }

    @Test
    public void testGetAllEmployees() throws Exception {
        List<EmployeeDto> employeeDtos = Arrays.asList(new EmployeeDto(), new EmployeeDto());
        when(employeeService.getAllEmployees()).thenReturn(employeeDtos);

        // Convert the list to JSON
        String employeeDtosJson = new ObjectMapper().writeValueAsString(employeeDtos);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(content().json(employeeDtosJson));
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        EmployeeDto employeeDto = new EmployeeDto();
        // Set required fields on the employeeDto object
        employeeDto.setId(1L);
        employeeDto.setName("John Doe");

        when(employeeService.getEmployeeById(anyLong())).thenReturn(employeeDto);

        // Convert the EmployeeDto to JSON
        String employeeDtoJson = new ObjectMapper().writeValueAsString(employeeDto);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(employeeDtoJson));
    }

//    @Test
//    public void testUpdateEmployee() throws Exception {
//        EmployeeDto employeeDto = new EmployeeDto();
//        // Set required fields on the employeeDto object
//        employeeDto.setId(1L);
//        employeeDto.setName("John Doe");
//
//        when(employeeService.updateEmployee(anyLong(), any(EmployeeDto.class))).thenReturn(employeeDto);
//
//        // Convert the EmployeeDto to JSON
//        String employeeDtoJson = new ObjectMapper().writeValueAsString(employeeDto);
//
//        mockMvc.perform(put("/api/employees/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(employeeDtoJson))
//                .andExpect(status().isOk())
//                .andExpect(content().json(employeeDtoJson));
//    }

    @Test
    public void testDeleteEmployee() throws Exception {
        when(employeeService.deleteEmployee(anyLong())).thenReturn("Employee deleted successfully");

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee deleted successfully"));
    }

    @Test
    public void testGetEmployeeByIdAndCode() throws Exception {
        ApiResponseDto apiResponseDto = new ApiResponseDto();
        when(employeeService.getEmployeeByIdAndCode(anyLong())).thenReturn(apiResponseDto);

        // Convert the ApiResponseDto to JSON
        String apiResponseDtoJson = new ObjectMapper().writeValueAsString(apiResponseDto);

        mockMvc.perform(get("/api/employees/code/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(apiResponseDtoJson));
    }

    @Test
    public void testGetEmployeeAndTask() throws Exception {
        TaskApiResponseDto taskApiResponseDto = new TaskApiResponseDto();
        when(employeeService.getTaskAndEmployee(anyLong())).thenReturn(taskApiResponseDto);

        // Convert the TaskApiResponseDto to JSON
        String taskApiResponseDtoJson = new ObjectMapper().writeValueAsString(taskApiResponseDto);

        mockMvc.perform(get("/api/employees/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(taskApiResponseDtoJson));
    }

    @Test
    public void testGetEmployeeAndPerformance() throws Exception {
        PerformanceApiResponseDto performanceApiResponseDto = new PerformanceApiResponseDto();
        when(employeeService.getEmployeePerformance(anyLong())).thenReturn(performanceApiResponseDto);

        // Convert the PerformanceApiResponseDto to JSON
        String performanceApiResponseDtoJson = new ObjectMapper().writeValueAsString(performanceApiResponseDto);

        mockMvc.perform(get("/api/employees/performance/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(performanceApiResponseDtoJson));
    }

    @Test
    public void testGetAllService() throws Exception {
        AllApiResponnseDto allApiResponnseDto = new AllApiResponnseDto();
        when(employeeService.getAllServices(anyLong())).thenReturn(allApiResponnseDto);

        // Convert the AllApiResponnseDto to JSON
        String allApiResponnseDtoJson = new ObjectMapper().writeValueAsString(allApiResponnseDto);

        mockMvc.perform(get("/api/employees/all/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(allApiResponnseDtoJson));
    }
}
