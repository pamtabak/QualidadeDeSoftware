package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SecretariaAcademica;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SecretariaAcademica entity.
 */
@SuppressWarnings("unused")
public interface SecretariaAcademicaRepository extends JpaRepository<SecretariaAcademica,Long> {

}
