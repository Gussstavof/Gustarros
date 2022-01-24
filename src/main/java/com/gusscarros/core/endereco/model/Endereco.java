package com.gusscarros.core.endereco.model;

public class Endereco {

    private String cep;
    private String uf;
    private String localidade;
    private String logradouro;
    private String bairro;

    public Endereco(String cep, String uf, String localidade, String logradouro, String bairro) {
        this.cep = cep;
        this.uf = uf;
        this.localidade = localidade;
        this.logradouro = logradouro;
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
}
