package br.com.dasa.printcore.type;

public enum MensagemInfoType {

    BUSCANDO_EMPRESAS("Buscando Empresas"),
    BUSCANDO_IMPRESSORAS("Buscando Impressoras"),
    ATUALIZANDO_HORARIO_IMPRESSORA("Atualizando Horario Impressora"),
    CRIANDO_FILA("Criando Fila"),
    APAGANDO_FILA("Apagando Fila"),
    PREPARANDO_CONTEUDO_PARA_IMPRESSAO ("Preparando Conteudo para Impressao"),
    ENVIANDO_IMPRESSAO_PARA_IMPRESSORA("Enviando impressao para Impressora"),
    SALVANDO_IMPRESSORA("Salvando impressora"),
    DELETANDO_IMPRESSORA("Deletando impressora"),
    IMPRESSORA_NOT_FOUND("N�o foi possivel encontrar impressora"),
    BUSCANDO_UNIDADE_POR_EMPRESA("Buscando Unidade por CodigoEmpresa"),
    INSERINDO_IMPRESSORA_POR_UNIDADE("Inserindo impressora na lista da unidade"),
    BUSCANDO_IMPRESSORA_POR_UNIDADE ("Buscando impressoras da unidade"),
    EXCLUINDO_IMPRESSORA_POR_UNIDADE("Excluindo impressora da unidade");

    private String mensagemInfo;

    private MensagemInfoType(String mensagemInfo){
        this.mensagemInfo = mensagemInfo;

    }

    public String getMensagem() {
        return mensagemInfo;
    }
}
