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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes = {TestWebConfiguration.class, DataBaseConfig.class})
@WebAppConfiguration
@Transactional
class DisciplineControllerTest {
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
        mockMvc.perform(get("/disciplines"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nameSubject").value("english"))
                .andExpect(jsonPath("$[1].nameSubject").value("maths"))
                .andReturn();
    }

    @Test
    void getOneExist() throws Exception {
        mockMvc.perform(get("/disciplines/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameSubject").value("english"))
                .andReturn();
    }

    @Test
    public void getOneNotExist() throws Exception {
        mockMvc.perform(get("/disciplines/10"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Discipline doesn't exist!"))
                .andReturn();
    }

    @Test
    public void testSaveExistBadRequest() throws Exception {
        mockMvc.perform(post("/disciplines").contentType(APPLICATION_JSON_UTF8).content("{\"nameSubject\":\"maths\",\"teacherId\":2}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Discipline name is not unique!"))
                .andReturn();
    }
    @Test
    public void testSaveHaveIdBadRequest() throws Exception {
        mockMvc.perform(post("/disciplines/1").contentType(APPLICATION_JSON_UTF8).content("{\"nameSubject\":\"OBJ\",\"teacherId\":2}"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void save()  throws Exception {
        mockMvc.perform(post("/disciplines").contentType(APPLICATION_JSON_UTF8).content("{\"nameSubject\":\"OBJ\",\"teacherId\":2}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameSubject").value("OBJ"))
                .andReturn();
    }


    @Test
    public void testPutOneBadRequest() throws Exception {
        mockMvc.perform(put("/disciplines/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":2,\"nameSubject\":\"OBJ\",\"teacherId\":2}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Url param id is not equals to disciplineId!"))
                .andReturn();
    }

    @Test
    public void testPutOneNotExist() throws Exception {
        mockMvc.perform(put("/disciplines/10").contentType(APPLICATION_JSON_UTF8).content("{\"id\":10,\"nameSubject\":\"OBJ\",\"teacherId\":2}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Discipline doesn't exist!"))
                .andReturn();
    }


    @Test
    public void testPutIdNullBadRequest() throws Exception {
        mockMvc.perform(put("/disciplines/2").contentType(APPLICATION_JSON_UTF8).content("{\"nameSubject\":\"OBJ\",\"teacherId\":2}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Url param id is not equals to disciplineId!"))
                .andReturn();
    }

    @Test
    public void testPutOneExist() throws Exception {
        mockMvc.perform(put("/disciplines/2").contentType(APPLICATION_JSON_UTF8).content("{\"id\":2,\"nameSubject\":\"english\",\"teacherId\":2}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Discipline name is not unique!"))
                .andReturn();
    }

    @Test
    void update()  throws Exception {
        mockMvc.perform(put("/disciplines/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"nameSubject\":\"OBJ\",\"teacherId\":2}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameSubject").value("OBJ"))
                .andReturn();
    }

    @Test
    public void testDeleteExist() throws Exception {
        mockMvc.perform(get("/disciplines/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testDeleteNotExist() throws Exception {
        mockMvc.perform(delete("/disciplines/10"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Discipline doesn't exist!"))
                .andReturn();
    }

}