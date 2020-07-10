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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes = {TestWebConfiguration.class, DataBaseConfig.class})
@WebAppConfiguration
@Transactional

 class AuthenticationControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


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
