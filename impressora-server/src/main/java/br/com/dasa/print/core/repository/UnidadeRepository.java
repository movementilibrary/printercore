package br.com.dasa.print.core.repository;

import br.com.dasa.print.core.model.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnidadeRepository extends JpaRepository<Unidade, Integer> {
    List<Unidade>findByIdentificacao(Integer identificacao);

}
