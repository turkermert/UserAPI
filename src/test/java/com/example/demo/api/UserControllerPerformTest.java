package com.example.demo.api;

import com.example.demo.data.UserGenerator;
import com.example.demo.model.Gender;
import com.example.demo.model.User;
import com.example.demo.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
class UserControllerPerformTest {
    private final static String CONTENT_TYPE = "application/json";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserServiceImpl userService;

    // -------------------- ADD USER TESTS -----------------------------------------
    @Test
    void whenValidInput_thenReturn201InAddUserTest() throws Exception {
        //given
        User user = UserGenerator.generateUser(Gender.getRandom(), 6, 4);

        //when
        ResultActions actions = mockMvc.perform(post("/api/v1/user")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsBytes(user)));

        //then
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userService, times(1)).addUser(captor.capture());
        assertEquals(captor.getValue().getName(), user.getName());
        assertEquals(captor.getValue().getSurname(), user.getSurname());
        actions.andExpect(status().isCreated());
    }

    @Test
    void whenInvalidInput_thenReturn400InAddUserTest() throws Exception {
        //given

        //when
        ResultActions actions = mockMvc.perform(post("/api/v1/user")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsBytes("")));

        //then
        actions.andExpect(status().isBadRequest());
    }

    // -------------------- GET ALL USER TEST --------------------------------------

    @Test
    void whenInUsers_thenReturn200InGetAllUserTest() throws Exception {
        //given
        User user = UserGenerator.generateUser(Gender.getRandom(), 6, 4);
        when(userService.getAllUser()).thenReturn(Collections.singletonList(user));

        //when
        ResultActions actions = mockMvc.perform(get("/api/v1/user")
                .accept(CONTENT_TYPE));

        MvcResult mvcResult = actions.andReturn();

        //then
        String responseBody = mvcResult.getResponse().getContentAsString();
        verify(userService, times(1)).getAllUser();
        assertEquals(objectMapper.writeValueAsString(Collections.singletonList(user)), responseBody);
        actions.andExpect(status().isOk());
    }

    @Test
    void whenNoUsers_thenReturn204InGetAllUserTest() throws Exception {
        //given
        when(userService.getAllUser()).thenReturn(Collections.emptyList());

        //when
        ResultActions actions = mockMvc.perform(get("/api/v1/user")
                .accept(CONTENT_TYPE));

        //then
        verify(userService, times(1)).getAllUser();
        actions.andExpect(status().isNoContent());
    }

    // -------------------- GET USER BY ID TEST ------------------------------------

    @Test
    void whenValidId_thenReturn200InGetUserByIdTest() throws Exception {

        //given
        User user = UserGenerator.generateUser(Gender.getRandom(), 6, 4);
        when(userService.getUserById(user.getId())).thenReturn(user);

        //when
        ResultActions actions = mockMvc.perform(get("/api/v1/user/" + user.getId()));
        MvcResult mvcResult = actions.andReturn();

        //then
        verify(userService, times(1)).getUserById(user.getId());
        assertEquals(objectMapper.writeValueAsString(user), mvcResult.getResponse().getContentAsString());
        actions.andExpect(status().isOk());
    }

    @Test
    void whenInvalidId_thenReturn404InGetUserByIdTest() throws Exception {

        //given
        UUID randomId = UUID.randomUUID();
        when(userService.getUserById(randomId)).thenReturn(null);

        //when
        ResultActions actions = mockMvc.perform(get("/api/v1/user/" + randomId));

        //then
        verify(userService, times(1)).getUserById(randomId);
        actions.andExpect(status().isNotFound());
    }

    // -------------------- DELETE USER BY ID TEST ---------------------------------
    @Test
    void whenValidId_thenReturn200InDeleteUserByIdTest() throws Exception {

        //given
        User user = UserGenerator.generateUser(Gender.getRandom(), 6, 4);
        when(userService.getUserById(user.getId())).thenReturn(user);

        //when
        ResultActions actions = mockMvc.perform(delete("/api/v1/user/del/")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsBytes(user.getId())));

        //then
        verify(userService, times(1)).getUserById(user.getId());
        verify(userService, times(1)).deleteUser(user.getId());
        actions.andExpect(status().isOk());
    }

    @Test
    void whenInvalidId_thenReturn404InDeleteUserByIdTest() throws Exception {

        //given
        UUID randomId = UUID.randomUUID();
        when(userService.getUserById(randomId)).thenReturn(null);

        //when
        ResultActions actions = mockMvc.perform(delete("/api/v1/user/del/")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsBytes(randomId)));

        //then
        verify(userService, times(1)).getUserById(randomId);
        actions.andExpect(status().isNotFound());
    }


    // -------------------- UPDATE USER TEST ---------------------------------------
    @Test
    void whenValidId_thenReturn200InUpdateUserTest() throws Exception {

        //given
        User currentUser = UserGenerator.generateUser(Gender.getRandom(), 3, 7);
        User updatedUser = UserGenerator.generateUser(Gender.getRandom(), 6, 4);
        updatedUser.setId(currentUser.getId());

        when(userService.updateUser(currentUser.getId(), updatedUser)).thenReturn(updatedUser);
        when(userService.getUserById(currentUser.getId())).thenReturn(currentUser);

        //when
        ResultActions actions = mockMvc.perform(put("/api/v1/user/update/" + currentUser.getId())
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsBytes(updatedUser)));

        MvcResult mvcResult = actions.andReturn();

        //then
        String responseBody = mvcResult.getResponse().getContentAsString();
        verify(userService, times(1)).getUserById(currentUser.getId());
        verify(userService, times(1)).updateUser(currentUser.getId(), updatedUser);
        assertEquals(objectMapper.writeValueAsString(updatedUser), responseBody);
        actions.andExpect(status().isOk());
    }

    @Test
    void whenInvalidId_thenReturn404InUpdateUserTest() throws Exception {

        //given
        UUID randomUUID = UUID.randomUUID();
        User updatedUser = UserGenerator.generateUser(Gender.getRandom(), 6, 4);

        when(userService.getUserById(randomUUID)).thenReturn(null);

        //when
        ResultActions actions = mockMvc.perform(put("/api/v1/user/update/" + randomUUID)
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsBytes(updatedUser)));

        //then
        verify(userService, times(1)).getUserById(randomUUID);
        verify(userService, times(0)).updateUser(randomUUID, updatedUser);
        actions.andExpect(status().isNotFound());
    }
}