package com.gusscarros.core.client.dto;

import com.gusscarros.core.client.model.Client;
import com.gusscarros.core.endereco.model.Adress;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientGetDto {

    private String name;
    private String cpf;
    private LocalDate birthDate;
    private String creditCard;
    private String gender;
    private Adress adress;

    public ClientGetDto(Client client) {
        this.name = client.getName();
        this.cpf = client.getCpf().substring(0,3).concat(".***.***-**");
        this.birthDate = client.getBirthDate();
        this.creditCard = client.getCreditCard().substring(12,16);
        this.gender = client.getGender();
        this.adress = client.getAdress();
    }

    public static List<ClientGetDto> convertListDto(List<Client> clients){
        return clients.stream().map(ClientGetDto::new).collect(Collectors.toList());
    }
}
