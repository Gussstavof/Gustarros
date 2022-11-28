package com.gusscarros.core.client.dto;

import com.gusscarros.core.client.entity.Client;
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

}
