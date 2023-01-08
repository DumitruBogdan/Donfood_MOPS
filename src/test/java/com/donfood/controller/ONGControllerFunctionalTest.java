package com.donfood.controller;

import com.donfood.exception.ResourceNotFoundException;
import com.donfood.models.ONGTestModels;
import com.donfood.service.ONGService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityExistsException;
import java.util.Collections;

import static com.donfood.models.ONGTestModels.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ONGController.class)
class ONGControllerFunctionalTest {

    @Autowired
    private Jackson2ObjectMapperBuilder mapperBuilder;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ONGService ongService;


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testCreateONG() throws Exception {

        // Given
        given(ongService.register(any())).willReturn(ONGTestModels.buildONGResponseDTO());

        // When
        this.mockMvc.perform(post("/api/ong/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapperBuilder.build().writeValueAsString(ONGTestModels.buildONGResponseDTO()))
                .with(csrf()))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value("1"))
                .andExpect(jsonPath("$.address").value("Address 1"))
                .andExpect(jsonPath("$.socialScore").value("111.0"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testCreateONGException() throws Exception {

        // Given
        when(ongService.register(any())).thenThrow(new EntityExistsException("ONG already exists"));

        // When
        this.mockMvc.perform(post("/api/ong/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapperBuilder.build().writeValueAsString(ONGTestModels.buildONGResponseDTO()))
                .with(csrf()))
                // Then
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("ONG already exists"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetONGById() throws Exception {

        // Given
        given(ongService.getById(ONG_ID)).willReturn(ONGTestModels.buildONGResponseDTO());

        // When
        this.mockMvc.perform(get("/api/ong/" + ONG_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value("1"))
                .andExpect(jsonPath("$.address").value("Address 1"))
                .andExpect(jsonPath("$.socialScore").value("111.0"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetONGByIdException() throws Exception {

        // Given
        doThrow(new ResourceNotFoundException("The ong with id: " + INVALID_ID + " was not found")).when(ongService).getById(any());

        // When
        this.mockMvc.perform(get("/api/ong/" + INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                // Then
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("The ong with id: " + INVALID_ID + " was not found"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetAllONGs() throws Exception {

        // Given
        given(ongService.getAll()).willReturn(Collections.singletonList(ONGTestModels.buildONGResponseDTO()));

        // When
        this.mockMvc.perform(get("/api/ong")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].accountId").value("1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUpdateONG() throws Exception {

        // Given
        given(ongService.update(any(), any())).willReturn(ONGTestModels.buildONGResponseDTO());

        // When
        this.mockMvc.perform(put("/api/ong/" + ONG_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapperBuilder.build().writeValueAsString(ONGTestModels.buildONGResponseDTO()))
                .with(csrf()))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value("1"))
                .andExpect(jsonPath("$.address").value("Address 1"))
                .andExpect(jsonPath("$.socialScore").value("111.0"));

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUpdateONGException() throws Exception {

        // Given
        doThrow(new ResourceNotFoundException("The ong with id: " + INVALID_ID + " was not found")).when(ongService).update(any(), any());

        // When
        this.mockMvc.perform(put("/api/ong/" + INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapperBuilder.build().writeValueAsString(ONGTestModels.buildONGResponseDTO()))
                .with(csrf()))
                // Then
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("The ong with id: " + INVALID_ID + " was not found"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testDeleteONG() throws Exception {

        // When
        this.mockMvc.perform(delete("/api/ong/" + ONG_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                // Then
                .andExpect(status().isOk());

        verify(ongService).delete(ONG_ID);
    }


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testDeleteONGException() throws Exception {

        // Given
        doThrow(new ResourceNotFoundException("The ong with id: " + INVALID_ID + " was not found")).when(ongService).delete(INVALID_ID);

        // When
        this.mockMvc.perform(delete("/api/ong/" + INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                // Then
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("The ong with id: " + INVALID_ID + " was not found"));
    }
}

