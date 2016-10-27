package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;

import com.mycompany.myapp.domain.Programa;
import com.mycompany.myapp.repository.ProgramaRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProgramaResource REST controller.
 *
 * @see ProgramaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class ProgramaResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAA";
    private static final String UPDATED_NOME = "BBBBB";

    private static final Integer DEFAULT_CODIGO = 1;
    private static final Integer UPDATED_CODIGO = 2;

    @Inject
    private ProgramaRepository programaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restProgramaMockMvc;

    private Programa programa;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProgramaResource programaResource = new ProgramaResource();
        ReflectionTestUtils.setField(programaResource, "programaRepository", programaRepository);
        this.restProgramaMockMvc = MockMvcBuilders.standaloneSetup(programaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Programa createEntity(EntityManager em) {
        Programa programa = new Programa()
                .nome(DEFAULT_NOME)
                .codigo(DEFAULT_CODIGO);
        return programa;
    }

    @Before
    public void initTest() {
        programa = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrograma() throws Exception {
        int databaseSizeBeforeCreate = programaRepository.findAll().size();

        // Create the Programa

        restProgramaMockMvc.perform(post("/api/programas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(programa)))
                .andExpect(status().isCreated());

        // Validate the Programa in the database
        List<Programa> programas = programaRepository.findAll();
        assertThat(programas).hasSize(databaseSizeBeforeCreate + 1);
        Programa testPrograma = programas.get(programas.size() - 1);
        assertThat(testPrograma.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPrograma.getCodigo()).isEqualTo(DEFAULT_CODIGO);
    }

    @Test
    @Transactional
    public void getAllProgramas() throws Exception {
        // Initialize the database
        programaRepository.saveAndFlush(programa);

        // Get all the programas
        restProgramaMockMvc.perform(get("/api/programas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(programa.getId().intValue())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
                .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)));
    }

    @Test
    @Transactional
    public void getPrograma() throws Exception {
        // Initialize the database
        programaRepository.saveAndFlush(programa);

        // Get the programa
        restProgramaMockMvc.perform(get("/api/programas/{id}", programa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(programa.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO));
    }

    @Test
    @Transactional
    public void getNonExistingPrograma() throws Exception {
        // Get the programa
        restProgramaMockMvc.perform(get("/api/programas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrograma() throws Exception {
        // Initialize the database
        programaRepository.saveAndFlush(programa);
        int databaseSizeBeforeUpdate = programaRepository.findAll().size();

        // Update the programa
        Programa updatedPrograma = programaRepository.findOne(programa.getId());
        updatedPrograma
                .nome(UPDATED_NOME)
                .codigo(UPDATED_CODIGO);

        restProgramaMockMvc.perform(put("/api/programas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPrograma)))
                .andExpect(status().isOk());

        // Validate the Programa in the database
        List<Programa> programas = programaRepository.findAll();
        assertThat(programas).hasSize(databaseSizeBeforeUpdate);
        Programa testPrograma = programas.get(programas.size() - 1);
        assertThat(testPrograma.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPrograma.getCodigo()).isEqualTo(UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void deletePrograma() throws Exception {
        // Initialize the database
        programaRepository.saveAndFlush(programa);
        int databaseSizeBeforeDelete = programaRepository.findAll().size();

        // Get the programa
        restProgramaMockMvc.perform(delete("/api/programas/{id}", programa.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Programa> programas = programaRepository.findAll();
        assertThat(programas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
