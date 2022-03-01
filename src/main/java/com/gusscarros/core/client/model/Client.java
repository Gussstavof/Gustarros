package com.gusscarros.core.client.model;

import com.gusscarros.core.endereco.model.Adress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Document(collection = "user")
public class Client {


    @Id
    private  String id;

    @NotBlank(message = "Name is empty")
    private String name;

    @CPF(message = "invalid CPF")
    private String cpf;

    @DateTimeFormat(pattern = "yyyy-dd-MM")
    @NotNull
    private LocalDate birthDate;

    @NotBlank
    @CreditCardNumber(message = "Invalid credit card number")
    private String creditCard;

    @NotBlank
    private String gender;

    private boolean status = true;

    private Adress adress;

    public Client(String name, String cpf, LocalDate birthDate, String creditCard, String gender, Adress adress) {
        this.name = name;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.creditCard = creditCard;
        this.gender = gender;
        this.adress = adress;
    }

    public Client(String name,String creditCard, Adress adress) {
        this.name = name;
        this.creditCard = creditCard;
        this.adress = adress;
    }


}
