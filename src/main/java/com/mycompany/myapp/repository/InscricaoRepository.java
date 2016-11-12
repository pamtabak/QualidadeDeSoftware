package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Inscricao;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Inscricao entity.
 */
@SuppressWarnings("unused")
public interface InscricaoRepository extends JpaRepository<Inscricao,Long> {
	Optional<Inscricao> findOneById(Long userId);
}
