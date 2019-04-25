package br.com.dasa.printcore.redis.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.dasa.printcore.type.TipoEtiquetaType;


@RedisHash("Impressao")
public class Impressao implements Serializable {

	
	@Id
    private String impressora;
	@JsonProperty("conteudo_impressao")
    private String conteudoImpressao;
	@JsonProperty("tipo_etiqueta")
    private TipoEtiquetaType tipoEtiqueta;

    public Impressao() {
        this(null, null);
    }

    public Impressao(String impressora, String conteudo_impressao) {
        this(impressora, conteudo_impressao, TipoEtiquetaType.PORTRAIT);
    }

    public Impressao(String impressora, String conteudoImpressao, TipoEtiquetaType tipoEtiqueta) {
        this.impressora = impressora;
        this.conteudoImpressao = conteudoImpressao;
        this.tipoEtiqueta = tipoEtiqueta;
    }


    public String getImpressora() {
        return impressora;
    }

    public void setImpressora(String impressora) {
        this.impressora = impressora;
    }

    public String getConteudoImpressao() {
        return conteudoImpressao;
    }

    public void setConteudoImpressao(String conteudoImpressao) {
        this.conteudoImpressao = conteudoImpressao;
    }

    public TipoEtiquetaType getTipoEtiqueta() {
        return tipoEtiqueta;
    }

    public void setTipoEtiqueta(TipoEtiquetaType tipoEtiqueta) {
        this.tipoEtiqueta = tipoEtiqueta;
    }
}
