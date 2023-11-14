package org.example.entity.endereco;

import java.io.Serializable;

public class Endereco implements Serializable {

    private String rua;
    private String numero;
    private String complemento;

    public Endereco(String rua, String numero, String complemento) {
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "rua='" + rua + ' ' +
                ", numero='" + numero + ' ' +
                ", complemento='" + complemento +
                '}';
    }
}

