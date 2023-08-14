package com.gusscarros.core.client.validation;

import com.gusscarros.core.client.models.request.ClientRequest;
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
    public void validator(ClientRequest clientRequest) {

       Optional.ofNullable((clientRequest
                       .setAddress(addressInfra.validationAdress(clientRequest.getAddress())))
               )
               .orElseThrow(() -> new InvalidAddressException("CEP invalid"));
    }
}
