package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Usuario.
 */
@Entity
@Table(name = "usuario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Inheritance(strategy=InheritanceType.JOINED)
public class Usuario implements Serializable 
{
    protected static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id")
    protected Long id;

    @Column(name = "login")
    protected String login;

    @Column(name = "senha")
    protected String senha;

    @Column(name = "tipo")
    protected String tipo;

    public Usuario(){}

    public Usuario(String login, String senha, String tipo)
    {
        this.login = login;
        this.senha = senha;
        this.tipo  = tipo;
    }

    public Long getId() 
    {
        return id;
    }

    public void setId(Long id) 
    {
        this.id = id;
    }

    public String getLogin() 
    {
        return login;
    }

    public Usuario login(String login) 
    {
        this.login = login;
        return this;
    }

    public void setLogin(String login) 
    {
        this.login = login;
    }

    public String getSenha() 
    {
        return senha;
    }

    public Usuario senha(String senha) 
    {
        this.senha = senha;
        return this;
    }

    public void setSenha(String senha) 
    {
        this.senha = senha;
    }

     public String getTipo() 
    {
        return tipo;
    }

    public Usuario tipo(String tipo) 
    {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) 
    {
        this.tipo = tipo;
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
        Usuario usuario = (Usuario) o;
        if(usuario.id == null || id == null) 
        {
            return false;
        }
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() 
    {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() 
    {
        return "Usuario{" +
            "id=" + id +
            ", login='" + login + "'" +
            ", senha='" + senha + "'" +
            ", tipo='"  + tipo + "'" +
            '}';
    }
}
