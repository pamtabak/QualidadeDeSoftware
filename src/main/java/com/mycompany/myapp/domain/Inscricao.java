package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Inscricao.
 */
@Entity
@Table(name = "inscricao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Inscricao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "periodo")
    private ZonedDateTime periodo;

    @Column(name = "grau")
    private Double grau;

    @Column(name = "frequencia")
    private Integer frequencia;

    @ManyToOne
    private Aluno aluno;

    @ManyToOne
    private Disciplina disciplina;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getPeriodo() {
        return periodo;
    }

    public Inscricao periodo(ZonedDateTime periodo) {
        this.periodo = periodo;
        return this;
    }

    public void setPeriodo(ZonedDateTime periodo) {
        this.periodo = periodo;
    }

    public Double getGrau() {
        return grau;
    }

    public Inscricao grau(Double grau) {
        this.grau = grau;
        return this;
    }

    public void setGrau(Double grau) {
        this.grau = grau;
    }

    public Integer getFrequencia() {
        return frequencia;
    }

    public Inscricao frequencia(Integer frequencia) {
        this.frequencia = frequencia;
        return this;
    }

    public void setFrequencia(Integer frequencia) {
        this.frequencia = frequencia;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Inscricao aluno(Aluno aluno) {
        this.aluno = aluno;
        return this;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public Inscricao disciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
        return this;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Inscricao inscricao = (Inscricao) o;
        if(inscricao.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, inscricao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Inscricao{" +
            "id=" + id +
            ", periodo='" + periodo + "'" +
            ", grau='" + grau + "'" +
            ", frequencia='" + frequencia + "'" +
            '}';
    }
}
