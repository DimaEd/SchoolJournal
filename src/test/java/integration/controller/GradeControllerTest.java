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
class GradeControllerTest {
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
        mockMvc.perform(get("/grades"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].schoolboy.user.firstName").value("Ednach"))
                .andExpect(jsonPath("$[0].mark").value("10"))
                .andReturn();
    }

    @Test
    void getOneExist() throws Exception {
        mockMvc.perform(get("/grades/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mark").value(10))
                .andReturn();
    }

    @Test
    void getOneNotExist() throws Exception {
        mockMvc.perform(get("/grades/5"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Grade doesn't exist!"))
                .andReturn();
    }

    @Test
    void testSaveHaveIdBadRequest() throws Exception {
        mockMvc.perform(post("/grades/1").contentType(APPLICATION_JSON_UTF8).content("{\"schoolboyId\":1,\"disciplineId\":5,\"mark\":7,\"teacherId\":2,\"date\":\"20.10.2019\"}"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void save()  throws Exception {
        mockMvc.perform(post("/grades").contentType(APPLICATION_JSON_UTF8).content("{\"schoolboyId\":1,\"disciplineId\":5,\"mark\":\"7\",\"teacherId\":2,\"date\":\"20.10.2019\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mark").value("7"))
                .andReturn();
    }


    @Test
     void testPutOneBadRequest() throws Exception {
        mockMvc.perform(put("/grades/2").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"schoolboyId\":1,\"disciplineId\":5,\"mark\":\"7\",\"teacherId\":2,\"date\":\"20.10.2019\"}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Url param id is not equals to gradeId!"))
                .andReturn();
    }

    @Test
    void testPutOneNotExist() throws Exception {
        mockMvc.perform(put("/grades/5").contentType(APPLICATION_JSON_UTF8).content("{\"id\":5,\"schoolboyId\":1,\"disciplineId\":5,\"mark\":\"7\",\"teacherId\":2,\"date\":\"20.10.2019\"}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Grade doesn't exist!"))
                .andReturn();
    }


    @Test
     void testPutIdNullBadRequest() throws Exception {
        mockMvc.perform(put("/grades/1").contentType(APPLICATION_JSON_UTF8).content("{\"schoolboyId\":1,\"disciplineId\":5,\"mark\":\"7\",\"teacherId\":2,\"date\":\"20.10.2019\"}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Url param id is not equals to gradeId!"))
                .andReturn();
    }

    @Test
    void update()  throws Exception {
        mockMvc.perform(put("/grades/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"schoolboyId\":1,\"disciplineId\":5,\"mark\":\"7\",\"teacherId\":2,\"date\":\"20.10.2019\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.schoolboy.user.firstName").value("Moroz"))
                .andReturn();
    }

    @Test
     void testDeleteExist() throws Exception {
        mockMvc.perform(get("/grades/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testDeleteNotExist() throws Exception {
        mockMvc.perform(delete("/grades/5"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Grade doesn't exist!"))
                .andReturn();
    }
}