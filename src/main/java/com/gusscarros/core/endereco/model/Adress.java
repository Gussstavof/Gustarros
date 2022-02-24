package com.gusscarros.core.endereco.model;

import lombok.Data;

@Data
public class Adress {

    private String cep;
    private String uf;
    private String localidade;
    private String logradouro;
    private String bairro;
    private String numero;


}
