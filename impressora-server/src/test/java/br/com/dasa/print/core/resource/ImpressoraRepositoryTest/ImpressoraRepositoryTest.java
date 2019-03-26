package br.com.dasa.print.core.resource.ImpressoraRepositoryTest;

import br.com.dasa.print.core.model.Empresa;
import br.com.dasa.print.core.model.Impressora;
import br.com.dasa.print.core.model.Unidade;
import br.com.dasa.print.core.repository.ImpressoraRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michel Marciano
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ImpressoraRepositoryTest {

    @Autowired
    private ImpressoraRepository impressoraRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Impressora impressora;
    private Impressora impressora1;
    private Impressora impressora2;
    private Unidade unidade;
    private Empresa empresa;

    @Before
    public void setUp() {
        impressora = new Impressora("SHA01",new Date(), "Paulista", "Impress01", "Delboni");
        impressora1 = new Impressora("SHA02",new Date(), "Paulista", "Impress02", "Alta");
        impressora2 = new Impressora("SHA02",new Date(), null, null, null);
    }

    @Test
    public void deveCriarNovaImpressora() {
        this.impressoraRepository.save(impressora);
        assertThat(impressora.getIdentificacao()).isEqualTo("SHA01");
        assertThat(impressora.getEmpresa()).isEqualTo("Delboni");
        assertThat(impressora.getUnidade()).isEqualTo("Paulista");
        assertThat(impressora.getIdentificacao()).isNotNull();
    }

    @Test
    public void deveAtualizarImpressora() {
        this.impressoraRepository.save(impressora);
        impressora.setIdentificacao("SHA02");
        impressora.setEmpresa("Alta");
        impressora.setNome("Impress02");
        this.impressoraRepository.save(impressora);
        assertThat(impressora.getIdentificacao()).isEqualTo("SHA02");
        assertThat(impressora.getNome()).isEqualTo("Impress02");
    }

    @Test
    public void deveRetornarExceptionCampoIdentificacao() {
        thrown.expect(ConstraintViolationException.class);
        this.impressoraRepository.save(impressora2);
        List<Impressora> listaImpressora = impressoraRepository.findByUnidade("Paulista");
    }

    @Test
    public void deveListarPelaUnidade() {
        this.impressoraRepository.save(impressora);
        this.impressoraRepository.save(impressora1);
        List<Impressora> listaImpressora = impressoraRepository.findByUnidade("Paulista");
        assertThat(listaImpressora.size()).isEqualTo(2);

    }



}
