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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes = {TestWebConfiguration.class, DataBaseConfig.class})
@WebAppConfiguration
@Transactional
class ClassroomControllerTest {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
     void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/classrooms"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].className").value("11B"))
                .andExpect(jsonPath("$[1].className").value("10A"))
                .andReturn();
    }

    @Test
    void getOne() throws Exception {
        mockMvc.perform(get("/classrooms/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.className").value("10A"))
                .andReturn();
    }

    @Test
     void testGetOneNotExist() throws Exception {
        mockMvc.perform(get("/classrooms/3"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Classroom doesn't exist!"))
                .andReturn();
    }

    @Test
     void testSaveExistBadRequest() throws Exception {
        mockMvc.perform(post("/classrooms").contentType(APPLICATION_JSON_UTF8).content("{\"className\":\"10A\",\"teacherId\":1}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Classroom name is not unique!"))
                .andReturn();
    }
    @Test
     void testSaveHaveIdBadRequest() throws Exception {
        mockMvc.perform(post("/classrooms/3").contentType(APPLICATION_JSON_UTF8).content("{\"id\":3,\"className\":\"10A\",\"teacherId\":1}"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void save()  throws Exception {
        mockMvc.perform(post("/classrooms").contentType(APPLICATION_JSON_UTF8).content("{\"className\":\"7A\",\"teacherId\":2}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.className").value("7A"))
                .andReturn();
    }

    @Test
    void testPutOneBadRequest() throws Exception {
        mockMvc.perform(put("/classrooms/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":2,\"className\":\"7A\",\"teacherId\":2}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Url param id is not equals to classroomId!"))
                .andReturn();
    }

    @Test
    void testPutOneNotExist() throws Exception {
        mockMvc.perform(put("/classrooms/5").contentType(APPLICATION_JSON_UTF8).content("{\"id\":5,\"className\":\"7A\",\"teacherId\":2}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Classroom doesn't exist!"))
                .andReturn();
    }


    @Test
     void testPutIdNullBadRequest() throws Exception {
        mockMvc.perform(put("/classrooms/2").contentType(APPLICATION_JSON_UTF8).content("{\"className\":\"7A\",\"teacherId\":2}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Url param id is not equals to classroomId!"))
                .andReturn();
    }

    @Test
     void testPutOneExist() throws Exception {
        mockMvc.perform(put("/classrooms/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"className\":\"11B\",\"teacherId\":2}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Classroom name is not unique!"))
                .andReturn();
    }

    @Test
    void update()  throws Exception {
        mockMvc.perform(put("/classrooms/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"className\":\"7B\",\"teacherId\":2}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.className").value("7B"))
                .andReturn();
    }
    @Test
     void testDeleteExist() throws Exception {
        mockMvc.perform(get("/classrooms/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
     void testDeleteNotExist() throws Exception {
        mockMvc.perform(delete("/classrooms/5"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Classroom doesn't exist!"))
                .andReturn();
    }
}