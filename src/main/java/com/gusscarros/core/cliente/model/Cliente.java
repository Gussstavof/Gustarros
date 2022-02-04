package com.gusscarros.core.cliente.model;

import com.gusscarros.core.endereco.model.Endereco;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Locale;

@Document(collection = "usuarios")
public class Cliente {


    @Id
    private  String id;

    @NotBlank(message = "Nome inválido")
    private String nome;

    @CPF(message = "CPF inválido")
    private String cpf;

    @DateTimeFormat(pattern = "yyyy-dd-MM")
    @NotNull
    private LocalDate dataDeNascimento;

    @CreditCardNumber(message = "Número Do cartão de crédito Inválido")
    private String cartao;


    private Endereco endereco;
    private String sexo;
    private boolean atividade;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome.toUpperCase(Locale.ROOT);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public  LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento( LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getCartao() {
        return cartao;
    }

    public void setCartao(String cartao) {
        this.cartao = cartao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo.toUpperCase(Locale.ROOT);
    }

    public boolean isAtividade() {
        return atividade;
    }

    public void setAtividade(boolean atividade) {
        this.atividade = atividade;
    }
}
