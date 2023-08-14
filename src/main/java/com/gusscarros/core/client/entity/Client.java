package com.gusscarros.core.client.entity;

import com.gusscarros.core.client.constraints.AgeValidator;
import com.gusscarros.core.address.entity.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
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
    private String cpf;

    @DateTimeFormat(pattern = "yyyy-dd-MM")
    @NotNull
    @AgeValidator
    private LocalDate birthDate;

    @NotBlank
    @CreditCardNumber(message = "Invalid credit card number")
    private String creditCard;

    @NotBlank
    private String gender;

    private boolean status = true;

    private Address address;
}
