package br.com.dasa.job.job;

import br.com.dasa.job.model.Impressora;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImpressoraJob implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImpressoraJob.class);

    @Value("${job-config.minimo-horas-consideras-para-exclusao}")
    private int minimoHorasConsideradasParaExclusao;

    private final String URL_DELETAR_IMPRESSORAS = "http://localhost:9090/impressora/identificacao";

    private final String URL_BUSCAR_IMPRESSORAS = "http://localhost:9090/impressora";

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Responsável por orquestrar chamadas de verificacao de impressoras ativas
     * e exclusao de impressoras ativas
     */
    @Scheduled(cron = "${job-config.cron}")
    public void orquestraJobImpressao() {
        List<Impressora> listaImpressorasAtivas = listaImpressorasAtivas();

        if (!listaImpressorasAtivas.isEmpty())
            deletaImpressorasAtivas(calculaDiferencaHoras(listaImpressorasAtivas));
        else
            LOGGER.info("Não existe(m) impressora(s) inativa(s).");
    }


    /**
     * Metodo Responsável por calcular diferenca de Horas
     *
     * @param impressoras
     * @return listaImpressorasQueSeraoExcluidas
     */
    public List<Impressora> calculaDiferencaHoras(List<Impressora> impressoras) {
        LOGGER.info("Iniciando calculo de diferenca de horas");
        LocalDateTime horaAtual = LocalDateTime.now();
        List<Impressora> listaImpressorasQueSeraoExcluidas = new ArrayList<>();

        for (Impressora impressora : impressoras) {
            long diferencaEntreHoraAtualeUltimaAtualizacaoImpressora = Duration.between(impressora.getUltimaAtualizacao(), horaAtual).toHours();

            if (diferencaEntreHoraAtualeUltimaAtualizacaoImpressora > minimoHorasConsideradasParaExclusao)
                listaImpressorasQueSeraoExcluidas.add(impressora);
        }
        LOGGER.info("Calculo de horas realizado com sucesso.");
        return listaImpressorasQueSeraoExcluidas;
    }

    /**
     * Resposável por listas todas as impressoras que estão ativas
     *
     * @return listaImpressorasAtivas
     */
    public List<Impressora> listaImpressorasAtivas() {
        List<Impressora> listaImpressorasAtivas = null;
        try {
            LOGGER.info("Iniciando busca de impressora(s) ativa(s)");
            ResponseEntity<List<Impressora>> response = restTemplate.exchange(URL_BUSCAR_IMPRESSORAS,
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Impressora>>() {
                    });
            listaImpressorasAtivas = response.getBody();

        } catch (Exception e) {
            LOGGER.error("Erro ao listar impressora(s) ativa(s) ", e);
        }
        LOGGER.info("Impressora(s) listada(s) com sucesso");
        return listaImpressorasAtivas;
    }

    /**
     * Responsável por deletar impressoras inativas
     *
     * @param listaImpressorasDevemSerExcluidas
     */
    public void deletaImpressorasAtivas(List<Impressora> listaImpressorasDevemSerExcluidas) {
        try {
            LOGGER.info("Excluindo {} Impressora(s) Inativa(s)." , listaImpressorasDevemSerExcluidas.size());
            listaImpressorasDevemSerExcluidas.forEach(excluirImpressoraPor -> restTemplate.delete(URL_DELETAR_IMPRESSORAS + "/" + excluirImpressoraPor.getIdentificacao()));

            LOGGER.info("Impressora(as) inativa(s) excluidas com sucesso.");

        } catch (Exception e) {
            LOGGER.error("Erro ao excluir impressora(s) ativa(s) ", e);
        }

    }
}
