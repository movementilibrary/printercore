package br.com.dasa.print.core.oracle.repository;

import br.com.dasa.print.core.oracle.model.EmpImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpImgRepository extends JpaRepository<EmpImg, Integer> {

    @Query(value = "SELECT \"EmpImg_codigo\", \"EmpImg_nome\" FROM \"EmpImg\"", nativeQuery = true)
    List<EmpImg> listaEmpresas();
}
