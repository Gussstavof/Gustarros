package com.gusscarros.core.client.validation;

import com.gusscarros.core.address.entity.Address;
import com.gusscarros.core.address.infra.AddressInfra;
import com.gusscarros.core.client.dto.ClientDto;
import com.gusscarros.core.client.exception.CpfExistException;
import com.gusscarros.core.client.exception.InvalidAddressException;
import com.gusscarros.core.client.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CpfValidationTest {

    @InjectMocks
    CpfValidation cpfValidation;

    @Mock
    ClientRepository clientRepository;

    Address address;
    ClientDto clientDto;

    @BeforeEach
    public void setup(){
        address = Address.builder()
                .cep("03245")
                .numero("277")
                .build();
        clientDto = ClientDto.builder()
                .name("Ferreira")
                .address(address)
                .birthDate(LocalDate.parse("2003-11-12"))
                .cpf("56040769025")
                .creditCard("5245759559334078")
                .gender("masculino")
                .build();
    }

    @Test
    void validator() {
        when(clientRepository.existsByCpf("56040769025"))
                .thenThrow(new CpfExistException("Cpf already exist"));

        assertThrows(CpfExistException.class,
                () -> cpfValidation.validator(clientDto) );
    }
}