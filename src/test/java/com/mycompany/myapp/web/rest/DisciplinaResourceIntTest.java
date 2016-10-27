package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;

import com.mycompany.myapp.domain.Disciplina;
import com.mycompany.myapp.repository.DisciplinaRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DisciplinaResource REST controller.
 *
 * @see DisciplinaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class DisciplinaResourceIntTest {

    private static final Integer DEFAULT_NUMERO_DE_VAGAS = 1;
    private static final Integer UPDATED_NUMERO_DE_VAGAS = 2;

    private static final ZonedDateTime DEFAULT_HORARIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_HORARIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_HORARIO_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_HORARIO);

    private static final String DEFAULT_SALA = "AAAAA";
    private static final String UPDATED_SALA = "BBBBB";

    private static final String DEFAULT_CODIGO = "AAAAA";
    private static final String UPDATED_CODIGO = "BBBBB";

    @Inject
    private DisciplinaRepository disciplinaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDisciplinaMockMvc;

    private Disciplina disciplina;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DisciplinaResource disciplinaResource = new DisciplinaResource();
        ReflectionTestUtils.setField(disciplinaResource, "disciplinaRepository", disciplinaRepository);
        this.restDisciplinaMockMvc = MockMvcBuilders.standaloneSetup(disciplinaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disciplina createEntity(EntityManager em) {
        Disciplina disciplina = new Disciplina()
                .numeroDeVagas(DEFAULT_NUMERO_DE_VAGAS)
                .horario(DEFAULT_HORARIO)
                .sala(DEFAULT_SALA)
                .codigo(DEFAULT_CODIGO);
        return disciplina;
    }

    @Before
    public void initTest() {
        disciplina = createEntity(em);
    }

    @Test
    @Transactional
    public void createDisciplina() throws Exception {
        int databaseSizeBeforeCreate = disciplinaRepository.findAll().size();

        // Create the Disciplina

        restDisciplinaMockMvc.perform(post("/api/disciplinas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(disciplina)))
                .andExpect(status().isCreated());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        assertThat(disciplinas).hasSize(databaseSizeBeforeCreate + 1);
        Disciplina testDisciplina = disciplinas.get(disciplinas.size() - 1);
        assertThat(testDisciplina.getNumeroDeVagas()).isEqualTo(DEFAULT_NUMERO_DE_VAGAS);
        assertThat(testDisciplina.getHorario()).isEqualTo(DEFAULT_HORARIO);
        assertThat(testDisciplina.getSala()).isEqualTo(DEFAULT_SALA);
        assertThat(testDisciplina.getCodigo()).isEqualTo(DEFAULT_CODIGO);
    }

    @Test
    @Transactional
    public void getAllDisciplinas() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        // Get all the disciplinas
        restDisciplinaMockMvc.perform(get("/api/disciplinas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(disciplina.getId().intValue())))
                .andExpect(jsonPath("$.[*].numeroDeVagas").value(hasItem(DEFAULT_NUMERO_DE_VAGAS)))
                .andExpect(jsonPath("$.[*].horario").value(hasItem(DEFAULT_HORARIO_STR)))
                .andExpect(jsonPath("$.[*].sala").value(hasItem(DEFAULT_SALA.toString())))
                .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())));
    }

    @Test
    @Transactional
    public void getDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        // Get the disciplina
        restDisciplinaMockMvc.perform(get("/api/disciplinas/{id}", disciplina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(disciplina.getId().intValue()))
            .andExpect(jsonPath("$.numeroDeVagas").value(DEFAULT_NUMERO_DE_VAGAS))
            .andExpect(jsonPath("$.horario").value(DEFAULT_HORARIO_STR))
            .andExpect(jsonPath("$.sala").value(DEFAULT_SALA.toString()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDisciplina() throws Exception {
        // Get the disciplina
        restDisciplinaMockMvc.perform(get("/api/disciplinas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);
        int databaseSizeBeforeUpdate = disciplinaRepository.findAll().size();

        // Update the disciplina
        Disciplina updatedDisciplina = disciplinaRepository.findOne(disciplina.getId());
        updatedDisciplina
                .numeroDeVagas(UPDATED_NUMERO_DE_VAGAS)
                .horario(UPDATED_HORARIO)
                .sala(UPDATED_SALA)
                .codigo(UPDATED_CODIGO);

        restDisciplinaMockMvc.perform(put("/api/disciplinas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDisciplina)))
                .andExpect(status().isOk());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        assertThat(disciplinas).hasSize(databaseSizeBeforeUpdate);
        Disciplina testDisciplina = disciplinas.get(disciplinas.size() - 1);
        assertThat(testDisciplina.getNumeroDeVagas()).isEqualTo(UPDATED_NUMERO_DE_VAGAS);
        assertThat(testDisciplina.getHorario()).isEqualTo(UPDATED_HORARIO);
        assertThat(testDisciplina.getSala()).isEqualTo(UPDATED_SALA);
        assertThat(testDisciplina.getCodigo()).isEqualTo(UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void deleteDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);
        int databaseSizeBeforeDelete = disciplinaRepository.findAll().size();

        // Get the disciplina
        restDisciplinaMockMvc.perform(delete("/api/disciplinas/{id}", disciplina.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        assertThat(disciplinas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
