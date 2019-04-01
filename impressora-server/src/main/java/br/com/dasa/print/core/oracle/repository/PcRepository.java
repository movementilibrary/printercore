package br.com.dasa.print.core.oracle.repository;

import br.com.dasa.print.core.oracle.model.Pc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PcRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Pc> listaUnidadePorCodigoEmpresa(String codigoEmpresa) {
        return jdbcTemplate.query("SELECT p.\"Pc_Empresa\", \"Pc_Mnemonico\" FROM \"Pc\" p " +
                "WHERE \"Pc_Empresa\" = ? AND \"Pc_status\" = 1 " +
                "AND (\"Pc_bloqueiaAdmissaoGliese\" is null or \"Pc_bloqueiaAdmissaoGliese\" <> 1) " +
                "ORDER BY \"Pc_Mnemonico\"", new Object[] {codigoEmpresa},
                (resultSet, i) -> new Pc(resultSet.getString(1), resultSet.getString(2)));
    }
}
