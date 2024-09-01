package com.wipro.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.wipro.dto.DepartmentDto;
import com.wipro.service.DepartmentService;

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
    }

    @Test
    public void testCreateDepartment() throws Exception {
        DepartmentDto departmentDto = new DepartmentDto(); // Add required fields
        departmentDto.setName("IT");
        departmentDto.setDescription("Information Technology");
        when(departmentService.createDepartment(any(DepartmentDto.class))).thenReturn(departmentDto);

        mockMvc.perform(post("/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"IT\",\"description\":\"Information Technology\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("IT"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetAllDepartments() throws Exception {
        List<DepartmentDto> departments = List.of(new DepartmentDto()); // Add required fields
        when(departmentService.getAllDepartments()).thenReturn(departments);

        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").isNotEmpty())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetDepartmentById() throws Exception {
        DepartmentDto departmentDto = new DepartmentDto(); // Add required fields
        departmentDto.setId(1L);
        when(departmentService.getDepartmentById(1L)).thenReturn(departmentDto);

        mockMvc.perform(get("/departments/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testUpdateDepartment() throws Exception {
        DepartmentDto departmentDto = new DepartmentDto(); // Add required fields
        departmentDto.setName("HR");
        departmentDto.setDescription("Human Resources");
        when(departmentService.updateDepartment(eq(1L), any(DepartmentDto.class))).thenReturn(departmentDto);

        mockMvc.perform(put("/departments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"HR\",\"description\":\"Human Resources\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("HR"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDeleteDepartment() throws Exception {
        when(departmentService.deleteDepartment(1L)).thenReturn("Department deleted successfully");

        mockMvc.perform(delete("/departments/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Department deleted successfully"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetDepartmentByCode() throws Exception {
        DepartmentDto departmentDto = new DepartmentDto(); // Add required fields
        departmentDto.setName("IT");
        when(departmentService.getDepartmentByCode("IT")).thenReturn(departmentDto);

        mockMvc.perform(get("/departments/code/IT"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("IT"))
                .andDo(MockMvcResultHandlers.print());
    }
}
