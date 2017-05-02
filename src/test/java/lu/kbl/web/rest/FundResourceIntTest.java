package lu.kbl.web.rest;

import lu.kbl.KbldemoApp;

import lu.kbl.domain.Fund;
import lu.kbl.repository.FundRepository;
import lu.kbl.service.FundService;
import lu.kbl.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FundResource REST controller.
 *
 * @see FundResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KbldemoApp.class)
public class FundResourceIntTest {

    private static final String DEFAULT_RANGE = "AAAAAAAAAA";
    private static final String UPDATED_RANGE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private FundRepository fundRepository;

    @Autowired
    private FundService fundService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFundMockMvc;

    private Fund fund;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FundResource fundResource = new FundResource(fundService);
        this.restFundMockMvc = MockMvcBuilders.standaloneSetup(fundResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fund createEntity(EntityManager em) {
        Fund fund = new Fund()
            .range(DEFAULT_RANGE)
            .name(DEFAULT_NAME);
        return fund;
    }

    @Before
    public void initTest() {
        fund = createEntity(em);
    }

    @Test
    @Transactional
    public void createFund() throws Exception {
        int databaseSizeBeforeCreate = fundRepository.findAll().size();

        // Create the Fund
        restFundMockMvc.perform(post("/api/funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fund)))
            .andExpect(status().isCreated());

        // Validate the Fund in the database
        List<Fund> fundList = fundRepository.findAll();
        assertThat(fundList).hasSize(databaseSizeBeforeCreate + 1);
        Fund testFund = fundList.get(fundList.size() - 1);
        assertThat(testFund.getRange()).isEqualTo(DEFAULT_RANGE);
        assertThat(testFund.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createFundWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fundRepository.findAll().size();

        // Create the Fund with an existing ID
        fund.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFundMockMvc.perform(post("/api/funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fund)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Fund> fundList = fundRepository.findAll();
        assertThat(fundList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRangeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fundRepository.findAll().size();
        // set the field null
        fund.setRange(null);

        // Create the Fund, which fails.

        restFundMockMvc.perform(post("/api/funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fund)))
            .andExpect(status().isBadRequest());

        List<Fund> fundList = fundRepository.findAll();
        assertThat(fundList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fundRepository.findAll().size();
        // set the field null
        fund.setName(null);

        // Create the Fund, which fails.

        restFundMockMvc.perform(post("/api/funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fund)))
            .andExpect(status().isBadRequest());

        List<Fund> fundList = fundRepository.findAll();
        assertThat(fundList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFunds() throws Exception {
        // Initialize the database
        fundRepository.saveAndFlush(fund);

        // Get all the fundList
        restFundMockMvc.perform(get("/api/funds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fund.getId().intValue())))
            .andExpect(jsonPath("$.[*].range").value(hasItem(DEFAULT_RANGE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getFund() throws Exception {
        // Initialize the database
        fundRepository.saveAndFlush(fund);

        // Get the fund
        restFundMockMvc.perform(get("/api/funds/{id}", fund.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fund.getId().intValue()))
            .andExpect(jsonPath("$.range").value(DEFAULT_RANGE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFund() throws Exception {
        // Get the fund
        restFundMockMvc.perform(get("/api/funds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFund() throws Exception {
        // Initialize the database
        fundService.save(fund);

        int databaseSizeBeforeUpdate = fundRepository.findAll().size();

        // Update the fund
        Fund updatedFund = fundRepository.findOne(fund.getId());
        updatedFund
            .range(UPDATED_RANGE)
            .name(UPDATED_NAME);

        restFundMockMvc.perform(put("/api/funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFund)))
            .andExpect(status().isOk());

        // Validate the Fund in the database
        List<Fund> fundList = fundRepository.findAll();
        assertThat(fundList).hasSize(databaseSizeBeforeUpdate);
        Fund testFund = fundList.get(fundList.size() - 1);
        assertThat(testFund.getRange()).isEqualTo(UPDATED_RANGE);
        assertThat(testFund.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingFund() throws Exception {
        int databaseSizeBeforeUpdate = fundRepository.findAll().size();

        // Create the Fund

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFundMockMvc.perform(put("/api/funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fund)))
            .andExpect(status().isCreated());

        // Validate the Fund in the database
        List<Fund> fundList = fundRepository.findAll();
        assertThat(fundList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFund() throws Exception {
        // Initialize the database
        fundService.save(fund);

        int databaseSizeBeforeDelete = fundRepository.findAll().size();

        // Get the fund
        restFundMockMvc.perform(delete("/api/funds/{id}", fund.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fund> fundList = fundRepository.findAll();
        assertThat(fundList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fund.class);
    }
}
