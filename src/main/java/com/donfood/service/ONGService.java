package com.donfood.service;

import com.donfood.dao.IONGRepository;
import com.donfood.domain.Account;
import com.donfood.domain.ONG;
import com.donfood.dto.AccountRequestDTO;
import com.donfood.dto.ONGRequestDTO;
import com.donfood.dto.ONGResponseDTO;
import com.donfood.exception.ResourceNotFoundException;
import com.donfood.mapper.ONGMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ONGService implements IONGService{
    @Autowired
    private IAccountService accountService;

    @Autowired
    private IONGRepository ongRepository;

    @Override
    public ONGResponseDTO register(ONGRequestDTO ongRequestDTO) {

        AccountRequestDTO account = ongRequestDTO.getAccountRequestONG();
        if(account.getPasswordDecoded() == null)
            throw new IllegalArgumentException("Password cannot be empty");
        account.setAccountVerified(false);
        account.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        account.setAccessRights(1);

        ongRequestDTO.setAccountRequestONG(account);
        ONG ong = ONGMapper.requestToONG(ongRequestDTO);
        ong.setAccountONG(accountService.register(ongRequestDTO.getAccountRequestONG()));
        ongRepository.save(ong);

        ONGResponseDTO ongResponseDTO = ONGMapper.ONGToResponse(ong);
        return ongResponseDTO;
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

        if(ongRequestDTO.getAccountRequestONG() != null)
            dbOng.get().setAccountONG(accountService.update(id, ongRequestDTO.getAccountRequestONG()));

        if(ongRequestDTO.getAccountRequestONG() != null)
            dbOng.get().setAccountONG(accountService.update(id, ongRequestDTO.getAccountRequestONG()));
        ongRepository.save(dbOng.get());
        ONGResponseDTO ongResponseDTO = ONGMapper.ONGToResponse(dbOng.get());

        return ongResponseDTO;
    }

    @Override
    public void delete(Long id) {

        if (id == null)
            throw new IllegalArgumentException("The id is null");
        if (!ongRepository.existsById(id))
            throw new ResourceNotFoundException("The ONG with id " + id + " was not found");
        try{
            accountService.delete(id); //has delete cascade
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
        catch(EntityNotFoundException e){
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
