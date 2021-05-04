package com.example.projecttool.Project.web;

import com.example.projecttool.Project.domain.Project;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;


    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void createNewProject() throws Exception {

        Project project = new Project();
        project.setProjectName("Test");
        project.setProjectIdentifier("TES2");
        project.setDescription("a new project");

//        String jsonResponse = objectMapper.writeValueAsString(project);
//        System.out.println(jsonResponse);
        String prepareJson = "{\"id\":null,\"projectName\":\"Test\",\"projectIdentifier\":\"TES2\",\"description\":\"a new project\",\"start_date\":null,\"end_date\":null,\"created_At\":null,\"updated_At\":null,\"projectLeader\":null}";
        String userJson = "{\"username\":\"johndoe@yahoo.com\",\"password\":\"password\"}";

        RequestBuilder requestLogin = MockMvcRequestBuilders
                .post("/api/users/login")
                .content(userJson)
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json");

//        System.out.println(requestLogin.);
        String resultLogin = mockMvc.perform(requestLogin)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonObject jobj = new Gson().fromJson(resultLogin, JsonObject.class);
        String token = jobj.get("token").toString();
        System.out.println(token);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/project", project)
                .header("Authorization",token)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(content().string(prepareJson))
                .andExpect(status().isCreated())
                .andReturn();

    }

    @Test
    @WithMockUser(username="johndoe@yahoo.com", password ="password")
    void createNewProject_ProjectNameNotBlank() throws Exception {
        Project project = new Project();
        project.setProjectIdentifier("TEST1");
        project.setDescription("a new project");

        String jsonResponse = objectMapper.writeValueAsString(project);
        System.out.println(jsonResponse);
        String prepareJson = "{\"id\":null,\"projectName\":null,\"projectIdentifier\":\"TEST1\",\"description\":\"a new project\",\"start_date\":null,\"end_date\":null,\"created_At\":null,\"updated_At\":null,\"projectLeader\":null}";

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/project", project)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(content().string(prepareJson))
                .andExpect(jsonPath("$.projectName", Is.is("Project name is required")))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

//    "{\"id\": 9,\"projectName\": \"Test\", \"projectIdentifier\": \"TEST1\", \"description\": \"a new project\",\"start_date\": null,\"end_date\": null,\"created_At\": \"2021-42-19\",\"updated_At\": null,\"projectLeader\": \"carlosdoe@yahoo.com\"}"

    @Test
    @WithMockUser(username="johndoe@yahoo.com", password ="password")
    void getProjectById() throws Exception {
        Project project = new Project();
        project.setProjectName("Test");
        project.setProjectIdentifier("TEST1");
        project.setDescription("a new project");
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/{projectId}", "TEST1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.projectIdentifier").value("TEST1"));

    }
//
//    @Test
//    void getAllProjects() {
//    }
//
//    @Test
//    void deleteProject() {
//    }
}