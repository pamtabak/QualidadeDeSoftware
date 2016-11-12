package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Professor;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Professor entity.
 */
@SuppressWarnings("unused")
public interface ProfessorRepository extends JpaRepository<Professor,Long> {
	Optional<Professor> findOneById(Long userId);
}
