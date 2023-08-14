package com.gusscarros.core.client.dto;

import com.gusscarros.core.client.entity.Client;
import com.gusscarros.core.address.entity.Address;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Builder
@Getter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    private String name;
    private String cpf;
    private LocalDate birthDate;
    private String creditCard;
    private String gender;
    private boolean status;
    private Address address;

    public ClientDto(Client client) {
        this.name = client.getName();
        this.cpf = client.getCpf().substring(0,3).concat(".***.***-**");
        this.birthDate = client.getBirthDate();
        this.creditCard = client.getCreditCard().substring(12,16);
        this.gender = client.getGender();
        this.address = client.getAddress();
    }

}
