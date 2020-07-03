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
class RoleControllerTest {
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
        mockMvc.perform(get("/roles"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("ROLE_TEACHER"))
                .andExpect(jsonPath("$[1].name").value("ROLE_PARENT"))
                .andReturn();
    }

    @Test
    void getOneExist() throws Exception {
        mockMvc.perform(get("/roles/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ROLE_TEACHER"))
                .andReturn();
    }

    @Test
    public void getOneNotExist() throws Exception {
        mockMvc.perform(get("/roles/5"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Role doesn't exist!"))
                .andReturn();
    }

    @Test
    public void testSaveExistBadRequest() throws Exception {
        mockMvc.perform(post("/roles").contentType(APPLICATION_JSON_UTF8).content("{\"name\":\"ROLE_PARENT\"}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Role name is not unique!"))
                .andReturn();
    }
    @Test
    public void testSaveHaveIdBadRequest() throws Exception {
        mockMvc.perform(post("/roles").contentType(APPLICATION_JSON_UTF8).content("{\"id\":3,\"name\":\"ROLE_SUPERTACHER"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void save()  throws Exception {
        mockMvc.perform(post("/roles").contentType(APPLICATION_JSON_UTF8).content("{\"name\":\"ROLE_SUPERBOSS\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ROLE_SUPERBOSS"))
                .andReturn();
    }


    @Test
    public void testPutOneBadRequest() throws Exception {
        mockMvc.perform(put("/roles/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":2,\"name\":\"ROLE_BOSS\"}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Url param id is not equals to roleId!"))
                .andReturn();
    }

    @Test
    public void testPutOneNotExist() throws Exception {
        mockMvc.perform(put("/roles/5").contentType(APPLICATION_JSON_UTF8).content("{\"id\":5,\"name\":\"ROLE_BOSS\"}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Role doesn't exist!"))
                .andReturn();
    }


    @Test
    public void testPutIdNullBadRequest() throws Exception {
        mockMvc.perform(put("/roles/2").contentType(APPLICATION_JSON_UTF8).content("{\"name\":\"ROLE_SUPERTEACHER\"}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Url param id is not equals to roleId!"))
                .andReturn();
    }

    @Test
    public void testPutOneExist() throws Exception {
        mockMvc.perform(put("/roles/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"name\":\"ROLE_PARENT\"}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Role name is not unique!"))
                .andReturn();
    }

    @Test
    void update()  throws Exception {
        mockMvc.perform(put("/roles/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"name\":\"ROLE_SUPERTEACHER\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ROLE_SUPERTEACHER"))
                .andReturn();
    }

    @Test
    public void testDeleteExist() throws Exception {
        mockMvc.perform(get("/roles/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testDeleteNotExist() throws Exception {
        mockMvc.perform(delete("/roles/5"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Role doesn't exist!"))
                .andReturn();
    }
}