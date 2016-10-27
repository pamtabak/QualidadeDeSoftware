package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Inscricao;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Inscricao entity.
 */
@SuppressWarnings("unused")
public interface InscricaoRepository extends JpaRepository<Inscricao,Long> {

}
