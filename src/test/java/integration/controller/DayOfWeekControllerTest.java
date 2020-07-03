package integration.controller;

import com.ednach.config.DataBaseConfig;
import integration.configuration.TestWebConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes = {TestWebConfiguration.class, DataBaseConfig.class})
@WebAppConfiguration
@Transactional
class DayOfWeekControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/dayOfWeek"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dayOfWeek").value("monday"))
                .andExpect(jsonPath("$[1].dayOfWeek").value("tuesday"))
                .andReturn();
    }

    @Test
    void getOneExist() throws Exception {
        mockMvc.perform(get("/dayOfWeek/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dayOfWeek").value("monday"))
                .andReturn();
    }

    @Test
    public void getOneNotExist() throws Exception {
        mockMvc.perform(get("/dayOfWeek/6"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Day of the week doesn't exist;"))
                .andReturn();
    }
}