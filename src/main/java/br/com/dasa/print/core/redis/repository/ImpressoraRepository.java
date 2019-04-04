package br.com.dasa.print.core.redis.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.dasa.print.core.redis.model.Impressora;

@Repository
public interface ImpressoraRepository extends CrudRepository<Impressora, String> {

    List<Impressora> findByUnidade(String unidade);
    Optional<Impressora> findByIdentificacao(String identificacao);

}
