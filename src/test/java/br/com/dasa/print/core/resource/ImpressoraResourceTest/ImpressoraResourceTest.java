package br.com.dasa.print.core.resource.ImpressoraResourceTest;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
public class ImpressoraResourceTest {
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @MockBean
//    private ImpressoraRepository impressoraRepository;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    private Impressora impressora;

//    @Before
//    public void setUp() {
//        impressora =  new Impressora();
//        impressora.setIdentificacao("Impress01");
//        impressora.setUnidade("Delboni01");
//    }
//
//    @Test
//    public void criaImpressoraDeveRetornar201(){
//        ResponseEntity<Impressora> create = restTemplate.postForEntity("/impressora", impressora, Impressora.class);
//        assertThat(create.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//
//       }
//
//    @Test
//    public void deletaImpressoraDeveRetornar200(){
//        BDDMockito.doNothing().when().
//        ResponseEntity<Impressora> create = restTemplate.postForEntity("/impressora", impressora, Impressora.class);
//        restTemplate.exchange("/impressora/identificacao/{identificacao}" , DELETE, null, String.class , "Imprees01");
//    }
//
//
//    @Test
//    public void listaImpressoraPelaIdentificacao(){
//        BDDMockito.when(impressoraRepository.findByIdentificacao(impressora.getIdentificacao())).thenReturn(impressora);
//        ResponseEntity<Impressora> listaImpressora = restTemplate.getForEntity("/impressora/identificacao/{identificacao}", Impressora.class, "Impress01");
//        assertThat(listaImpressora.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertEquals("Delboni01", listaImpressora.getBody().getUnidade().equals("Delboni01"));
//
//    }



}
