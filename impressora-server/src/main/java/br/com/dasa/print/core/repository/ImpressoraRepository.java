package br.com.dasa.print.core.repository;

import br.com.dasa.print.core.model.Impressora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImpressoraRepository extends JpaRepository<Impressora, String> {

    List<Impressora> findByUnidade(String unidade);
    Optional<Impressora> findByIdentificacao(String identificacao);

}