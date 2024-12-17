package web.service.controller;

import jpa.entities.MathRes;
import jpa.repository.MathResRepos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import web.service.service.MathService;
import org.springframework.boot.test.context.SpringBootTest;
import web.service.application.Application;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class MathFunctionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MathService mathService;
    @MockBean
    private MathResRepos mathResRepository;

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
        MathRes mathRes1 = new MathRes(1.0, 2.0, 123456L);
        mathRes1.setId(0);
        MathRes mathRes2 = new MathRes(3.0, 4.0, 654321L);
        mathRes2.setId(1);
        List<MathRes> mathResList = Arrays.asList(mathRes1, mathRes2);

        when(mathService.findAll()).thenReturn(mathResList);

        mockMvc.perform(get("/api/math"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(0))
                .andExpect(jsonPath("$[0].x").value(1.0))
                .andExpect(jsonPath("$[0].y").value(2.0))
                .andExpect(jsonPath("$[0].hash").value(123456L))
                .andExpect(jsonPath("$[1].id").value(1))
                .andExpect(jsonPath("$[1].x").value(3.0))
                .andExpect(jsonPath("$[1].y").value(4.0))
                .andExpect(jsonPath("$[1].hash").value(654321L));

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

    @Test
    void addMathResShouldReturnCreated() throws Exception {
        MathRes newMathRes = new MathRes(5.0, 25.0, 987654L);
        newMathRes.setId(3); // предполагаем, что id генерируется сервером

        when(mathService.addMathRes(any(MathRes.class))).thenReturn(newMathRes);

        mockMvc.perform(post("/api/math")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"x\": 5.0, \"y\": 25.0, \"hash\": 987654 }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.x").value(5.0))
                .andExpect(jsonPath("$.y").value(25.0))
                .andExpect(jsonPath("$.hash").value(987654));

        verify(mathService).addMathRes(any(MathRes.class));
    }

    @Test
    void addBulkMathResShouldReturnCreated() throws Exception {
        MathRes newMathRes1 = new MathRes(5.0, 25.0, 987654L);
        MathRes newMathRes2 = new MathRes(6.0, 36.0, 123987L);
        List<MathRes> newMathResList = Arrays.asList(newMathRes1, newMathRes2);

        when(mathService.addMathResList(anyList())).thenReturn(newMathResList);

        mockMvc.perform(post("/api/math/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{ \"x\": 5.0, \"y\": 25.0, \"hash\": 987654 }, { \"x\": 6.0, \"y\": 36.0, \"hash\": 123987 }]"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].x").value(5.0))
                .andExpect(jsonPath("$[0].y").value(25.0))
                .andExpect(jsonPath("$[0].hash").value(987654))
                .andExpect(jsonPath("$[1].x").value(6.0))
                .andExpect(jsonPath("$[1].y").value(36.0))
                .andExpect(jsonPath("$[1].hash").value(123987));

        verify(mathService).addMathResList(anyList());
    }

    @Test
    void addMathResShouldReturnBadRequestOnInvalidData() throws Exception {
        mockMvc.perform(post("/api/math")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"x\": \"invalid\", \"y\": 25.0, \"hash\": 987654 }"))
                .andExpect(status().isBadRequest());

        verify(mathService, never()).addMathRes(any(MathRes.class));
    }
}
