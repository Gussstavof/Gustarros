package com.gusscarros.core.client.validation;

import com.gusscarros.core.client.dto.ClientDto;
import com.gusscarros.core.client.exception.InvalidAddressException;
import com.gusscarros.core.address.infra.AddressInfra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AddressValidation implements CreateValidation {

    @Autowired
    AddressInfra addressInfra;

    @Override
    public void validator(ClientDto clientDto) {
       Optional.ofNullable((addressInfra.validationAdress(clientDto.getAddress())))
               .orElseThrow(() -> new InvalidAddressException("CEP invalid"));
    }
}
