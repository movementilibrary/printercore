package br.com.dasa.print.core.resource.ImpressoraRepositoryTest;

/**
 * @author Michel Marciano
 */
//@RunWith(SpringRunner.class)
//@DataJpaTest
public class ImpressoraRepositoryTest {

//    @Autowired
//    private ImpressoraRepository impressoraRepository;
//
//    @Rule
//    public ExpectedException thrown = ExpectedException.none();
//
//    private Impressora impressora;
//    private Impressora impressora1;
//    private Impressora impressora2;
//    private Pc pc;
//    private EmpImg empImg;
//
//    @Before
//    public void setUp() {
//        impressora = new Impressora("SHA01", LocalDateTime.now(), "Paulista", "Impress01", "Delboni");
//        impressora1 = new Impressora("SHA02",LocalDateTime.now(), "Paulista", "Impress02", "Alta");
//        impressora2 = new Impressora("SHA02",LocalDateTime.now(), null, null, null);
//    }
//
//    @Test
//    public void deveCriarNovaImpressora() {
//        this.impressoraRepository.save(impressora);
//        assertThat(impressora.getMacaddress()).isEqualTo("SHA01");
//        assertThat(impressora.getEmpresa()).isEqualTo("Delboni");
//        assertThat(impressora.getUnidade()).isEqualTo("Paulista");
//        assertThat(impressora.getMacaddress()).isNotNull();
//    }
//
//    @Test
//    public void deveAtualizarImpressora() {
//        this.impressoraRepository.save(impressora);
//        impressora.setMacaddress("SHA02");
//        impressora.setEmpresa("Alta");
//        impressora.setNome("Impress02");
//        this.impressoraRepository.save(impressora);
//        assertThat(impressora.getMacaddress()).isEqualTo("SHA02");
//        assertThat(impressora.getNome()).isEqualTo("Impress02");
//    }
//
//    @Test
//    public void deveRetornarExceptionCampoIdentificacao() {
//        thrown.expect(ConstraintViolationException.class);
//        this.impressoraRepository.save(impressora2);
//        List<Impressora> listaImpressora = impressoraRepository.findByUnidade("Paulista");
//    }
//
//    @Test
//    public void deveListarPelaUnidade() {
//        this.impressoraRepository.save(impressora);
//        this.impressoraRepository.save(impressora1);
//        List<Impressora> listaImpressora = impressoraRepository.findByUnidade("Paulista");
//        assertThat(listaImpressora.size()).isEqualTo(2);
//
//    }
//
//

}
