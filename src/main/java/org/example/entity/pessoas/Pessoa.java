package org.example.entity.pessoas;

import java.io.Serializable;

public interface Pessoa{

    String getNome();

    void setNome(String nome);

    String getPaisOrigem();

    void setPaisOrigem(String paisOrigem);

    boolean casar(Pessoa conjuge);

    boolean divorciar();

}
