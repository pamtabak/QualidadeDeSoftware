package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;

import com.mycompany.myapp.domain.SecretariaAcademica;
import com.mycompany.myapp.repository.SecretariaAcademicaRepository;

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
 * Test class for the SecretariaAcademicaResource REST controller.
 *
 * @see SecretariaAcademicaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class SecretariaAcademicaResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAA";
    private static final String UPDATED_NOME = "BBBBB";

    @Inject
    private SecretariaAcademicaRepository secretariaAcademicaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSecretariaAcademicaMockMvc;

    private SecretariaAcademica secretariaAcademica;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SecretariaAcademicaResource secretariaAcademicaResource = new SecretariaAcademicaResource();
        ReflectionTestUtils.setField(secretariaAcademicaResource, "secretariaAcademicaRepository", secretariaAcademicaRepository);
        this.restSecretariaAcademicaMockMvc = MockMvcBuilders.standaloneSetup(secretariaAcademicaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecretariaAcademica createEntity(EntityManager em) {
        SecretariaAcademica secretariaAcademica = new SecretariaAcademica()
                .nome(DEFAULT_NOME);
        return secretariaAcademica;
    }

    @Before
    public void initTest() {
        secretariaAcademica = createEntity(em);
    }

    @Test
    @Transactional
    public void createSecretariaAcademica() throws Exception {
        int databaseSizeBeforeCreate = secretariaAcademicaRepository.findAll().size();

        // Create the SecretariaAcademica

        restSecretariaAcademicaMockMvc.perform(post("/api/secretaria-academicas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(secretariaAcademica)))
                .andExpect(status().isCreated());

        // Validate the SecretariaAcademica in the database
        List<SecretariaAcademica> secretariaAcademicas = secretariaAcademicaRepository.findAll();
        assertThat(secretariaAcademicas).hasSize(databaseSizeBeforeCreate + 1);
        SecretariaAcademica testSecretariaAcademica = secretariaAcademicas.get(secretariaAcademicas.size() - 1);
        assertThat(testSecretariaAcademica.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void getAllSecretariaAcademicas() throws Exception {
        // Initialize the database
        secretariaAcademicaRepository.saveAndFlush(secretariaAcademica);

        // Get all the secretariaAcademicas
        restSecretariaAcademicaMockMvc.perform(get("/api/secretaria-academicas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(secretariaAcademica.getId().intValue())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }

    @Test
    @Transactional
    public void getSecretariaAcademica() throws Exception {
        // Initialize the database
        secretariaAcademicaRepository.saveAndFlush(secretariaAcademica);

        // Get the secretariaAcademica
        restSecretariaAcademicaMockMvc.perform(get("/api/secretaria-academicas/{id}", secretariaAcademica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(secretariaAcademica.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSecretariaAcademica() throws Exception {
        // Get the secretariaAcademica
        restSecretariaAcademicaMockMvc.perform(get("/api/secretaria-academicas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSecretariaAcademica() throws Exception {
        // Initialize the database
        secretariaAcademicaRepository.saveAndFlush(secretariaAcademica);
        int databaseSizeBeforeUpdate = secretariaAcademicaRepository.findAll().size();

        // Update the secretariaAcademica
        SecretariaAcademica updatedSecretariaAcademica = secretariaAcademicaRepository.findOne(secretariaAcademica.getId());
        updatedSecretariaAcademica
                .nome(UPDATED_NOME);

        restSecretariaAcademicaMockMvc.perform(put("/api/secretaria-academicas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSecretariaAcademica)))
                .andExpect(status().isOk());

        // Validate the SecretariaAcademica in the database
        List<SecretariaAcademica> secretariaAcademicas = secretariaAcademicaRepository.findAll();
        assertThat(secretariaAcademicas).hasSize(databaseSizeBeforeUpdate);
        SecretariaAcademica testSecretariaAcademica = secretariaAcademicas.get(secretariaAcademicas.size() - 1);
        assertThat(testSecretariaAcademica.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void deleteSecretariaAcademica() throws Exception {
        // Initialize the database
        secretariaAcademicaRepository.saveAndFlush(secretariaAcademica);
        int databaseSizeBeforeDelete = secretariaAcademicaRepository.findAll().size();

        // Get the secretariaAcademica
        restSecretariaAcademicaMockMvc.perform(delete("/api/secretaria-academicas/{id}", secretariaAcademica.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SecretariaAcademica> secretariaAcademicas = secretariaAcademicaRepository.findAll();
        assertThat(secretariaAcademicas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
