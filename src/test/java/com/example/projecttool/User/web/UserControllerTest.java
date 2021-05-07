package com.example.projecttool.User.web;

import com.example.projecttool.ProjectTask.domain.ProjectTask;
import com.example.projecttool.User.domain.User;
import com.google.gson.Gson;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

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
    void registerUser() throws Exception {
        User user = new User();
        user.setUsername("test@yahoo.com");
        user.setFullName("test");
        user.setPassword("password");
        user.setConfirmPassword("password");

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/users/register")
                .content(new Gson().toJson(user))
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    String username = JsonPath.parse(json).read("$.username").toString();
                    String fullName = JsonPath.parse(json).read("$.fullName").toString();
                    String password = JsonPath.parse(json).read("$.password").toString();
                    Assert.isTrue("test@yahoo.com".equals(username));
                    Assert.isTrue("test".equals(fullName));
                    Assert.isTrue("$2a$10$sVxV39MVqXzi9fuw0kHdqezftwTaxlAPCdFr7IFArX.p5.lxrCuN2".equals(password));
                })
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void registerUser_userAlreadyExists() throws Exception {
        User user = new User();
        user.setUsername("test@yahoo.com");
        user.setFullName("test");
        user.setPassword("password");
        user.setConfirmPassword("password");

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/users/register")
                .content(new Gson().toJson(user))
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    String username = JsonPath.parse(json).read("$.username").toString();
                    Assert.isTrue("Username 'test@yahoo.com' already exists.".equals(username));
                })
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void registerUser_usernameNotEmail() throws Exception {
        User user = new User();
        user.setUsername("testyahoo.com");
        user.setFullName("test");
        user.setPassword("password");
        user.setConfirmPassword("password");

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/users/register")
                .content(new Gson().toJson(user))
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    String username = JsonPath.parse(json).read("$.username").toString();
                    Assert.isTrue("Username needs to be an email.".equals(username));
                })
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void registerUser_usernameNotBlank() throws Exception {
        User user = new User();
        user.setFullName("test");
        user.setPassword("password");
        user.setConfirmPassword("password");

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/users/register")
                .content(new Gson().toJson(user))
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    String username = JsonPath.parse(json).read("$.username").toString();
                    Assert.isTrue("Username is required.".equals(username));
                })
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void registerUser_fullNameNotBlank() throws Exception {
        User user = new User();
        user.setUsername("test@yahoo.com");
        user.setPassword("password");
        user.setConfirmPassword("password");

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/users/register")
                .content(new Gson().toJson(user))
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    String fullName = JsonPath.parse(json).read("$.fullName").toString();
                    Assert.isTrue("Please enter your full name.".equals(fullName));
                })
                .andExpect(status().isBadRequest())
                .andReturn();
    }

//    @Test
//    void authenticateUser() {
//    }


}