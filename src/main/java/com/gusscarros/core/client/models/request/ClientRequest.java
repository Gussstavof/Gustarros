package com.gusscarros.core.client.models.request;

import com.gusscarros.core.address.entity.Address;
import com.gusscarros.core.client.models.entity.Client;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Builder
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {
    private String name;
    private String cpf;
    private LocalDate birthDate;
    private String creditCard;
    private String gender;
    private boolean status;
    private Address address;

    public Client toClient(){
        Client client = new Client();
        BeanUtils.copyProperties(this, client);
        return client;
    }
}
