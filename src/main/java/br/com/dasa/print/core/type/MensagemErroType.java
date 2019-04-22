package br.com.dasa.print.core.type;

public enum MensagemErroType {

    ERRO_BUSCAR_EMPRESAS("Erro ao Buscar Empresas"),
    ERRO_BUSCAR_IMPRESSORAS("Erro ao Buscar Impressoras"),
    ERRO_CRIAR_FILA("Erro ao Criar Fila"),
    ERRO_APAGAR_FILA("Erro ao Apagar Fila"),
    ERRO_PREPARANDO_CONTEUDO_PARA_IMPRESSAO("Erro ao Preparar Conteudo para Impressao"),
    ERRO_ENVIAR_IMPRESSAO_IMPRESSORA("Erro ao enviar mensagem para impressora"),
    ERRO_SALVAR_IMPRESSORA ("Erro ao salvar impressora"),
    ERRO_DELETAR_IMPRESSORA("Erro ao apagar impressora"),
    ERRO_BUSCAR_IMPRESSORA_POR_UNIDADE("Erro ao buscar unidade"),
    ERRO_INSERIR_IMPRESSORA_POR_UNIDADE("Não foi possivel inserir impressora na lista da unidade" ),
    ERRO_EXCLUIR_IMPRESSORA_POR_UNIDADE("Não foi possivel excluir impressora da unidade");


    private String mensagemErro;

    private MensagemErroType(String mensagemErro){
        this.mensagemErro = mensagemErro;

    }

    public String getMensagem() {
        return mensagemErro;
    }
}
