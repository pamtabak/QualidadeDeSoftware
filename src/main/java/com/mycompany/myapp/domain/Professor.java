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
 * A Professor.
 */
@Entity
@Table(name = "professor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@PrimaryKeyJoinColumn(name="id")
public class Professor extends Usuario {

    @Column(name = "nome")
    private String nome;

    @Column(name = "documento")
    private String documento;

    @OneToMany(mappedBy = "professor")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Aluno> orientadors = new HashSet<>();

    @OneToMany(mappedBy = "professor")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Disciplina> professors = new HashSet<>();

    public Professor(){}

    public Professor(String login, String senha, String nome, String documento)
    {
        super(login, senha);
        this.nome      = nome;
        this.documento = documento;
    }

    public String getNome() {
        return nome;
    }

    public Professor nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    public Professor documento(String documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Set<Aluno> getOrientadors() {
        return orientadors;
    }

    public Professor orientadors(Set<Aluno> alunos) {
        this.orientadors = alunos;
        return this;
    }

    public Professor addOrientador(Aluno aluno) {
        orientadors.add(aluno);
        aluno.setProfessor(this);
        return this;
    }

    public Professor removeOrientador(Aluno aluno) {
        orientadors.remove(aluno);
        aluno.setProfessor(null);
        return this;
    }

    public void setOrientadors(Set<Aluno> alunos) {
        this.orientadors = alunos;
    }

    public Set<Disciplina> getProfessors() {
        return professors;
    }

    public Professor professors(Set<Disciplina> disciplinas) {
        this.professors = disciplinas;
        return this;
    }

    public Professor addProfessor(Disciplina disciplina) {
        professors.add(disciplina);
        disciplina.setProfessor(this);
        return this;
    }

    public Professor removeProfessor(Disciplina disciplina) {
        professors.remove(disciplina);
        disciplina.setProfessor(null);
        return this;
    }

    public void setProfessors(Set<Disciplina> disciplinas) {
        this.professors = disciplinas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Professor professor = (Professor) o;
        if(professor.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, professor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Professor{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", documento='" + documento + "'" +
            ", login='" + login + "'" +
            '}';
    }
}
