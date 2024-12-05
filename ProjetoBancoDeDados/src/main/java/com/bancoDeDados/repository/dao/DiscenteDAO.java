package com.bancoDeDados.repository.dao;

import com.bancoDeDados.model.Discente;
import com.bancoDeDados.model.Pessoa;
import com.bancoDeDados.model.mapper.DiscenteRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DiscenteDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PessoaDAO pessoaDAO;

    public Discente buscarDiscenteCompletoPorId(Long discenteId) {
        String sql = "SELECT * FROM discente WHERE id_discente = ?";
        Discente discente = jdbcTemplate.queryForObject(sql, new DiscenteRowMapper(), discenteId);
        if (discente != null) {
            Pessoa pessoa = pessoaDAO.buscarPessoaComEnderecos(discente.getIdPessoa());
            discente.setPessoa(pessoa);
        }
        return discente;
    }

    public void inserirDiscente(Discente discente) {
        String sql = "INSERT INTO discente (pessoa_ID, registro_academico, data_ingresso, status) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, discente.getIdPessoa(), discente.getRegistroAcademico(),
                discente.getDataIngresso(), discente.getStatusFormatado());
    }

//    public Optional<Discente> buscaPorId(Long idDiscente) {
//        String sql = "SELECT * FROM discente WHERE ID_discente = ?";
//        return jdbcTemplate.query(sql, rs -> {
//            if (rs.next()) {
//                Discente discente = new Discente();
//                discente.setIdDiscente(rs.getLong("ID_discente"));
//                discente.setIdPessoa(rs.getLong("pessoa_ID"));
//                discente.setRegistroAcademico(rs.getString("registro_academico"));
//                discente.setDataIngresso(LocalDate.parse(rs.getString("data_nascimento")));
//                discente.setStatus(Discente.StatusDiscente.valueOf(rs.getString("status")));
//                return Optional.of(discente);
//            }
//            return Optional.empty();
//        }, idDiscente);
//    }

    public int contarDiscentesCadastrados() {
        String sql = "SELECT COUNT(*) FROM discente";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
