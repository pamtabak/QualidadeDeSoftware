package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Inscricao;
import com.mycompany.myapp.domain.Disciplina;
import com.mycompany.myapp.domain.Aluno;

import com.mycompany.myapp.repository.InscricaoRepository;
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
 * REST controller for managing Inscricao.
 */
@RestController
@RequestMapping("/api")
public class InscricaoResource {

    private final Logger log = LoggerFactory.getLogger(InscricaoResource.class);
        
    @Inject
    private InscricaoRepository inscricaoRepository;

    @Inject
    private DisciplinaRepository disciplinaRepository;

    private WriteToLog writeToLog = new WriteToLog();

    /**
     * POST  /inscricaos : Create a new inscricao.
     *
     * @param inscricao the inscricao to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inscricao, or with status 400 (Bad Request) if the inscricao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/inscricaos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Inscricao> createInscricao(@RequestBody Inscricao inscricao) throws URISyntaxException {
        log.debug("REST request to save Inscricao : {}", inscricao);
        if (inscricao.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("inscricao", "idexists", "A new inscricao cannot already have an ID")).body(null);
        }
        List<Inscricao> inscricaos = inscricaoRepository.findAll();
        Disciplina disciplina = inscricao.getDisciplina();
        Aluno aluno = inscricao.getAluno();
        Integer numeroDeVagas = disciplina.getNumeroDeVagas();
        Integer numeroDeInscritos = disciplina.getNumeroDeInscritos();
        for ( Inscricao ins : inscricaos ) {
            if(ins.getDisciplina().equals(disciplina) && ins.getAluno().equals(aluno)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("inscricao", "idexists", "Aluno  já possui inscricao nessa disciplina")).body(null);
            }
        }
        if(numeroDeInscritos + 1 <= numeroDeVagas) {
            writeToLog.writeMessage(inscricao.toString() + " criada");
            inscricao.setEstado("solicitado");
            Inscricao result = inscricaoRepository.save(inscricao);
            return ResponseEntity.created(new URI("/api/inscricaos/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("inscricao", result.getId().toString()))
                .body(result);
        } else {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("inscricao", "idexists", "a disciplina ja esta com capacidade maxima")).body(null);
        }
    }

    /**
     * PUT  /inscricaos : Updates an existing inscricao.
     *
     * @param inscricao the inscricao to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inscricao,
     * or with status 400 (Bad Request) if the inscricao is not valid,
     * or with status 500 (Internal Server Error) if the inscricao couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/inscricaos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Inscricao> updateInscricao(@RequestBody Inscricao inscricao) throws URISyntaxException {
        log.debug("REST request to update Inscricao : {}", inscricao);
        if (inscricao.getId() == null) {
            return createInscricao(inscricao);
        }
        String estado = inscricao.getEstado();
        Inscricao inscricaoDB = inscricaoRepository.findOne(inscricao.getId());
        System.out.println(estado);
        if(estado.equals("concordado")) {
            Disciplina disciplina = inscricao.getDisciplina();
            Integer numeroDeInscritos = disciplina.getNumeroDeInscritos();
            if(inscricaoDB.getEstado().equals("solicitado")) {
                if(numeroDeInscritos + 1 <=  disciplina.getNumeroDeVagas()) {
                    disciplina.setNumeroDeInscritos(numeroDeInscritos + 1);
                    disciplinaRepository.save(disciplina);
                    inscricao.setEstado("inscrito");
                }  
            } else if( inscricaoDB.getEstado().equals("solicitar trancamento")) {
                disciplina.setNumeroDeInscritos(numeroDeInscritos - 1);
                disciplinaRepository.save(disciplina);
                inscricao.setEstado("trancado");
            }
        } else if(estado.equals("não concordado")){
            if(inscricaoDB.getEstado().equals("solicitar trancamento")){
                inscricao.setEstado("inscrito");
            }
        }
        writeToLog.writeMessage(inscricao.toString() + " atualizada");
        Inscricao result = inscricaoRepository.save(inscricao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("inscricao", inscricao.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inscricaos : get all the inscricaos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of inscricaos in body
     */
    @RequestMapping(value = "/inscricaos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Inscricao> getAllInscricaos() {
        log.debug("REST request to get all Inscricaos");
        List<Inscricao> inscricaos = inscricaoRepository.findAll();
        return inscricaos;
    }

    /**
     * GET  /inscricaos/:id : get the "id" inscricao.
     *
     * @param id the id of the inscricao to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inscricao, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/inscricaos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Inscricao> getInscricao(@PathVariable Long id) {
        log.debug("REST request to get Inscricao : {}", id);
        Inscricao inscricao = inscricaoRepository.findOne(id);
        return Optional.ofNullable(inscricao)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /inscricaos/:id : delete the "id" inscricao.
     *
     * @param id the id of the inscricao to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/inscricaos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInscricao(@PathVariable Long id) {
        log.debug("REST request to delete Inscricao : {}", id);
        Optional inscricaoOptional = inscricaoRepository.findOneById(id);
        if (inscricaoOptional.isPresent()) {
            writeToLog.writeMessage(inscricaoOptional.get().toString() + " deletada");
        }
        inscricaoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("inscricao", id.toString())).build();
    }

}
