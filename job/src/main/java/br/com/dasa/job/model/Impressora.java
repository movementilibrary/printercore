package br.com.dasa.job.model;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;



public class Impressora{


    private String identificacao;
    private LocalDateTime ultimaAtualizacao;
    private String unidade;
    private String nome;
    private String empresa;

    public Impressora() { }


    public Impressora(String identificacao, LocalDateTime ultimaAtualizacao,String unidade,String nome,String empresa) {
        this.identificacao = identificacao;
        this.ultimaAtualizacao = ultimaAtualizacao;
        this.unidade = unidade;
        this.nome = nome;
        this.empresa = empresa;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
}