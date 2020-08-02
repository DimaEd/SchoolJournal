package com.ednach.controller;

import com.ednach.Main;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@WithMockUser(roles={"ADMIN"})
class SinControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/sins"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].typeSin").value("fight"))
                .andExpect(jsonPath("$[0].points").value(5))
                .andReturn();
    }

    @Test
    void getOneExist() throws Exception {
        mockMvc.perform(get("/sins/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.typeSin").value("fight"))
                .andReturn();
    }

    @Test
    void getOneNotExist() throws Exception {
        mockMvc.perform(get("/sins/5"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Проступок не существует!"))
                .andReturn();
    }

    @Test
    void testSaveHaveIdBadRequest() throws Exception {
        mockMvc.perform(post("/sins/3").contentType(APPLICATION_JSON_UTF8).content("{\"typeSin\":\"hooky\",\"points\":7,\"teacherId\":2,\"schoolboyId\":1}"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void save() throws Exception {
        mockMvc.perform(post("/sins").contentType(APPLICATION_JSON_UTF8).content("{\"typeSin\":\"hooky\",\"points\":7,\"teacherId\":2,\"schoolboyId\":1}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.typeSin").value("hooky"))
                .andReturn();
    }


    @Test
    void testPutOneBadRequest() throws Exception {
        mockMvc.perform(put("/sins/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":2,\"typeSin\":\"hooky\",\"points\":7,\"teacherId\":2,\"schoolboyId\":1}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Url param id is not equals to sinId!"))
                .andReturn();
    }

    @Test
    void testPutOneNotExist() throws Exception {
        mockMvc.perform(put("/sins/7").contentType(APPLICATION_JSON_UTF8).content("{\"id\":7,\"typeSin\":\"hooky\",\"points\":7,\"teacherId\":2,\"schoolboyId\":1}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Проступок не существует!"))
                .andReturn();
    }


    @Test
    void testPutIdNullBadRequest() throws Exception {
        mockMvc.perform(put("/sins/2").contentType(APPLICATION_JSON_UTF8).content("{\"typeSin\":\"hooky\",\"points\":7,\"teacherId\":2,\"schoolboyId\":1}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Url param id is not equals to sinId!"))
                .andReturn();
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(put("/sins/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"typeSin\":\"hooky\",\"points\":7,\"teacherId\":2,\"schoolboyId\":1}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.typeSin").value("hooky"))
                .andReturn();
    }

    @Test
    void testDeleteExist() throws Exception {
        mockMvc.perform(get("/sins/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testDeleteNotExist() throws Exception {
        mockMvc.perform(delete("/sins/5"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Проступок не существует!"))
                .andReturn();
    }
}