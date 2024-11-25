package web.service.controller;

import jpa.entities.MathRes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import web.service.MathService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MathFunctionController.class)
class MathFunctionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MathService mathService;

    private MathRes mathRes1;
    private MathRes mathRes2;
    private List<MathRes> mathResList;

    @BeforeEach
    void setUp() {
        mathRes1 = new MathRes();
        mathRes1.setId(1);
        mathRes1.setX(2.0);
        mathRes1.setY(4.0);
        mathRes1.setHash(123L);

        mathRes2 = new MathRes();
        mathRes2.setId(2);
        mathRes2.setX(3.0);
        mathRes2.setY(9.0);
        mathRes2.setHash(123L);

        mathResList = Arrays.asList(mathRes1, mathRes2);
    }

    @Test
    void getAllShouldReturnListOfMathRes() throws Exception {
        when(mathService.findAll()).thenReturn(mathResList);

        mockMvc.perform(get("/api/math"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].x").value(2.0))
                .andExpect(jsonPath("$[0].y").value(4.0))
                .andExpect(jsonPath("$[0].hash").value(123))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].x").value(3.0))
                .andExpect(jsonPath("$[1].y").value(9.0))
                .andExpect(jsonPath("$[1].hash").value(123));

        verify(mathService).findAll();
    }

    @Test
    void getByHashShouldReturnMatchingResults() throws Exception {
        when(mathService.findByHash(123L)).thenReturn(mathResList);

        mockMvc.perform(get("/api/math/123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].hash").value(123))
                .andExpect(jsonPath("$[1].hash").value(123));

        verify(mathService).findByHash(123L);
    }

    @Test
    void deleteAllShouldReturnOk() throws Exception {
        doNothing().when(mathService).deleteAll();

        mockMvc.perform(delete("/api/math"))
                .andExpect(status().isOk());

        verify(mathService).deleteAll();
    }

    @Test
    void deleteByHashShouldReturnOk() throws Exception {
        doNothing().when(mathService).deleteByHash(anyLong());

        mockMvc.perform(delete("/api/math/123"))
                .andExpect(status().isOk());

        verify(mathService).deleteByHash(123L);
    }

    @Test
    void getAllShouldHandleError() throws Exception {
        when(mathService.findAll()).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(get("/api/math"))
                .andExpect(status().isInternalServerError());
    }
}
