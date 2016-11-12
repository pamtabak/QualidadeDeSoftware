package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Aluno;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Aluno entity.
 */
@SuppressWarnings("unused")
public interface AlunoRepository extends JpaRepository<Aluno,Long> {
	Optional<Aluno> findOneById(Long userId);
}
