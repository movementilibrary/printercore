package br.com.dasa.printcore.oracle.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.dasa.printcore.oracle.model.Pc;

@Repository
public class PcRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Pc> listaUnidadePorCodigoEmpresa(String codigoEmpresa) {
		return jdbcTemplate.query(
				"SELECT p.\"Pc_Nome\", \"Pc_Mnemonico\" FROM \"Pc\" p "
						+ "WHERE \"Pc_Empresa\" = ? AND \"Pc_status\" = 1 "
						+ "AND (\"Pc_bloqueiaAdmissaoGliese\" is null or \"Pc_bloqueiaAdmissaoGliese\" <> 1) "
						+ "ORDER BY \"Pc_Mnemonico\"",
				new Object[] { codigoEmpresa },
				(resultSet, i) -> new Pc(resultSet.getString(1), resultSet.getString(2)));

	}
}
