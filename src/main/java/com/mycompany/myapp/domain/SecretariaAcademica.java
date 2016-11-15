package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SecretariaAcademica.
 */
@Entity
@Table(name = "secretaria_academica")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@PrimaryKeyJoinColumn(name="id")
public class SecretariaAcademica extends Usuario  
{
    @Column(name = "nome")
    private String nome;

    public String getNome() 
    {
        return nome;
    }

    public SecretariaAcademica nome(String nome) 
    {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) 
    {
        this.nome = nome;
    }

    public SecretariaAcademica() {
        this.tipo = "Secretaria";
    }

    public SecretariaAcademica(String login, String senha, String nome)
    {
        super(login, senha, "Secretaria");
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) 
    {
        if (this == o) 
        {
            return true;
        }
        if (o == null || getClass() != o.getClass()) 
        {
            return false;
        }
        SecretariaAcademica secretariaAcademica = (SecretariaAcademica) o;
        if(secretariaAcademica.id == null || id == null) 
        {
            return false;
        }
        return Objects.equals(id, secretariaAcademica.id);
    }

    @Override
    public int hashCode() 
    {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() 
    {
        return "SecretariaAcademica{" +
            "id=" + id  +
            ", nome='"  + nome  + "'" +
            ", login='" + login + "'" +
            ", tipo='"  + tipo  + "'" +
            '}';
    }
}
