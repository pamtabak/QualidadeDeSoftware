package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Disciplina;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Disciplina entity.
 */
@SuppressWarnings("unused")
public interface DisciplinaRepository extends JpaRepository<Disciplina,Long> {
	Optional<Disciplina> findOneById(Long userId);
}
