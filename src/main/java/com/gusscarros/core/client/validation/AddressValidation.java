package com.gusscarros.core.client.validation;

import com.gusscarros.core.address.repositories.AddressHTTPRepository;
import com.gusscarros.core.client.models.request.ClientRequest;
import com.gusscarros.core.client.exception.InvalidAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AddressValidation implements Validation {

    @Autowired
    AddressHTTPRepository addressHTTPRepository;

    @Override
    public void validator(ClientRequest clientRequest) {

       Optional.ofNullable(clientRequest.setAddress(
               addressHTTPRepository.validationAddress(clientRequest.getAddress())))
               .orElseThrow(() -> new InvalidAddressException("CEP invalid"));
    }
}
