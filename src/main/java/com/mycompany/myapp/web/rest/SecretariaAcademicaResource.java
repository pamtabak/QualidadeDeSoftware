package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.SecretariaAcademica;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.Authority;


import com.mycompany.myapp.service.UserService;

import com.mycompany.myapp.repository.SecretariaAcademicaRepository;
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
 * REST controller for managing SecretariaAcademica.
 */
@RestController
@RequestMapping("/api")
public class SecretariaAcademicaResource {

    private final Logger log = LoggerFactory.getLogger(SecretariaAcademicaResource.class);
        
    @Inject
    private SecretariaAcademicaRepository secretariaAcademicaRepository;

    @Inject
    private UserService userService;

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private UserRepository userRepository;

    private WriteToLog writeToLog = new WriteToLog();

    /**
     * POST  /secretaria-academicas : Create a new secretariaAcademica.
     *
     * @param secretariaAcademica the secretariaAcademica to create
     * @return the ResponseEntity with status 201 (Created) and with body the new secretariaAcademica, or with status 400 (Bad Request) if the secretariaAcademica has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/secretaria-academicas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SecretariaAcademica> createSecretariaAcademica(@RequestBody SecretariaAcademica secretariaAcademica) throws URISyntaxException {
        log.debug("REST request to save SecretariaAcademica : {}", secretariaAcademica);
        if (secretariaAcademica.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("secretariaAcademica", "idexists", "A new secretariaAcademica cannot already have an ID")).body(null);
        }

        User newUser = userService.createUser(secretariaAcademica.getLogin(), secretariaAcademica.getSenha(), secretariaAcademica.getNome(), null, secretariaAcademica.getLogin() + "@gmail.com", "en");
        Authority authority = authorityRepository.findOne(AuthoritiesConstants.SECRETARIA);
        Set<Authority> authorities = newUser.getAuthorities();
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);

        writeToLog.writeMessage(secretariaAcademica.toString() + " criado");
        SecretariaAcademica result = secretariaAcademicaRepository.save(secretariaAcademica);
        return ResponseEntity.created(new URI("/api/secretaria-academicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("secretariaAcademica", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /secretaria-academicas : Updates an existing secretariaAcademica.
     *
     * @param secretariaAcademica the secretariaAcademica to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated secretariaAcademica,
     * or with status 400 (Bad Request) if the secretariaAcademica is not valid,
     * or with status 500 (Internal Server Error) if the secretariaAcademica couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/secretaria-academicas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SecretariaAcademica> updateSecretariaAcademica(@RequestBody SecretariaAcademica secretariaAcademica) throws URISyntaxException {
        log.debug("REST request to update SecretariaAcademica : {}", secretariaAcademica);
        if (secretariaAcademica.getId() == null) {
            return createSecretariaAcademica(secretariaAcademica);
        }
        writeToLog.writeMessage(secretariaAcademica.toString() + " atualizado");
        SecretariaAcademica result = secretariaAcademicaRepository.save(secretariaAcademica);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("secretariaAcademica", secretariaAcademica.getId().toString()))
            .body(result);
    }

    /**
     * GET  /secretaria-academicas : get all the secretariaAcademicas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of secretariaAcademicas in body
     */
    @RequestMapping(value = "/secretaria-academicas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SecretariaAcademica> getAllSecretariaAcademicas() {
        log.debug("REST request to get all SecretariaAcademicas");
        List<SecretariaAcademica> secretariaAcademicas = secretariaAcademicaRepository.findAll();
        return secretariaAcademicas;
    }

    /**
     * GET  /secretaria-academicas/:id : get the "id" secretariaAcademica.
     *
     * @param id the id of the secretariaAcademica to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the secretariaAcademica, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/secretaria-academicas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SecretariaAcademica> getSecretariaAcademica(@PathVariable Long id) {
        log.debug("REST request to get SecretariaAcademica : {}", id);
        SecretariaAcademica secretariaAcademica = secretariaAcademicaRepository.findOne(id);
        return Optional.ofNullable(secretariaAcademica)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /secretaria-academicas/:id : delete the "id" secretariaAcademica.
     *
     * @param id the id of the secretariaAcademica to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/secretaria-academicas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSecretariaAcademica(@PathVariable Long id) {
        log.debug("REST request to delete SecretariaAcademica : {}", id);
        Optional secretariaAcademicaOptional = secretariaAcademicaRepository.findOneById(id);
        if (secretariaAcademicaOptional.isPresent()) {
            writeToLog.writeMessage(secretariaAcademicaOptional.get().toString() + " deletado");
        }
        secretariaAcademicaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("secretariaAcademica", id.toString())).build();
    }

}
