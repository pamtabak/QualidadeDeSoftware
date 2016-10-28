package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Aluno.
 */
@Entity
@Table(name = "aluno")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@PrimaryKeyJoinColumn(name="id")
public class Aluno extends Usuario 
{

    @Column(name = "nome")
    private String nome;

    @Column(name = "documento")
    private Integer documento;

    @Column(name = "matricula")
    private Integer matricula;

    @Column(name = "periodo")
    private ZonedDateTime periodo;

    @ManyToOne
    private Programa programa;

    @OneToMany(mappedBy = "aluno")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Inscricao> alunos = new HashSet<>();

    @ManyToOne
    private Professor professor;

    public Aluno() {}

    public Aluno(String login, String senha, String nome, Integer documento, Integer matricula, ZonedDateTime periodo, Programa programa, Professor professor)
    {
        super(login, senha);
        this.nome      = nome;
        this.documento = documento;
        this.matricula = matricula;
        this.periodo   = periodo;
        this.programa  = programa;
        this.professor = professor; 
    }

    public String getNome() {
        return nome;
    }

    public Aluno nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getDocumento() {
        return documento;
    }

    public Aluno documento(Integer documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public Aluno matricula(Integer matricula) {
        this.matricula = matricula;
        return this;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public ZonedDateTime getPeriodo() {
        return periodo;
    }

    public Aluno periodo(ZonedDateTime periodo) {
        this.periodo = periodo;
        return this;
    }

    public void setPeriodo(ZonedDateTime periodo) {
        this.periodo = periodo;
    }

    public Programa getPrograma() {
        return programa;
    }

    public Aluno programa(Programa programa) {
        this.programa = programa;
        return this;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public Set<Inscricao> getAlunos() {
        return alunos;
    }

    public Aluno alunos(Set<Inscricao> inscricaos) {
        this.alunos = inscricaos;
        return this;
    }

    public Aluno addAluno(Inscricao inscricao) {
        alunos.add(inscricao);
        inscricao.setAluno(this);
        return this;
    }

    public Aluno removeAluno(Inscricao inscricao) {
        alunos.remove(inscricao);
        inscricao.setAluno(null);
        return this;
    }

    public void setAlunos(Set<Inscricao> inscricaos) {
        this.alunos = inscricaos;
    }

    public Professor getProfessor() {
        return professor;
    }

    public Aluno professor(Professor professor) {
        this.professor = professor;
        return this;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Aluno aluno = (Aluno) o;
        if(aluno.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, aluno.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Aluno{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", documento='" + documento + "'" +
            ", matricula='" + matricula + "'" +
            ", periodo='" + periodo + "'" +
            '}';
    }
}
