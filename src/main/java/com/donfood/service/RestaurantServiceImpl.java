package com.donfood.service;

import com.donfood.dao.RestaurantRepository;
import com.donfood.domain.Restaurant;
import com.donfood.dto.RestaurantRequestDTO;
import com.donfood.dto.RestaurantResponseDTO;
import com.donfood.exception.ResourceNotFoundException;
import com.donfood.mapper.AccountMapper;
import com.donfood.mapper.RestaurantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private IAccountService accountService;

    @Override
    @Transactional
    public RestaurantResponseDTO createRestaurant(RestaurantRequestDTO restaurantRequestDTO) {

        if (restaurantRepository.existsById(restaurantRequestDTO.getAccountId())) {
            throw new EntityExistsException("Restaurant already exists");
        }

        Restaurant callRestaurant = RestaurantMapper.requestDtoToDo(restaurantRequestDTO);
        callRestaurant.setAccountRest(accountService.register(restaurantRequestDTO.getAccountRequestDTO()));
        return RestaurantMapper.doToResponseDto(restaurantRepository.save(callRestaurant));
    }

    @Override
    public RestaurantResponseDTO getRestaurantById(Long id) {

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
        if (databaseRestaurant.isEmpty()) {
            throw new ResourceNotFoundException("The restaurant with id: " + restaurantRequestDTO.getAccountId() + " was not found");
        }
        Restaurant updatedRestaurant = validateRestaurant(databaseRestaurant.get(), restaurantRequestDTO);
        Restaurant savedRestaurant = restaurantRepository.save(updatedRestaurant);
        savedRestaurant.setAccountRest(updatedRestaurant.getAccountRest());
        return RestaurantMapper.doToResponseDto(savedRestaurant);
    }

    @Override
    @Transactional
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
            restaurant.setAccountRest(AccountMapper.requestToAccount(restaurantRequestDTO.getAccountRequestDTO()));
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
