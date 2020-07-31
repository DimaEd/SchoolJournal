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

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class RoleControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles={"ADMIN"})
    void getAll() throws Exception {
        mockMvc.perform(get("/roles"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("ROLE_TEACHER"))
                .andExpect(jsonPath("$[1].name").value("ROLE_PARENT"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    void getOneExist() throws Exception {
        mockMvc.perform(get("/roles/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ROLE_TEACHER"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    void getOneNotExist() throws Exception {
        mockMvc.perform(get("/roles/5"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Role doesn't exist!"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    void testSaveExistBadRequest() throws Exception {
        mockMvc.perform(post("/roles").contentType(APPLICATION_JSON_UTF8).content("{\"name\":\"ROLE_PARENT\"}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Role name is not unique!"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    void testSaveHaveIdBadRequest() throws Exception {
        mockMvc.perform(post("/roles").contentType(APPLICATION_JSON_UTF8).content("{\"id\":3,\"name\":\"ROLE_SUPERTACHER"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    void save() throws Exception {
        mockMvc.perform(post("/roles").contentType(APPLICATION_JSON_UTF8).content("{\"name\":\"ROLE_SUPERBOSS\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ROLE_SUPERBOSS"))
                .andReturn();
    }


    @Test
    @WithMockUser(roles={"ADMIN"})
    void testPutOneBadRequest() throws Exception {
        mockMvc.perform(put("/roles/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":2,\"name\":\"ROLE_BOSS\"}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Url param id is not equals to roleId!"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    void testPutOneNotExist() throws Exception {
        mockMvc.perform(put("/roles/5").contentType(APPLICATION_JSON_UTF8).content("{\"id\":5,\"name\":\"ROLE_BOSS\"}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Role doesn't exist!"))
                .andReturn();
    }


    @Test
    @WithMockUser(roles={"ADMIN"})
    void testPutIdNullBadRequest() throws Exception {
        mockMvc.perform(put("/roles/2").contentType(APPLICATION_JSON_UTF8).content("{\"name\":\"ROLE_SUPERTEACHER\"}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Url param id is not equals to roleId!"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    void testPutOneExist() throws Exception {
        mockMvc.perform(put("/roles/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"name\":\"ROLE_PARENT\"}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Role name is not unique!"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    void update() throws Exception {
        mockMvc.perform(put("/roles/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"name\":\"ROLE_SUPERTEACHER\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ROLE_SUPERTEACHER"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    void testDeleteExist() throws Exception {
        mockMvc.perform(get("/roles/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    void testDeleteNotExist() throws Exception {
        mockMvc.perform(delete("/roles/5"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Role doesn't exist!"))
                .andReturn();
    }
}