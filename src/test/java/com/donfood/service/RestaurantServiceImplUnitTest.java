package com.donfood.service;

import com.donfood.dao.RestaurantRepository;
import com.donfood.domain.Restaurant;
import com.donfood.dto.RestaurantRequestDTO;
import com.donfood.dto.RestaurantResponseDTO;
import com.donfood.exception.ResourceNotFoundException;
import com.donfood.mapper.RestaurantMapper;
import com.donfood.models.RestaurantTestModels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.persistence.EntityExistsException;
import javax.swing.text.html.Option;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.donfood.models.AccountModels.ACCOUNT_ID;
import static com.donfood.models.RestaurantTestModels.INVALID_ID;
import static com.donfood.models.RestaurantTestModels.RESTAURANT_ID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class RestaurantServiceImplUnitTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantServiceImpl restaurantServiceImpl;

    @BeforeEach
    void init() { initMocks(this); }

    @Test
    void testCreateRestaurant() {

        // given
        when(restaurantRepository.save(any())).thenReturn(RestaurantTestModels.buildRestaurant());

        // when
        RestaurantResponseDTO restaurantResponseDTO = restaurantServiceImpl.createRestaurant(RestaurantTestModels.buildRestaurantRequestDTO());

        // then
        assertEquals(1L, restaurantResponseDTO.getAccountId());
        assertEquals("111111", restaurantResponseDTO.getFiscalIdCode());
        assertEquals(111, restaurantResponseDTO.getNrPeopleHelping());
    }

    @Test
    void testCreateRestaurantException() {

        // Given
        RestaurantRequestDTO restaurantRequestDTO = RestaurantRequestDTO.builder().accountId(ACCOUNT_ID).build();
        when(restaurantRepository.existsById(restaurantRequestDTO.getAccountId())).thenReturn(true);

        // When
        EntityExistsException entityExistsException = assertThrows(EntityExistsException.class, () ->
                restaurantServiceImpl.createRestaurant(restaurantRequestDTO));

        // Then
        assertEquals("Restaurant already exists", entityExistsException.getMessage());
    }

    @Test
    void testGetRestaurantById() {

        // given
        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(RestaurantTestModels.buildRestaurant()));

        // when
        RestaurantResponseDTO restaurantResponseDTO = restaurantServiceImpl.getRestaurantById(RESTAURANT_ID);

        // then
        assertEquals(1L, restaurantResponseDTO.getAccountId());
    }

    @Test
    void testGetRestaurantException() {

        // Given
        when(restaurantRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        // When
        ResourceNotFoundException entityExistsException = assertThrows(ResourceNotFoundException.class, () ->
                restaurantServiceImpl.getRestaurantById(INVALID_ID));

        // Then
        assertEquals("The restaurant with id: " + INVALID_ID + " was not found", entityExistsException.getMessage());
    }

    @Test
    void testGetRestaurants() {

        // given
        when(restaurantRepository.findAll()).thenReturn(Collections.singletonList(RestaurantTestModels.buildRestaurant()));

        // when
        List<RestaurantResponseDTO> responseDTOS = restaurantServiceImpl.getAllRestaurants();

        // then
        assertEquals(1, responseDTOS.size());
    }

    @Test
    void testGetRestaurantsException() {

        // Given
        when(restaurantRepository.findAll()).thenReturn(new ArrayList<>());

        // When
        ResourceNotFoundException entityExistsException = assertThrows(ResourceNotFoundException.class, () ->
                restaurantServiceImpl.getAllRestaurants());

        // Then
        assertEquals("There are not restaurants in the database", entityExistsException.getMessage());
    }

    @Test
    void testUpdateRestaurant() {

        // given
        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(RestaurantTestModels.buildRestaurant()));
        RestaurantRequestDTO restaurantRequestDTO = RestaurantTestModels.buildRestaurantRequestDTO();
        restaurantRequestDTO.setFiscalIdCode("9");
        when(restaurantRepository.save(any())).thenReturn(RestaurantMapper.requestDtoToDo(restaurantRequestDTO));

        // when
        RestaurantResponseDTO restaurantResponseDTO = restaurantServiceImpl.updateRestaurant(restaurantRequestDTO);

        // then
        assertEquals("9", restaurantResponseDTO.getFiscalIdCode());
    }

    @Test
    void testUpdateRestaurantException() {

        // Given
        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.empty());

        // When
        ResourceNotFoundException entityExistsException = assertThrows(ResourceNotFoundException.class, () ->
                restaurantServiceImpl.getRestaurantById(INVALID_ID));

        // Then
        assertEquals("The restaurant with id: " + INVALID_ID + " was not found", entityExistsException.getMessage());
    }

    @Test
    void testDeleteRestaurant() {

        // Given
        when(restaurantRepository.existsById(RESTAURANT_ID)).thenReturn(true);

        // When
        restaurantServiceImpl.deleteRestaurant(RESTAURANT_ID);

        // Then
        verify(restaurantRepository, times(1)).deleteById(RESTAURANT_ID);
    }


    @Test
    void testDeleteRestaurantException() {

        // Given
        when(restaurantRepository.existsById(INVALID_ID)).thenReturn(false);

        // When
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () ->
                restaurantServiceImpl.deleteRestaurant(INVALID_ID));

        // Then
        assertEquals("The restaurant with id: " + INVALID_ID + " was not found", resourceNotFoundException.getMessage());
    }

}
