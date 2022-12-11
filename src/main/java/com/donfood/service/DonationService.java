package com.donfood.service;

import com.donfood.dao.IDonationRepository;
import com.donfood.dao.RestaurantRepository;
import com.donfood.domain.Donation;
import com.donfood.dto.DonationRequestDTO;
import com.donfood.exception.ResourceNotFoundException;
import com.donfood.mapper.DonationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DonationService implements IDonationService {
    @Autowired
    private IDonationRepository donationRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public Donation create(DonationRequestDTO donationRequestDTO) {

        if(donationRequestDTO.getRestaurantId() == null)
           throw new IllegalArgumentException("Donation must be assigned to a restaurant");

        if(restaurantRepository.existsById(donationRequestDTO.getRestaurantId()) == false)
            throw new IllegalArgumentException("Restaurant provided does not exist.");

        if(donationRequestDTO.getExpirationDate() == null)
            throw new IllegalArgumentException("Provide an expiration date.");

        if(donationRequestDTO.getQuantity() == null || donationRequestDTO.getQuantity() <= 0)
            throw new IllegalArgumentException("Provide a quantity.");

        if(donationRequestDTO.getQuantityMeasure() == null)
            throw new IllegalArgumentException("Provide a quantity measure.");

        if(donationRequestDTO.getProduct() == null)
            throw new IllegalArgumentException("Provide a product name.");

        if(donationRequestDTO.getPickUpLocation() == null)
            throw new IllegalArgumentException("Provide a pickup location.");

        if(donationRequestDTO.getPickUpTime() == null)
            throw new IllegalArgumentException("Provide a pickup time.");

        donationRequestDTO.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        donationRequestDTO.setModifiedAt(Timestamp.valueOf(LocalDateTime.now()));
        Donation donation = DonationMapper.requestToDonation(donationRequestDTO);
        return donationRepository.save(donation);
    }

    @Override
    public List<Donation> getAll() {
        return donationRepository.findAll();
    }

    @Override
    public List<Donation> findByProduct(String product) {
        return donationRepository.findByProduct(product);
    }

    @Override
    public Donation getById(Long id) {
        if (id == null)
            throw new IllegalArgumentException("The id is null");
        try{
            Donation donation= donationRepository.findById(id).get();
            return donation;
        }
        catch(NoSuchElementException e){
            throw new ResourceNotFoundException("No donation with that id.");
        }
    }

    @Override
    public Donation update(Long id, DonationRequestDTO donationRequestDTO) {
        if (!donationRepository.existsById(id))
            throw new ResourceNotFoundException("The donation with id " + id + " was not found");

        Donation donation = donationRepository.getReferenceById(id);

        if(donationRequestDTO.getExpirationDate() != null)
            donation.setExpirationDate(donationRequestDTO.getExpirationDate());

        if(donationRequestDTO.getQuantity() != null)
            if(donationRequestDTO.getQuantity() <= 0)
                throw new IllegalArgumentException("Provide a quantity.");
            else
                donation.setQuantity(donationRequestDTO.getQuantity());

        if(donationRequestDTO.getQuantityMeasure() != null)
            donation.setQuantityMeasure(donationRequestDTO.getQuantityMeasure());

        if(donationRequestDTO.getProduct() != null)
            donation.setProduct(donationRequestDTO.getProduct());

        if(donationRequestDTO.getPickUpLocation() != null)
            donation.setPickUpLocation(donationRequestDTO.getPickUpLocation());

        if(donationRequestDTO.getPickUpTime() != null)
            donation.setPickUpTime(donationRequestDTO.getPickUpTime());

        donation.setModifiedAt(Timestamp.valueOf(LocalDateTime.now()));
        return donationRepository.save(donation);
    }

    @Override
    public void delete(Long id) {
        if (id == null)
            throw new IllegalArgumentException("The id is null");
        if (!donationRepository.existsById(id))
            throw new ResourceNotFoundException("The donation with id " + id + " was not found");
        try{
            donationRepository.deleteById(id); //has delete cascade
        }
        catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Error while deleting resource");
        }
    }
}

