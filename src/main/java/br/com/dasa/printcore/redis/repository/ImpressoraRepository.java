package br.com.dasa.printcore.redis.repository;

import br.com.dasa.printcore.redis.model.Impressora;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImpressoraRepository extends CrudRepository<Impressora, String> {

    List<Impressora> findByUnidade(String unidade);
    Optional<Impressora> findById(String id);

}
