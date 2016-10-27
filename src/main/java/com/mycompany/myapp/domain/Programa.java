package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Programa.
 */
@Entity
@Table(name = "programa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Programa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "codigo")
    private Integer codigo;

    @OneToMany(mappedBy = "programa")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Aluno> programas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Programa nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public Programa codigo(Integer codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Set<Aluno> getProgramas() {
        return programas;
    }

    public Programa programas(Set<Aluno> alunos) {
        this.programas = alunos;
        return this;
    }

    public Programa addPrograma(Aluno aluno) {
        programas.add(aluno);
        aluno.setPrograma(this);
        return this;
    }

    public Programa removePrograma(Aluno aluno) {
        programas.remove(aluno);
        aluno.setPrograma(null);
        return this;
    }

    public void setProgramas(Set<Aluno> alunos) {
        this.programas = alunos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Programa programa = (Programa) o;
        if(programa.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, programa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Programa{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", codigo='" + codigo + "'" +
            '}';
    }
}
