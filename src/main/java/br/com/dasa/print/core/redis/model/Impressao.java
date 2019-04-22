package br.com.dasa.print.core.redis.model;

import java.io.Serializable;

import br.com.dasa.print.core.type.TipoEtiquetaType;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.annotation.Id;


@RedisHash("Impressao")
//TODO: Colocar bean validation e escrever testes para validacao
public class Impressao implements Serializable {

	
	@Id
    private String impressora;
    private String conteudo_impressao;
    private TipoEtiquetaType tipo_etiqueta;

    public Impressao() {
        this(null, null);
    }

    public Impressao(String impressora, String conteudo_impressao) {
        this(impressora, conteudo_impressao, TipoEtiquetaType.PORTRAIT);
    }

    public Impressao(String impressora, String conteudo_impressao, TipoEtiquetaType tipo_etiqueta) {
        this.impressora = impressora;
        this.conteudo_impressao = conteudo_impressao;
        this.tipo_etiqueta = tipo_etiqueta;
    }

    public String getImpressora() {
        return impressora;
    }

    public void setImpressora(String impressora) {
        this.impressora = impressora;
    }

    public String getConteudo_impressao() {
        return conteudo_impressao;
    }

    public void setConteudo_impressao(String conteudo_impressao) {
        this.conteudo_impressao = conteudo_impressao;
    }

    public TipoEtiquetaType getTipo_etiqueta() {
        return tipo_etiqueta;
    }

    public void setTipo_etiqueta(TipoEtiquetaType tipo_etiqueta) {
        this.tipo_etiqueta = tipo_etiqueta;
    }
}
