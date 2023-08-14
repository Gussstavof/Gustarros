package com.gusscarros.core.client.dto.response;

import com.gusscarros.core.address.entity.Address;
import com.gusscarros.core.client.entity.Client;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponse {
    private String name;
    private String cpf;
    private LocalDate birthDate;
    private String creditCard;
    private String gender;
    private boolean status;
    private Address address;

    public ClientResponse(Client client) {
        BeanUtils.copyProperties(client, this);
        this.cpf = client.getCpf().substring(0,3).concat(".***.***-**");
        this.creditCard = client.getCreditCard().substring(12,16);

    }
}
