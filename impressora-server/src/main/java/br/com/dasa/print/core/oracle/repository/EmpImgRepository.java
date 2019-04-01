package br.com.dasa.print.core.oracle.repository;

import br.com.dasa.print.core.oracle.model.EmpImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmpImgRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<EmpImg> listaEmpresas() {
        return jdbcTemplate.query("SELECT \"EmpImg_codigo\", \"EmpImg_nome\" FROM \"EmpImg\"",
                (rs, i) -> new EmpImg(rs.getInt(1), rs.getString(2)));
    }
}
