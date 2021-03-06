package integration.controller;


import com.ednach.config.DataBaseConfig;
import integration.configuration.TestWebConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes = {TestWebConfiguration.class, DataBaseConfig.class})
@WebAppConfiguration
@Transactional
class SchoolboyControllerTest {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/schoolboys"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].user.firstName").value("Moroz"))
                .andExpect(jsonPath("$[1].user.firstName").value("Ednach"))
                .andReturn();
    }

    @Test
    void getOneExist() throws Exception {
        mockMvc.perform(get("/schoolboys/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.firstName").value("Moroz"))
                .andReturn();
    }

    @Test
    void getOneNotExist() throws Exception {
        mockMvc.perform(get("/schoolboys/5"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Schoolboy doesn't exist!"))
                .andReturn();
    }

    @Test
    void testSaveExistBadRequest() throws Exception {
        mockMvc.perform(post("/schoolboys").contentType(APPLICATION_JSON_UTF8).content("{\"userId\":1,\"classroomId\":1}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Schoolboy user is not unique!"))
                .andReturn();
    }

    @Test
    void testSaveHaveIdBadRequest() throws Exception {
        mockMvc.perform(post("/schoolboys/3").contentType(APPLICATION_JSON_UTF8).content("{\"userId\":1,\"classroomId\":1}"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void save() throws Exception {
        mockMvc.perform(post("/schoolboys").contentType(APPLICATION_JSON_UTF8).content("{\"userId\":3,\"classroomId\":1}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.firstName").value("Gausa"))
                .andReturn();
    }


    @Test
    void testPutOneBadRequest() throws Exception {
        mockMvc.perform(put("/schoolboys/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":2,\"userId\":1,\"classroomId\":1}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Url param id is not equals to schoolboyId!"))
                .andReturn();
    }

    @Test
    void testPutOneNotExist() throws Exception {
        mockMvc.perform(put("/schoolboys/5").contentType(APPLICATION_JSON_UTF8).content("{\"id\":5,\"userId\":3,\"classroomId\":1}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Schoolboy doesn't exist!"))
                .andReturn();
    }


    @Test
    void testPutIdNullBadRequest() throws Exception {
        mockMvc.perform(put("/schoolboys/2").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"userId\":3,\"classroomId\":1}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Url param id is not equals to schoolboyId!"))
                .andReturn();
    }

    @Test
    void testPutOneExist() throws Exception {
        mockMvc.perform(put("/schoolboys/2").contentType(APPLICATION_JSON_UTF8).content("{\"id\":2,\"userId\":1,\"classroomId\":1}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Schoolboy user is not unique!"))
                .andReturn();
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(put("/schoolboys/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"userId\":3,\"classroomId\":1}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.firstName").value("Gausa"))
                .andReturn();
    }

    @Test
    void testDeleteExist() throws Exception {
        mockMvc.perform(get("/schoolboys/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testDeleteNotExist() throws Exception {
        mockMvc.perform(delete("/schoolboys/5"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Schoolboy doesn't exist!"))
                .andReturn();
    }
}