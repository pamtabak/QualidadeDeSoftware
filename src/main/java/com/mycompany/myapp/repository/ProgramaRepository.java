package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Programa;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Programa entity.
 */
@SuppressWarnings("unused")
public interface ProgramaRepository extends JpaRepository<Programa,Long> {
	Optional<Programa> findOneById(Long userId);
}
