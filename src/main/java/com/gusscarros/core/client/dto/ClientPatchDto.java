package com.gusscarros.core.client.dto;

import com.gusscarros.core.client.model.Client;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientPatchDto {

    private boolean status;
    @CPF
    private String cpf;

    public ClientPatchDto(Client client) {
        this.cpf = client.getCpf().substring(0,3).concat(".***.***-**");
        this.status = client.isStatus();
    }

    public static ClientPatchDto convert(Client client){
        return new ClientPatchDto(client);
    }
}
