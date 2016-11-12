package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Usuario;

import com.mycompany.myapp.repository.UsuarioRepository;
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
 * REST controller for managing Usuario.
 */
@RestController
@RequestMapping("/api")
public class UsuarioResource {

    private final Logger log = LoggerFactory.getLogger(UsuarioResource.class);
        
    @Inject
    private UsuarioRepository usuarioRepository;

    private WriteToLog writeToLog = new WriteToLog();

    /**
     * POST  /usuarios : Create a new usuario.
     *
     * @param usuario the usuario to create
     * @return the ResponseEntity with status 201 (Created) and with body the new usuario, or with status 400 (Bad Request) if the usuario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/usuarios",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) throws URISyntaxException {
        log.debug("REST request to save Usuario : {}", usuario);
        if (usuario.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("usuario", "idexists", "A new usuario cannot already have an ID")).body(null);
        }

        writeToLog.writeMessage(usuario.toString() + " criado");

        Usuario result = usuarioRepository.save(usuario);
        return ResponseEntity.created(new URI("/api/usuarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("usuario", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /usuarios : Updates an existing usuario.
     *
     * @param usuario the usuario to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated usuario,
     * or with status 400 (Bad Request) if the usuario is not valid,
     * or with status 500 (Internal Server Error) if the usuario couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/usuarios",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario) throws URISyntaxException {
        log.debug("REST request to update Usuario : {}", usuario);
        if (usuario.getId() == null) {
            return createUsuario(usuario);
        }
        writeToLog.writeMessage(usuario.toString() + " atualizado");
        Usuario result = usuarioRepository.save(usuario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("usuario", usuario.getId().toString()))
            .body(result);
    }

    /**
     * GET  /usuarios : get all the usuarios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of usuarios in body
     */
    @RequestMapping(value = "/usuarios",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Usuario> getAllUsuarios() {
        log.debug("REST request to get all Usuarios");
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    /**
     * GET  /usuarios/:id : get the "id" usuario.
     *
     * @param id the id of the usuario to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the usuario, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/usuarios/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Usuario> getUsuario(@PathVariable Long id) {
        log.debug("REST request to get Usuario : {}", id);
        Usuario usuario = usuarioRepository.findOne(id);
        return Optional.ofNullable(usuario)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /usuarios/:id : delete the "id" usuario.
     *
     * @param id the id of the usuario to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/usuarios/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        log.debug("REST request to delete Usuario : {}", id);
        Optional usuarioOptional = usuarioRepository.findOneById(id);
        if (usuarioOptional.isPresent()) {
            writeToLog.writeMessage(usuarioOptional.get().toString() + " deletado");
        }
        usuarioRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("usuario", id.toString())).build();
    }
}
