package com.donfood.service;

import com.donfood.dao.IAccountRepository;
import com.donfood.dao.RestaurantRepository;
import com.donfood.domain.ONG;
import com.donfood.domain.Restaurant;
import com.donfood.dto.RestaurantRequestDTO;
import com.donfood.dto.RestaurantResponseDTO;
import com.donfood.exception.ResourceNotFoundException;
import com.donfood.mapper.ONGMapper;
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

    @Autowired
    private IAccountService accountService;

    @Override
    public RestaurantResponseDTO createRestaurant(RestaurantRequestDTO restaurantRequestDTO) {

        if (restaurantRepository.existsById(restaurantRequestDTO.getAccountId())) {
            throw new EntityExistsException("Restaurant already exists");
        }

        accountService.register(restaurantRequestDTO.getAccountRequestDTO());

        Restaurant restaurant = RestaurantMapper.requestDtoToDo(restaurantRequestDTO);
        restaurant.setAccountRest(accountService.register(restaurantRequestDTO.getAccountRequestDTO()));
        restaurantRepository.save(restaurant);
        return RestaurantMapper.doToResponseDto(restaurant);
    }

    @Override
    public RestaurantResponseDTO getRestaurantById(Long id) {

        checkIdIsNull(id);
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (!restaurant.isPresent()) {
            throw new ResourceNotFoundException("The restaurant with id: " + id + " was not found");
        }
        return RestaurantMapper.doToResponseDto(restaurant.get());
    }

    @Override
    public List<RestaurantResponseDTO> getAllRestaurants() {

        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (restaurants.isEmpty()) {
            throw new ResourceNotFoundException("There are not restaurants in the database");
        }
        return RestaurantMapper.dosToDtos(restaurants);
    }

    @Override
    public RestaurantResponseDTO updateRestaurant(RestaurantRequestDTO restaurantRequestDTO) {

        Optional<Restaurant> databaseRestaurant = restaurantRepository.findById(restaurantRequestDTO.getAccountId());
        if (!databaseRestaurant.isPresent()) {
            throw new ResourceNotFoundException("The restaurant with id: " + restaurantRequestDTO.getAccountId() + " was not found");
        }
        return RestaurantMapper.doToResponseDto(restaurantRepository.save(validateRestaurant(databaseRestaurant.get(), restaurantRequestDTO)));
    }

    @Override
    public void deleteRestaurant(Long id) {

        checkIdIsNull(id);
        if (!restaurantRepository.existsById(id)) {
            throw new ResourceNotFoundException("The restaurant with id: " + id + " was not found");
        }
        restaurantRepository.deleteById(id);
    }

    private Restaurant validateRestaurant(Restaurant restaurant, RestaurantRequestDTO restaurantRequestDTO) {

        if (restaurantRequestDTO.getAccountId() != null) {
            restaurant.setAccountId(restaurantRequestDTO.getAccountId());
        }
        if (StringUtils.hasText(restaurantRequestDTO.getFiscalIdCode())) {
            restaurant.setFiscalIdCode(restaurantRequestDTO.getFiscalIdCode());
        }
        if (restaurantRequestDTO.getNrPeopleHelping() != null) {
            restaurant.setNrPeopleHelping(restaurantRequestDTO.getNrPeopleHelping());
        }
        return restaurant;
    }

    public void checkIdIsNull(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("The id is not valid");
        }
    }
}
