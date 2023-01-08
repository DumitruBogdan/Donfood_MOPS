package com.donfood.service;

import com.donfood.dao.IAccountRepository;
import com.donfood.dao.IONGRepository;
import com.donfood.dto.ONGRequestDTO;
import com.donfood.dto.ONGResponseDTO;
import com.donfood.exception.ResourceNotFoundException;
import com.donfood.mapper.ONGMapper;
import com.donfood.models.AccountModels;
import com.donfood.models.ONGTestModels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.persistence.EntityExistsException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.donfood.models.AccountModels.ACCOUNT_ID;
import static com.donfood.models.ONGTestModels.INVALID_ID;
import static com.donfood.models.ONGTestModels.ONG_ID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class ONGServiceImplUnitTest {

    @Mock
    private IONGRepository ongRepository;

    @Mock
    private IAccountRepository accountRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private ONGService ongService;

    @BeforeEach
    void init() { initMocks(this); }

    @Test
    void testCreateONG() {

        // given
        when(ongRepository.save(any())).thenReturn(ONGTestModels.buildONG());
        when(accountService.register(AccountModels.buildAcountRequestDTO())).thenReturn(AccountModels.buildAcount());

        // when
        ONGResponseDTO ongResponseDTO = ongService.register(ONGTestModels.buildONGRequestDTO());

        // then
        assertEquals(1L, ongResponseDTO.getAccountId());
        assertEquals("Address 1", ongResponseDTO.getAddress());
        assertEquals(111.0, ongResponseDTO.getSocialScore());
    }

    @Test
    void testCreateONGException() {

        // Given
        ONGRequestDTO ongRequestDTO = ONGRequestDTO.builder().accountId(ACCOUNT_ID).build();
        when(ongRepository.existsById(ongRequestDTO.getAccountId())).thenReturn(true);

        // When
        EntityExistsException entityExistsException = assertThrows(EntityExistsException.class, () ->
                ongService.register(ongRequestDTO));

        // Then
        assertEquals("ONG already exists", entityExistsException.getMessage());
    }

    @Test
    void testGetONGById() {

        // given
        when(ongRepository.getReferenceById(ONG_ID)).thenReturn(ONGTestModels.buildONG());

        // when
        ONGResponseDTO ongResponseDTO = ongService.getById(ONG_ID);

        // then
        assertEquals(1L, ongResponseDTO.getAccountId());
    }

    @Test
    void testGetONGException() {

        // Given
        when(ongRepository.getReferenceById(INVALID_ID)).thenThrow(ResourceNotFoundException.class);

        // When
        ResourceNotFoundException entityExistsException = assertThrows(ResourceNotFoundException.class, () ->
                ongService.getById(INVALID_ID));

        // Then
        assertEquals("No ONG with that id.", entityExistsException.getMessage());
    }

    @Test
    void testGetONGByFullName() {

        // given
        when(ongRepository.findByAccountONGFullName(any())).thenReturn(Collections.singletonList(ONGTestModels.buildONG()));

        // when
        List<ONGResponseDTO> ongResponseDTOS = ongService.getByFullName("test name");

        // then
        assertEquals(1L, ongResponseDTOS.get(0).getAccountId());
    }

    @Test
    void testGetONGs() {

        // given
        when(ongRepository.findAll()).thenReturn(Collections.singletonList(ONGTestModels.buildONG()));

        // when
        List<ONGResponseDTO> responseDTOS = ongService.getAll();

        // then
        assertEquals(1, responseDTOS.size());
    }

    @Test
    void testUpdateONG() {

        // given
        when(ongRepository.findById(ONG_ID)).thenReturn(Optional.of(ONGTestModels.buildONG()));
        when(accountRepository.findById(ONG_ID)).thenReturn(Optional.of(AccountModels.buildAcount()));
        ONGRequestDTO ongRequestDTO = ONGTestModels.buildONGRequestDTO();
        ongRequestDTO.setSocialScore(9.0);
        when(ongRepository.save(any())).thenReturn(ONGMapper.requestToONG(ongRequestDTO));

        // when
        ONGResponseDTO ongResponseDTO = ongService.update(ONG_ID, ongRequestDTO);

        // then
        assertEquals(9.0, ongResponseDTO.getSocialScore());
    }

    @Test
    void testUpdateONGException() {

        // Given
        when(ongRepository.findById(ONG_ID)).thenReturn(Optional.empty());

        // When
        ResourceNotFoundException entityExistsException = assertThrows(ResourceNotFoundException.class, () ->
                ongService.update(INVALID_ID, any()));

        // Then
        assertEquals("ONG was not found by id", entityExistsException.getMessage());
    }

    @Test
    void testDeleteONG() {

        // Given
        when(ongRepository.existsById(ONG_ID)).thenReturn(true);

        // When
        ongService.delete(ONG_ID);

        // Then
        verify(ongRepository, times(1)).deleteById(ONG_ID);
    }


    @Test
    void testDeleteONGException() {

        // Given
        when(ongRepository.existsById(INVALID_ID)).thenReturn(false);

        // When
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () ->
                ongService.delete(INVALID_ID));

        // Then
        assertEquals("The ONG with id " + INVALID_ID + " was not found", resourceNotFoundException.getMessage());
    }

}
