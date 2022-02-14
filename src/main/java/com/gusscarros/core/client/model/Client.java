package com.gusscarros.core.client.model;

import com.gusscarros.core.endereco.model.Adress;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Document(collection = "user")
public class Client {


    @Id
    private  String id;

    @NotBlank(message = "Nome inválido")
    private String name;

    @CPF(message = "CPF inválido")
    private String cpf;

    @DateTimeFormat(pattern = "yyyy-dd-MM")
    @NotNull
    private LocalDate birthDate;

    @NotBlank
    @CreditCardNumber(message = "Número Do cartão de crédito Inválido")
    private String creditCard;

    @NotBlank
    private String gender;

    @NotNull
    private boolean status;

    private Adress adress;
}
