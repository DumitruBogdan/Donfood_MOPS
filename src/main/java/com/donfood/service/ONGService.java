package com.donfood.service;

import com.donfood.dao.IONGRepository;
import com.donfood.domain.ONG;
import com.donfood.dto.ONGRequestDTO;
import com.donfood.dto.ONGResponseDTO;
import com.donfood.exception.ResourceNotFoundException;
import com.donfood.mapper.ONGMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ONGService implements IONGService{
    @Autowired
    private IAccountService accountService;

    @Autowired
    private IONGRepository ongRepository;

    @Override
    @Transactional
    public ONGResponseDTO register(ONGRequestDTO ongRequestDTO) {

        if (ongRepository.existsById(ongRequestDTO.getAccountId())) {
            throw new EntityExistsException("ONG already exists");
        }

        ONG ong = ONGMapper.requestToONG(ongRequestDTO);
        ong.setAccountONG(accountService.register(ongRequestDTO.getAccountRequestDTO()));
        return ONGMapper.ONGToResponse(ongRepository.save(ong));
    }

    @Override
    public ONGResponseDTO update(Long id, ONGRequestDTO ongRequestDTO) {

        Optional<ONG> dbOng = ongRepository.findById(id);

        if(dbOng.equals(Optional.empty()))
            throw new ResourceNotFoundException("ONG was not found by id");

        if(ongRequestDTO.getAddress() != null)
            dbOng.get().setAddress(ongRequestDTO.getAddress());

        if(ongRequestDTO.getSocialScore() != null)
            dbOng.get().setSocialScore(ongRequestDTO.getSocialScore());

        if(ongRequestDTO.getAccountRequestDTO() != null)
            dbOng.get().setAccountONG(accountService.update(id, ongRequestDTO.getAccountRequestDTO()));

        return ONGMapper.ONGToResponse(ongRepository.save(dbOng.get()));
    }

    @Override
    @Transactional
    public void delete(Long id) {

        if (id == null)
            throw new IllegalArgumentException("The id is null");
        if (!ongRepository.existsById(id))
            throw new ResourceNotFoundException("The ONG with id " + id + " was not found");
        try{
            ongRepository.deleteById(id); //has delete cascade
        }
        catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Error while deleting resource");
        }
    }

    @Override
    public List<ONGResponseDTO> getAll() {
        List<ONG> ongs =  ongRepository.findAll();
        return ONGMapper.ONGToResponseList(ongs);
    }

    @Override
    public ONGResponseDTO getById(Long id) {
        try{
            ONG ong = ongRepository.getReferenceById(id);
            return ONGMapper.ONGToResponse(ong);
        }
        catch(Exception e){
            throw new ResourceNotFoundException("No ONG with that id.");
        }
    }

    @Override
    public List<ONGResponseDTO> getByFullName(String fullName) {
        List<ONG> ongs = ongRepository.findByAccountONGFullName(fullName);
        //if(ongs.equals(Optional.empty()))
        //    throw new ResourceNotFoundException("ONG not found");
        return ONGMapper.ONGToResponseList(ongs);
    }
}
