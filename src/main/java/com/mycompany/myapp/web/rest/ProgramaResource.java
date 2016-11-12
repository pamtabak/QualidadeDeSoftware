package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Programa;

import com.mycompany.myapp.repository.ProgramaRepository;
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

/**
 * REST controller for managing Programa.
 */
@RestController
@RequestMapping("/api")
public class ProgramaResource {

    private final Logger log = LoggerFactory.getLogger(ProgramaResource.class);
        
    @Inject
    private ProgramaRepository programaRepository;

    private WriteToLog writeToLog = new WriteToLog();

    /**
     * POST  /programas : Create a new programa.
     *
     * @param programa the programa to create
     * @return the ResponseEntity with status 201 (Created) and with body the new programa, or with status 400 (Bad Request) if the programa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/programas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Programa> createPrograma(@RequestBody Programa programa) throws URISyntaxException {
        log.debug("REST request to save Programa : {}", programa);
        if (programa.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("programa", "idexists", "A new programa cannot already have an ID")).body(null);
        }
        writeToLog.writeMessage(programa.toString() + " criado");
        Programa result = programaRepository.save(programa);
        return ResponseEntity.created(new URI("/api/programas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("programa", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /programas : Updates an existing programa.
     *
     * @param programa the programa to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated programa,
     * or with status 400 (Bad Request) if the programa is not valid,
     * or with status 500 (Internal Server Error) if the programa couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/programas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Programa> updatePrograma(@RequestBody Programa programa) throws URISyntaxException {
        log.debug("REST request to update Programa : {}", programa);
        if (programa.getId() == null) {
            return createPrograma(programa);
        }
        writeToLog.writeMessage(programa.toString() + " atualizado");
        Programa result = programaRepository.save(programa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("programa", programa.getId().toString()))
            .body(result);
    }

    /**
     * GET  /programas : get all the programas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of programas in body
     */
    @RequestMapping(value = "/programas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Programa> getAllProgramas() {
        log.debug("REST request to get all Programas");
        List<Programa> programas = programaRepository.findAll();
        return programas;
    }

    /**
     * GET  /programas/:id : get the "id" programa.
     *
     * @param id the id of the programa to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the programa, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/programas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Programa> getPrograma(@PathVariable Long id) {
        log.debug("REST request to get Programa : {}", id);
        Programa programa = programaRepository.findOne(id);
        return Optional.ofNullable(programa)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /programas/:id : delete the "id" programa.
     *
     * @param id the id of the programa to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/programas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrograma(@PathVariable Long id) {
        log.debug("REST request to delete Programa : {}", id);
        Optional programaOptional = programaRepository.findOneById(id);
        if (programaOptional.isPresent()) {
            writeToLog.writeMessage(programaOptional.get().toString() + " deletado");
        }
        programaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("programa", id.toString())).build();
    }

}
