package com.ednach.controller;

import com.ednach.Main;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AuthenticationControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testSignInExist() throws Exception {
        mockMvc.perform(post("/authentication/signIn").contentType(APPLICATION_JSON_UTF8).content("{\"email\":\"morozPavel@mail.ru\",\"password\":\"1234\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testSignInNotExist() throws Exception {
        mockMvc.perform(post("/authentication/signIn").contentType(APPLICATION_JSON_UTF8).content("{\"email\":\"morozPavel@mail.ru\",\"password\":\"43212\"}"))
                .andDo(print())
                .andExpect(status().is(500))
                .andReturn();
    }

    @Test
    void testSignUp() throws Exception {
        mockMvc.perform(post("/authentication/signUp").contentType(APPLICATION_JSON_UTF8).content("{\"firstName\":\"Klimko\",\"lastName\":\"Toly\",\"email\":\"klimkoToly@mail.ru\",\"password\":\"43212\",\"roles\":[\"ROLE_PARENT\"]}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Klimko"))
                .andExpect(jsonPath("$.lastName").value("Toly"))
                .andReturn();
    }


}
