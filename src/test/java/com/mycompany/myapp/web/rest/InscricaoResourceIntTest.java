package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;

import com.mycompany.myapp.domain.Inscricao;
import com.mycompany.myapp.repository.InscricaoRepository;

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
 * Test class for the InscricaoResource REST controller.
 *
 * @see InscricaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class InscricaoResourceIntTest {

    private static final ZonedDateTime DEFAULT_PERIODO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_PERIODO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_PERIODO_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_PERIODO);

    private static final Double DEFAULT_GRAU = 1D;
    private static final Double UPDATED_GRAU = 2D;

    private static final Integer DEFAULT_FREQUENCIA = 1;
    private static final Integer UPDATED_FREQUENCIA = 2;

    @Inject
    private InscricaoRepository inscricaoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restInscricaoMockMvc;

    private Inscricao inscricao;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InscricaoResource inscricaoResource = new InscricaoResource();
        ReflectionTestUtils.setField(inscricaoResource, "inscricaoRepository", inscricaoRepository);
        this.restInscricaoMockMvc = MockMvcBuilders.standaloneSetup(inscricaoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inscricao createEntity(EntityManager em) {
        Inscricao inscricao = new Inscricao()
                .periodo(DEFAULT_PERIODO)
                .grau(DEFAULT_GRAU)
                .frequencia(DEFAULT_FREQUENCIA);
        return inscricao;
    }

    @Before
    public void initTest() {
        inscricao = createEntity(em);
    }

    @Test
    @Transactional
    public void createInscricao() throws Exception {
        int databaseSizeBeforeCreate = inscricaoRepository.findAll().size();

        // Create the Inscricao

        restInscricaoMockMvc.perform(post("/api/inscricaos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inscricao)))
                .andExpect(status().isCreated());

        // Validate the Inscricao in the database
        List<Inscricao> inscricaos = inscricaoRepository.findAll();
        assertThat(inscricaos).hasSize(databaseSizeBeforeCreate + 1);
        Inscricao testInscricao = inscricaos.get(inscricaos.size() - 1);
        assertThat(testInscricao.getPeriodo()).isEqualTo(DEFAULT_PERIODO);
        assertThat(testInscricao.getGrau()).isEqualTo(DEFAULT_GRAU);
        assertThat(testInscricao.getFrequencia()).isEqualTo(DEFAULT_FREQUENCIA);
    }

    @Test
    @Transactional
    public void getAllInscricaos() throws Exception {
        // Initialize the database
        inscricaoRepository.saveAndFlush(inscricao);

        // Get all the inscricaos
        restInscricaoMockMvc.perform(get("/api/inscricaos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(inscricao.getId().intValue())))
                .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO_STR)))
                .andExpect(jsonPath("$.[*].grau").value(hasItem(DEFAULT_GRAU.doubleValue())))
                .andExpect(jsonPath("$.[*].frequencia").value(hasItem(DEFAULT_FREQUENCIA)));
    }

    @Test
    @Transactional
    public void getInscricao() throws Exception {
        // Initialize the database
        inscricaoRepository.saveAndFlush(inscricao);

        // Get the inscricao
        restInscricaoMockMvc.perform(get("/api/inscricaos/{id}", inscricao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inscricao.getId().intValue()))
            .andExpect(jsonPath("$.periodo").value(DEFAULT_PERIODO_STR))
            .andExpect(jsonPath("$.grau").value(DEFAULT_GRAU.doubleValue()))
            .andExpect(jsonPath("$.frequencia").value(DEFAULT_FREQUENCIA));
    }

    @Test
    @Transactional
    public void getNonExistingInscricao() throws Exception {
        // Get the inscricao
        restInscricaoMockMvc.perform(get("/api/inscricaos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInscricao() throws Exception {
        // Initialize the database
        inscricaoRepository.saveAndFlush(inscricao);
        int databaseSizeBeforeUpdate = inscricaoRepository.findAll().size();

        // Update the inscricao
        Inscricao updatedInscricao = inscricaoRepository.findOne(inscricao.getId());
        updatedInscricao
                .periodo(UPDATED_PERIODO)
                .grau(UPDATED_GRAU)
                .frequencia(UPDATED_FREQUENCIA);

        restInscricaoMockMvc.perform(put("/api/inscricaos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedInscricao)))
                .andExpect(status().isOk());

        // Validate the Inscricao in the database
        List<Inscricao> inscricaos = inscricaoRepository.findAll();
        assertThat(inscricaos).hasSize(databaseSizeBeforeUpdate);
        Inscricao testInscricao = inscricaos.get(inscricaos.size() - 1);
        assertThat(testInscricao.getPeriodo()).isEqualTo(UPDATED_PERIODO);
        assertThat(testInscricao.getGrau()).isEqualTo(UPDATED_GRAU);
        assertThat(testInscricao.getFrequencia()).isEqualTo(UPDATED_FREQUENCIA);
    }

    @Test
    @Transactional
    public void deleteInscricao() throws Exception {
        // Initialize the database
        inscricaoRepository.saveAndFlush(inscricao);
        int databaseSizeBeforeDelete = inscricaoRepository.findAll().size();

        // Get the inscricao
        restInscricaoMockMvc.perform(delete("/api/inscricaos/{id}", inscricao.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Inscricao> inscricaos = inscricaoRepository.findAll();
        assertThat(inscricaos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
