package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Professor;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.Authority;

import com.mycompany.myapp.service.UserService;

import com.mycompany.myapp.repository.ProfessorRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.repository.AuthorityRepository;

import com.mycompany.myapp.security.AuthoritiesConstants;

import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Professor.
 */
@RestController
@RequestMapping("/api")
public class ProfessorResource {

    private final Logger log = LoggerFactory.getLogger(ProfessorResource.class);

    @Inject
    private ProfessorRepository professorRepository;

    @Inject
    private UserService userService;

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private UserRepository userRepository;

    private WriteToLog writeToLog = new WriteToLog();

    /**
     * POST  /professors : Create a new professor.
     *
     * @param professor the professor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new professor, or with status 400 (Bad Request) if the professor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/professors",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Professor> createProfessor(@RequestBody Professor professor) throws URISyntaxException {
        log.debug("REST request to save Professor : {}", professor);
        if (professor.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("professor", "idexists", "A new professor cannot already have an ID")).body(null);
        }

        User newUser = userService.createUser(professor.getLogin(), professor.getSenha(), professor.getNome(), null, professor.getLogin() + "@gmail.com", "en");
        Authority authority = authorityRepository.findOne(AuthoritiesConstants.PROFESSOR);
        Set<Authority> authorities = newUser.getAuthorities();
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);

        writeToLog.writeMessage(professor.toString() + " criado");
        Professor result = professorRepository.save(professor);
        return ResponseEntity.created(new URI("/api/professors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("professor", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /professors : Updates an existing professor.
     *
     * @param professor the professor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated professor,
     * or with status 400 (Bad Request) if the professor is not valid,
     * or with status 500 (Internal Server Error) if the professor couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/professors",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Professor> updateProfessor(@RequestBody Professor professor) throws URISyntaxException {
        log.debug("REST request to update Professor : {}", professor);
        if (professor.getId() == null) {
            return createProfessor(professor);
        }
        writeToLog.writeMessage(professor.toString() + " atualizado");
        Professor result = professorRepository.save(professor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("professor", professor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /professors : get all the professors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of professors in body
     */
    @RequestMapping(value = "/professors",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Professor> getAllProfessors() {
        log.debug("REST request to get all Professors");
        List<Professor> professors = professorRepository.findAll();
        return professors;
    }

    /**
     * GET  /professors/:id : get the "id" professor.
     *
     * @param id the id of the professor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the professor, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/professors/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Professor> getProfessor(@PathVariable Long id) {
        log.debug("REST request to get Professor : {}", id);
        Professor professor = professorRepository.findOne(id);
        return Optional.ofNullable(professor)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /professors/:id : delete the "id" professor.
     *
     * @param id the id of the professor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/professors/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
        log.debug("REST request to delete Professor : {}", id);
        Optional professorOptional = professorRepository.findOneById(id);
        if (professorOptional.isPresent()) {
            writeToLog.writeMessage(professorOptional.get().toString() + " deletado");
        }
        professorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("professor", id.toString())).build();
    }

}
