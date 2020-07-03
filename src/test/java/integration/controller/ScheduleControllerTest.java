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
class ScheduleControllerTest {
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
        mockMvc.perform(get("/schedule"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].classroom.className").value("10A"))
                .andExpect(jsonPath("$[1].classroom.className").value("11B"))
                .andReturn();
    }

    @Test
    void getOneExist() throws Exception {
        mockMvc.perform(get("/schedule/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.classroom.className").value("10A"))
                .andReturn();
    }

    @Test
    public void getOneNotExist() throws Exception {
        mockMvc.perform(get("/schedule/7"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Schedule doesn't exist!"))
                .andReturn();
    }

    @Test
    public void testSaveHaveIdBadRequest() throws Exception {
        mockMvc.perform(post("/schedule/2").contentType(APPLICATION_JSON_UTF8).content("{\"classroomId\":1,\"disciplineId\":2,\"dayOfWeekId\":3}"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void save()  throws Exception {
        mockMvc.perform(post("/schedule").contentType(APPLICATION_JSON_UTF8).content("{\"classroomId\":1,\"disciplineId\":2,\"dayOfWeekId\":3}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.classroom.className").value("10A"))
                .andReturn();
    }


    @Test
    public void testPutOneBadRequest() throws Exception {
        mockMvc.perform(put("/schedule/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":2,\"classroomId\":1,\"disciplineId\":2,\"dayOfWeekId\":3}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Url param id is not equals to scheduleId!"))
                .andReturn();
    }

    @Test
    public void testPutOneNotExist() throws Exception {
        mockMvc.perform(put("/schedule/10").contentType(APPLICATION_JSON_UTF8).content("{\"id\":10,\"classroomId\":1,\"disciplineId\":2,\"dayOfWeekId\":3}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Schedule doesn't exist!"))
                .andReturn();
    }


    @Test
    public void testPutIdNullBadRequest() throws Exception {
        mockMvc.perform(put("/schedule/1").contentType(APPLICATION_JSON_UTF8).content("{\"classroomId\":1,\"disciplineId\":2,\"dayOfWeekId\":3}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Url param id is not equals to scheduleId!"))
                .andReturn();
    }

    @Test
    void update()  throws Exception {
        mockMvc.perform(put("/schedule/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"classroomId\":1,\"disciplineId\":2,\"dayOfWeekId\":3}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.classroom.className").value("10A"))
                .andReturn();
    }

    @Test
    public void testDeleteExist() throws Exception {
        mockMvc.perform(get("/schedule/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testDeleteNotExist() throws Exception {
        mockMvc.perform(delete("/schedule/5"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Schedule doesn't exist!"))
                .andReturn();
    }
}