package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Disciplina;

import com.mycompany.myapp.repository.DisciplinaRepository;
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
 * REST controller for managing Disciplina.
 */
@RestController
@RequestMapping("/api")
public class DisciplinaResource {

    private final Logger log = LoggerFactory.getLogger(DisciplinaResource.class);
        
    @Inject
    private DisciplinaRepository disciplinaRepository;

    private WriteToLog writeToLog = new WriteToLog();
    
    /**
     * POST  /disciplinas : Create a new disciplina.
     *
     * @param disciplina the disciplina to create
     * @return the ResponseEntity with status 201 (Created) and with body the new disciplina, or with status 400 (Bad Request) if the disciplina has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/disciplinas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Disciplina> createDisciplina(@RequestBody Disciplina disciplina) throws URISyntaxException {
        log.debug("REST request to save Disciplina : {}", disciplina);
        if (disciplina.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("disciplina", "idexists", "A new disciplina cannot already have an ID")).body(null);
        }
        writeToLog.writeMessage(disciplina.toString() + " criada");
        disciplina.setNumeroDeInscritos(0);
        Disciplina result = disciplinaRepository.save(disciplina);
        return ResponseEntity.created(new URI("/api/disciplinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("disciplina", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /disciplinas : Updates an existing disciplina.
     *
     * @param disciplina the disciplina to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated disciplina,
     * or with status 400 (Bad Request) if the disciplina is not valid,
     * or with status 500 (Internal Server Error) if the disciplina couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/disciplinas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Disciplina> updateDisciplina(@RequestBody Disciplina disciplina) throws URISyntaxException {
        log.debug("REST request to update Disciplina : {}", disciplina);
        if (disciplina.getId() == null) {
            return createDisciplina(disciplina);
        }
        writeToLog.writeMessage(disciplina.toString() + " atualizada");
        Disciplina result = disciplinaRepository.save(disciplina);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("disciplina", disciplina.getId().toString()))
            .body(result);
    }

    /**
     * GET  /disciplinas : get all the disciplinas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of disciplinas in body
     */
    @RequestMapping(value = "/disciplinas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Disciplina> getAllDisciplinas() {
        log.debug("REST request to get all Disciplinas");
        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        return disciplinas;
    }

    /**
     * GET  /disciplinas/:id : get the "id" disciplina.
     *
     * @param id the id of the disciplina to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the disciplina, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/disciplinas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Disciplina> getDisciplina(@PathVariable Long id) {
        log.debug("REST request to get Disciplina : {}", id);
        Disciplina disciplina = disciplinaRepository.findOne(id);
        return Optional.ofNullable(disciplina)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /disciplinas/:id : delete the "id" disciplina.
     *
     * @param id the id of the disciplina to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/disciplinas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDisciplina(@PathVariable Long id) {
        log.debug("REST request to delete Disciplina : {}", id);
        Optional disciplinaOptional = disciplinaRepository.findOneById(id);
        if (disciplinaOptional.isPresent()) {
            writeToLog.writeMessage(disciplinaOptional.get().toString() + " deletada");
        }
        disciplinaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("disciplina", id.toString())).build();
    }

}
