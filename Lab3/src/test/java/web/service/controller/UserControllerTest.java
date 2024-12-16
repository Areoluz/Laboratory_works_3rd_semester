package web.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jpa.entities.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import web.service.application.Application;
import web.service.dto.RegisterDTO;
import web.service.service.UserService;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testAuth() throws Exception {
        mockMvc.perform(post("/register")
                        .content(objectMapper.writeValueAsString(new RegisterDTO("Dinis", "1234")))
                        .contentType(MediaType.APPLICATION_JSON)) //спецификация
                        .andExpect(status().isOk());
        Mockito.verify(userService).createUser(Mockito.any());
    }

    @Test
    void testLogin() throws Exception {
        Mockito.when(userService.loadUserByUsername("Dinis")).thenReturn(new User(28023L,"Dinis", passwordEncoder.encode("1234")));
        mockMvc.perform(post("/login").param("username", "Dinis").param("password", "1234"))
                .andExpect(status().isOk());
        Mockito.verify(userService).loadUserByUsername("Dinis");

    }
}

