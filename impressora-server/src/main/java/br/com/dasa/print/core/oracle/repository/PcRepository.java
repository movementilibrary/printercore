package br.com.dasa.print.core.oracle.repository;

import br.com.dasa.print.core.oracle.model.EmpImg;
import br.com.dasa.print.core.oracle.model.Pc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PcRepository extends JpaRepository<Pc, String> {
    @Query(value = "SELECT \"Pc_Empresa\", \"Pc_Mnemonico\" FROM \"Pc\" WHERE \"Pc_Empresa\"=:codigoEmpresa", nativeQuery = true)
   // @Query("select u.empresa, u.mnemonico FROM Pc u WHERE u.empresa = :codigoEmpresa" )
    List<Pc> listaUnidadePorCodigoEmpresa(String codigoEmpresa);


}
