package com.donfood.service;

import com.donfood.dao.RestaurantRepository;
import com.donfood.domain.Restaurant;
import com.donfood.dto.RestaurantDTO;
import com.donfood.exception.ResourceNotFoundException;
import com.donfood.mapper.RestaurantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) {
        if (restaurantRepository.existsById(restaurantDTO.getAccountId())) {
            throw new EntityExistsException("Restaurant already exists");
        }
        return RestaurantMapper.doToDto(restaurantRepository.save(RestaurantMapper.dtoToDo(restaurantDTO)));
    }

    @Override
    public RestaurantDTO getRestaurantById(Long id) {
        checkIdIsNull(id);
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (!restaurant.isPresent()) {
            throw new ResourceNotFoundException("The restaurant with id: " + id + " was not found");
        }
        return RestaurantMapper.doToDto(restaurant.get());
    }

    @Override
    public List<RestaurantDTO> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (restaurants.isEmpty()) {
            throw new ResourceNotFoundException("There are not restaurants in the database");
        }
        return RestaurantMapper.dosToDtos(restaurants);
    }

    @Override
    public RestaurantDTO updateRestaurant(RestaurantDTO restaurantDTO) {
        Optional<Restaurant> databaseRestaurant = restaurantRepository.findById(restaurantDTO.getAccountId());
        if (!databaseRestaurant.isPresent()) {
            throw new ResourceNotFoundException("The restaurant with id: " + restaurantDTO.getAccountId() + " was not found");
        }
        return RestaurantMapper.doToDto(restaurantRepository.save(validateRestaurant(databaseRestaurant.get(), restaurantDTO)));
    }

    @Override
    public void deleteRestaurant(Long id) {

        checkIdIsNull(id);
        if (!restaurantRepository.existsById(id)) {
            throw new ResourceNotFoundException("The restaurant with id: " + id + " was not found");
        }
        restaurantRepository.deleteById(id);
    }

    public Restaurant validateRestaurant(Restaurant restaurant, RestaurantDTO restaurantDTO) {
        if (restaurantDTO.getAccountId() != null) {
            restaurant.setAccountId(restaurantDTO.getAccountId());
        }
        if (StringUtils.hasText(restaurantDTO.getFiscalIdCode())) {
            restaurant.setFiscalIdCode(restaurantDTO.getFiscalIdCode());
        }
        if (restaurantDTO.getNrPeopleHelping() != null) {
            restaurant.setNrPeopleHelping(restaurantDTO.getNrPeopleHelping());
        }
        return restaurant;
    }

    public void checkIdIsNull(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("The id is not valid");
        }
    }
}
