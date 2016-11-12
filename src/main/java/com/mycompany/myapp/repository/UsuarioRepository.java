package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Usuario;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Usuario entity.
 */
@SuppressWarnings("unused")
public interface UsuarioRepository extends JpaRepository<Usuario,Long> 
{
	List<Usuario> findAll();

	Optional<Usuario> findOneById(Long userId);
}
