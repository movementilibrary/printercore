package br.com.dasa.print.core.h2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.dasa.print.core.h2.model.Impressora;

@Repository
public interface ImpressoraRepository extends CrudRepository<Impressora, String> {

    List<Impressora> findByUnidade(String unidade);
    Optional<Impressora> findByIdentificacao(String identificacao);

}
