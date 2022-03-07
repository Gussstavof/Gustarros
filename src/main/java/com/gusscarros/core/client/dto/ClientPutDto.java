package com.gusscarros.core.client.dto;

import com.gusscarros.core.client.model.Client;
import com.gusscarros.core.endereco.model.Adress;
import com.gusscarros.core.endereco.validation.AdressValidation;
import lombok.*;
import org.hibernate.validator.constraints.CreditCardNumber;


import javax.validation.constraints.NotBlank;
import java.util.Locale;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientPutDto {

    @NotBlank(message = "Name is empty")
    private String name;

    @NotBlank
    @CreditCardNumber(message = "Invalid credit card number ")
    private String creditCard;

    @AdressValidation
    private Adress adress;

    public Client build(){
        Client client = new Client()
                .setName(this.name.toUpperCase(Locale.ROOT))
                .setCreditCard(this.creditCard)
                .setAdress(this.adress);
        return client;
    }

    public ClientPutDto(Client client) {
        this.name = client.getName();
        this.creditCard = client.getCreditCard().substring(12,16);
        this.adress = client.getAdress();
    }
}
