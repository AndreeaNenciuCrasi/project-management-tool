package com.example.projecttool.Backlog.web;

import com.example.projecttool.Project.domain.Project;
import com.example.projecttool.ProjectTask.domain.ProjectTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BacklogControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    String testLogin() throws Exception {
        String userJson = "{\"username\":\"johndoe@yahoo.com\",\"password\":\"password\"}";

        RequestBuilder requestLogin = MockMvcRequestBuilders
                .post("/api/users/login")
                .content(userJson)
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json");

        String resultLogin = mockMvc.perform(requestLogin)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonObject jobj = new Gson().fromJson(resultLogin, JsonObject.class);
        String token = jobj.get("token").toString();
        token=token.replaceAll("\"","");
        return token;
    }


    @Test
    void addPTtoBacklog() throws Exception {
        ProjectTask task = new ProjectTask();
        task.setSummary("first task");

//        String prepareJson = "{\"summary\":\"first task\"}";

        String token = testLogin();
        System.out.println(token);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/backlog/{backlog_id}", "JWT1")
                .header("Authorization",token)
                .content(new Gson().toJson(task))
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    String projectName = JsonPath.parse(json).read("$.summary").toString();
                    Assert.isTrue("first task".equals(projectName));
                })
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void getProjectBacklog() throws Exception {
        String prepareJson = "[{\"id\":15,\"projectSequence\":\"JWT1-7\",\"summary\":\"first task\",\"acceptanceCriteria\":null,\"status\":\"TO_DO\",\"priority\":3,\"dueDate\":null,\"projectIdentifier\":\"JWT1\",\"create_At\":\"2021-12-06\",\"update_At\":null}]";
        String token=testLogin();

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/backlog/{backlog_id}", "JWT1")
                .header("Authorization",token)
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(content().string(prepareJson))
                .andReturn();
    }

    @Test
    void getProjectTask() throws Exception {
        String prepareJson = "{\"id\":15,\"projectSequence\":\"JWT1-7\",\"summary\":\"first task\",\"acceptanceCriteria\":null,\"status\":\"TO_DO\",\"priority\":3,\"dueDate\":null,\"projectIdentifier\":\"JWT1\",\"create_At\":\"2021-12-06\",\"update_At\":null}";
        String token=testLogin();

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/backlog/{backlog_id}/{pt_id}", "JWT1", "JWT1-7")
                .header("Authorization",token)
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(content().string(prepareJson))
                .andExpect(status().isOk())
                .andReturn();
    }

//    @Test
//    void updateProjectTask() {
//    }
//
//    @Test
//    void deleteProjectTask() {
//    }
}