package br.com.dasa.printcore.oracle.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.dasa.printcore.oracle.model.EmpImg;

@Repository
public class EmpImgRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<EmpImg> listaEmpresas() {
        return jdbcTemplate.query("SELECT \"EmpImg_codigo\", \"EmpImg_nome\" FROM \"EmpImg\"",
                (rs, i) -> new EmpImg(rs.getInt(1), rs.getString(2)));


    }
}
