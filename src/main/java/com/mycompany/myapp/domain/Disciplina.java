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
 * A Disciplina.
 */
@Entity
@Table(name = "disciplina")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Disciplina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "numero_de_vagas")
    private Integer numeroDeVagas;

    @Column(name = "horario")
    private ZonedDateTime horario;

    @Column(name = "sala")
    private String sala;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nome")
    private String nome;

    @ManyToOne
    private Professor professor;

    @OneToMany(mappedBy = "disciplina")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Inscricao> disciplinas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroDeVagas() {
        return numeroDeVagas;
    }

    public Disciplina numeroDeVagas(Integer numeroDeVagas) {
        this.numeroDeVagas = numeroDeVagas;
        return this;
    }

    public void setNumeroDeVagas(Integer numeroDeVagas) {
        this.numeroDeVagas = numeroDeVagas;
    }

    public ZonedDateTime getHorario() {
        return horario;
    }

    public Disciplina horario(ZonedDateTime horario) {
        this.horario = horario;
        return this;
    }

    public void setHorario(ZonedDateTime horario) {
        this.horario = horario;
    }

    public String getSala() {
        return sala;
    }

    public Disciplina sala(String sala) {
        this.sala = sala;
        return this;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getCodigo() {
        return codigo;
    }

    public Disciplina codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public Disciplina nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Professor getProfessor() {
        return professor;
    }

    public Disciplina professor(Professor professor) {
        this.professor = professor;
        return this;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Set<Inscricao> getDisciplinas() {
        return disciplinas;
    }

    public Disciplina disciplinas(Set<Inscricao> inscricaos) {
        this.disciplinas = inscricaos;
        return this;
    }

    public Disciplina addDisciplina(Inscricao inscricao) {
        disciplinas.add(inscricao);
        inscricao.setDisciplina(this);
        return this;
    }

    public Disciplina removeDisciplina(Inscricao inscricao) {
        disciplinas.remove(inscricao);
        inscricao.setDisciplina(null);
        return this;
    }

    public void setDisciplinas(Set<Inscricao> inscricaos) {
        this.disciplinas = inscricaos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Disciplina disciplina = (Disciplina) o;
        if(disciplina.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, disciplina.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Disciplina{" +
            "id=" + id +
            ", numeroDeVagas='" + numeroDeVagas + "'" +
            ", horario='" + horario + "'" +
            ", sala='" + sala + "'" +
            ", codigo='" + codigo + "'" +
            '}';
    }
}
