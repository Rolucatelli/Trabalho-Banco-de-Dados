package com.bancoDeDados.repository.dao;

import com.bancoDeDados.model.Pagamento;
import com.bancoDeDados.model.mapper.EnderecoRowMapper;
import com.bancoDeDados.model.mapper.PagamentoRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class PagamentoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Pagamento> listarPagamentoPorPessoaId(Long id) {
        String sql = "SELECT * FROM pagamentos p WHERE p.discente_id = ? ORDER BY data_vencimento DESC";
        return jdbcTemplate.query(sql, new PagamentoRowMapper(), id);
    }

    public void atualizarPagamento(Long id, LocalDate dataPagamento) {
        String sql = "UPDATE pagamentos SET data_pagamento = ?, status_pagamento = 'pago' WHERE id_pagamento = ?";
        jdbcTemplate.update(sql, dataPagamento, id);
    }
}
