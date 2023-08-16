package com.gusscarros.core.address.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String cep;
    private String uf;
    private String localidade;
    private String logradouro;
    private String bairro;
    private String numero;

}
