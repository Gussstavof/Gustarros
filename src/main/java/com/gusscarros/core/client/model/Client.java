package com.gusscarros.core.client.model;

import com.gusscarros.core.client.validation.AgeValidation;
import com.gusscarros.core.client.validation.CpfValidation;
import com.gusscarros.core.endereco.model.Adress;
import com.gusscarros.core.endereco.validation.AdressValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Document(collection = "user")
public class Client {


    @Id
    private  String id;

    @NotBlank(message = "Name is empty")
    private String name;

    @CPF(message = "invalid CPF")
    @CpfValidation
    private String cpf;

    @DateTimeFormat(pattern = "yyyy-dd-MM")
    @NotNull
    @AgeValidation
    private LocalDate birthDate;

    @NotBlank
    @CreditCardNumber(message = "Invalid credit card number")
    private String creditCard;

    @NotBlank
    private String gender;

    private boolean status = true;

    @AdressValidation
    private Adress adress;

}
