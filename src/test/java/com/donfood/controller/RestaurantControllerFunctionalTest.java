package com.donfood.controller;

import com.donfood.exception.ResourceNotFoundException;
import com.donfood.models.RestaurantTestModels;
import com.donfood.service.RestaurantServiceImpl;
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

import static com.donfood.models.RestaurantTestModels.INVALID_ID;
import static com.donfood.models.RestaurantTestModels.RESTAURANT_ID;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerFunctionalTest {

    @Autowired
    private Jackson2ObjectMapperBuilder mapperBuilder;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RestaurantServiceImpl restaurantService;


    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    void testCreateRestaurant() throws Exception {

        // Given
        given(restaurantService.createRestaurant(any())).willReturn(RestaurantTestModels.buildRestaurantResponseDTO());

        // When
        this.mockMvc.perform(post("/api/restaurant/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapperBuilder.build().writeValueAsString(RestaurantTestModels.buildRestaurantResponseDTO()))
                .with(csrf()))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value("1"))
                .andExpect(jsonPath("$.fiscalIdCode").value("111111"))
                .andExpect(jsonPath("$.nrPeopleHelping").value("111"));
    }

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    void testCreateRestaurantException() throws Exception {

        // Given
        when(restaurantService.createRestaurant(any())).thenThrow(new EntityExistsException("Restaurant already exists"));

        // When
        this.mockMvc.perform(post("/api/restaurant/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapperBuilder.build().writeValueAsString(RestaurantTestModels.buildRestaurantResponseDTO()))
                .with(csrf()))
                // Then
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Restaurant already exists"));
    }

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    void testGetRestaurantById() throws Exception {

        // Given
        given(restaurantService.getRestaurantById(RESTAURANT_ID)).willReturn(RestaurantTestModels.buildRestaurantResponseDTO());

        // When
        this.mockMvc.perform(get("/api/restaurant/" + RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value("1"))
                .andExpect(jsonPath("$.fiscalIdCode").value("111111"))
                .andExpect(jsonPath("$.nrPeopleHelping").value("111"));
    }

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    void testGetRestaurantByIdException() throws Exception {

        // Given
        doThrow(new ResourceNotFoundException("The restaurant with id: " + INVALID_ID + " was not found")).when(restaurantService).getRestaurantById(any());

        // When
        this.mockMvc.perform(get("/api/restaurant/" + INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                // Then
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("The restaurant with id: " + INVALID_ID + " was not found"));
    }

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    void testGetAllRestaurants() throws Exception {

        // Given
        given(restaurantService.getAllRestaurants()).willReturn(Collections.singletonList(RestaurantTestModels.buildRestaurantResponseDTO()));

        // When
        this.mockMvc.perform(get("/api/restaurant")
                .contentType(MediaType.APPLICATION_JSON)
                 .with(csrf()))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].accountId").value("1"));
    }

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    void testUpdateRestaurant() throws Exception {

        // Given
        given(restaurantService.updateRestaurant(any())).willReturn(RestaurantTestModels.buildRestaurantResponseDTO());

        // When
        this.mockMvc.perform(put("/api/restaurant/" + RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapperBuilder.build().writeValueAsString(RestaurantTestModels.buildRestaurantResponseDTO()))
                .with(csrf()))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value("1"))
                .andExpect(jsonPath("$.fiscalIdCode").value("111111"))
                .andExpect(jsonPath("$.nrPeopleHelping").value("111"));

    }

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    void testUpdateRestaurantException() throws Exception {

        // Given
        doThrow(new ResourceNotFoundException("The restaurant with id: " + INVALID_ID + " was not found")).when(restaurantService).updateRestaurant(any());

        // When
        this.mockMvc.perform(put("/api/restaurant/" + INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapperBuilder.build().writeValueAsString(RestaurantTestModels.buildRestaurantResponseDTO()))
                .with(csrf()))
                // Then
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("The restaurant with id: " + INVALID_ID + " was not found"));
    }

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    void testDeleteRestaurant() throws Exception {

        // When
        this.mockMvc.perform(delete("/api/restaurant/" + RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                // Then
                .andExpect(status().isOk());

        verify(restaurantService).deleteRestaurant(RESTAURANT_ID);
    }


    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    void testDeleteRestaurantException() throws Exception {

        // Given
        doThrow(new ResourceNotFoundException("The restaurant with id: " + INVALID_ID + " was not found")).when(restaurantService).deleteRestaurant(INVALID_ID);

        // When
        this.mockMvc.perform(delete("/api/restaurant/" + INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                // Then
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("The restaurant with id: " + INVALID_ID + " was not found"));
    }
}

