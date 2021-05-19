//package com.example.projecttool.Project.web;
//
//import com.example.projecttool.Project.domain.Project;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.jayway.jsonpath.JsonPath;
//import org.hamcrest.core.Is;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.util.Assert;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class ProjectControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @Autowired
//    private MockMvc mvc;
//
//    @BeforeEach
//    public void setup() {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }
//
//
//    @Test
//    String testLogin() throws Exception {
//        String userJson = "{\"username\":\"johndoe@yahoo.com\",\"password\":\"password\"}";
//
//        RequestBuilder requestLogin = MockMvcRequestBuilders
//                .post("/api/users/login")
//                .content(userJson)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType("application/json");
//
//        String resultLogin = mockMvc.perform(requestLogin)
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        JsonObject jobj = new Gson().fromJson(resultLogin, JsonObject.class);
//        String token = jobj.get("token").toString();
//        token=token.replaceAll("\"","");
//        return token;
//    }
//
//
//    @Test
//    @Order(1)
//    void createNewProject() throws Exception {
//        Project project = new Project();
//        project.setProjectName("Test");
//        project.setProjectIdentifier("TES1");
//        project.setDescription("a new project");
//
//        String prepareJson = "{\"projectName\":\"Test\",\"projectIdentifier\":\"TES1\",\"description\":\"a new project\"}";
//
//        String token = testLogin();
//        System.out.println(token);
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .post("/api/project")
//                .header("Authorization",token)
//                .content(new Gson().toJson(project))
//                .contentType("application/json")
//                .accept(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(request)
//                .andDo(mvcResult -> {
//                    String json = mvcResult.getResponse().getContentAsString();
//                    String projectName = JsonPath.parse(json).read("$.projectName").toString();
//                    String projectIdentifier = JsonPath.parse(json).read("$.projectIdentifier").toString();
//                    String description = JsonPath.parse(json).read("$.description").toString();
//                    Assert.isTrue("Test".equals(projectName));
//                    Assert.isTrue("TES1".equals(projectIdentifier));
//                    Assert.isTrue("a new project".equals(description));
//                })
//                .andExpect(status().isCreated())
//                .andReturn();
//    }
//
//
//    @Test
//    void createNewProject_ProjectAlreadyExists() throws Exception {
//        Project project = new Project();
//        project.setProjectName("Test");
//        project.setProjectIdentifier("JWT1");
//        project.setDescription("a new project");
//
//        String prepareJson = "{\"projectIdentifier\":\"Project ID 'JWT1' already exists\"}";
//
//        String token = testLogin();
//        System.out.println(token);
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .post("/api/project")
//                .header("Authorization",token)
//                .content(new Gson().toJson(project))
//                .contentType("application/json")
//                .accept(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(request)
//                .andExpect(content().string(prepareJson))
//                .andExpect(jsonPath("$.projectIdentifier", Is.is("Project ID 'JWT1' already exists")))
//                .andExpect(status().isBadRequest())
//                .andReturn();
//
//    }
//
//
//    @Test
//    void createNewProject_ProjectNameNotBlank() throws Exception {
//        Project project = new Project();
//        project.setProjectIdentifier("TEST1");
//        project.setDescription("a new project");
//
//        String prepareJson = "{\"projectName\":\"Project name is required\"}";
//
//        String token=testLogin();
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .post("/api/project")
//                .header("Authorization",token)
//                .content(new Gson().toJson(project))
//                .contentType("application/json")
//                .accept(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(request)
//                .andExpect(content().string(prepareJson))
//                .andExpect(jsonPath("$.projectName", Is.is("Project name is required")))
//                .andExpect(status().isBadRequest())
//                .andReturn();
//    }
//
//
//    @Test
//    void createNewProject_ProjectIdentifierNotBlank() throws Exception {
//        Project project = new Project();
//        project.setProjectName("TEST");
//        project.setDescription("a new project");
//
//        String prepareJson = "{\"projectIdentifier\":\"Project Identifier is required\"}";
//
//        String token=testLogin();
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .post("/api/project")
//                .header("Authorization",token)
//                .content(new Gson().toJson(project))
//                .contentType("application/json")
//                .accept(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(request)
//                .andExpect(content().string(prepareJson))
//                .andExpect(jsonPath("$.projectIdentifier", Is.is("Project Identifier is required")))
//                .andExpect(status().isBadRequest())
//                .andReturn();
//    }
//
//
//    @Test
//    void createNewProject_DescriptionNotBlank() throws Exception {
//        Project project = new Project();
//        project.setProjectName("TEST");
//        project.setProjectIdentifier("TEST1");
//
//        String prepareJson = "{\"description\":\"Project description is not required\"}";
//
//        String token=testLogin();
//
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .post("/api/project")
//                .header("Authorization",token)
//                .content(new Gson().toJson(project))
//                .contentType("application/json")
//                .accept(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(request)
//                .andExpect(content().string(prepareJson))
//                .andExpect(jsonPath("$.description", Is.is("Project description is not required")))
//                .andExpect(status().isBadRequest())
//                .andReturn();
//    }
//
//
//    @Test
//    void createNewProject_ProjectIdentifierSize() throws Exception {
//        Project project = new Project();
//        project.setProjectName("TEST");
//        project.setProjectIdentifier("TES");
//        project.setDescription("a new project");
//
//        String prepareJson = "{\"projectIdentifier\":\"Please use 4 to 5 characters\"}";
//
//        String token=testLogin();
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .post("/api/project")
//                .header("Authorization",token)
//                .content(new Gson().toJson(project))
//                .contentType("application/json")
//                .accept(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(request)
//                .andExpect(content().string(prepareJson))
//                .andExpect(jsonPath("$.projectIdentifier", Is.is("Please use 4 to 5 characters")))
//                .andExpect(status().isBadRequest())
//                .andReturn();
//    }
//
//
//    @Test
//    @Order(2)
//    void getProjectById() throws Exception {
//        String prepareJson = "{\"id\":1,\"projectName\":\"Test\",\"projectIdentifier\":\"JWT1\",\"description\":\"a new project\",\"start_date\":null,\"end_date\":null,\"created_At\":\"2021-25-24\",\"updated_At\":null,\"projectLeader\":\"johndoe@yahoo.com\"}";
//        String token=testLogin();
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .get("/api/project/{projectId}", "JWT1")
//                .header("Authorization",token)
//                .contentType("application/json")
//                .accept(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(request)
//                .andExpect(content().string(prepareJson))
//                .andExpect(status().isOk())
//                .andReturn();
//    }
//
//
//    @Test
//    @Order(4)
//    void getAllProjects() throws Exception {
//        String prepareJson = "[{\"id\":1,\"projectName\":\"Test\",\"projectIdentifier\":\"JWT1\",\"description\":\"a new project\",\"start_date\":null,\"end_date\":null,\"created_At\":\"2021-25-24\",\"updated_At\":null,\"projectLeader\":\"johndoe@yahoo.com\"}]";
//        String token=testLogin();
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .get("/api/project/all")
//                .header("Authorization",token)
//                .contentType("application/json")
//                .accept(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(request)
//                .andExpect(content().string(prepareJson))
//                .andReturn();
//    }
//
//    @Test
//    @Order(3)
//    void deleteProject() throws Exception {
//        String token=testLogin();
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .delete("/api/project/{projectId}", "TES1")
//                .header("Authorization",token)
//                .contentType("application/json")
//                .accept(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andReturn();
//    }
//}