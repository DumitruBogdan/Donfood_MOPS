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
        /*Account account = accountService.register(ongRequestDTO.getAccountRequestONG());
        ONG ong;
        if (account != null) {
            ongRequestDTO.setAccountId(account.getId());
            ong = ONGMapper.requestToONG(ongRequestDTO);
            try{
                ong.setAccountONG(null);
                ongRepository.save(ong);
            }
            catch(IllegalArgumentException e){
                accountService.delete(ong.getAccountId());
            }
        } else {
            throw new IllegalArgumentException("Could not register account");
        }*/
        AccountRequestDTO account = ongRequestDTO.getAccountRequestONG();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
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
    public ONGResponseDTO login(ONGRequestDTO ongRequestDTO) {
        Account account = accountService.login(ongRequestDTO.getAccountRequestONG());
        ONG ong;
        if (account != null) {
            ong = ongRepository.getReferenceById(account.getId());
        } else
            throw new ResourceNotFoundException("Account not found");
        ONGResponseDTO ongResponseDTO = ONGMapper.ONGToResponse(ong);
        return ongResponseDTO;
    }

    @Override
    public ONGResponseDTO update(ONGRequestDTO ongRequestDTO) {
        Optional<ONG> dbOng = ongRepository.findById(ongRequestDTO.getAccountId());
        if(dbOng.equals(Optional.empty()))
            throw new ResourceNotFoundException("ONG was not found by id");

        ONG ong = ONGMapper.requestToONG(ongRequestDTO);

        Account account = accountService.update(ongRequestDTO.getAccountRequestONG());
        if (account != null) {
            try{
                ongRepository.save(ong);
            }
            catch(IllegalArgumentException e){
                accountService.delete(ong.getAccountId());
            }
        } else {
            throw new IllegalArgumentException("Could not update account");
        }

        ONGResponseDTO ongResponseDTO = ONGMapper.ONGToResponse(ong);
        return ongResponseDTO;
    }

    @Override
    public void delete(Long id) {

        if (id == null)
            throw new IllegalArgumentException("The id is null");
        if (!ongRepository.existsById(id))
            throw new ResourceNotFoundException("The ONG with id: " + id + " was not found");
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
        ONG ong = ongRepository.getReferenceById(id);
        return ONGMapper.ONGToResponse(ong);
    }

    @Override
    public ONGResponseDTO getByFullName(String fullName) {
        Optional<ONG> ong = ongRepository.findByAccountONGFullName(fullName).stream().findFirst();
        if(ong.equals(Optional.empty()))
            throw new ResourceNotFoundException("ONG not found");
        return ONGMapper.ONGToResponse(ong.get());
    }
}
