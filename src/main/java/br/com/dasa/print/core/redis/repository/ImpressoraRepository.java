package br.com.dasa.print.core.redis.repository;

import br.com.dasa.print.core.redis.model.Impressora;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImpressoraRepository extends CrudRepository<Impressora, String> {

    List<Impressora> findByUnidade(String unidade);
    Optional<Impressora> findById(String id);

}
