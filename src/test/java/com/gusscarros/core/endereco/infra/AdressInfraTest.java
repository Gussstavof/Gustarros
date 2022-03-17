package com.gusscarros.core.endereco.infra;

import com.gusscarros.core.endereco.model.Adress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class AdressInfraTest {

    @InjectMocks
    AdressInfra adressInfra;

    private Adress adress;

    @BeforeEach
    void setUp() {
         adress = Adress.builder()
                 .cep("03245110")
                 .numero("285")
                 .build();
         adressInfra.validationAdress(adress);
    }

    @Test
    void validationAdress() {
       // Mockito.when(adressInfra.validationAdress(adress))
         //       .thenReturn(adress);

        adress = Adress.builder()
                .cep("03245110")
                .numero("285")
                .build();
        adressInfra.validationAdress(adress);

        Assertions.assertTrue(adress.getLogradouro().contains("Rua Mandubirá"));
    }
}