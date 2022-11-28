package com.gusscarros.core.client.validation;

import com.gusscarros.core.address.entity.Address;
import com.gusscarros.core.address.infra.AddressInfra;
import com.gusscarros.core.client.dto.ClientDto;
import com.gusscarros.core.client.exception.InvalidAddressException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static  org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class AddressValidationTest {

    @InjectMocks
    AddressValidation addressValidation;

    @Mock
    AddressInfra addressInfra;

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
        when(addressInfra.validationAdress(address))
                .thenThrow(new InvalidAddressException("CEP invalid"));

        assertThrows(InvalidAddressException.class,
                () -> addressValidation.validator(clientDto) );
    }
}