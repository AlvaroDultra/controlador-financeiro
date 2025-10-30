package com.pessoal.financeiro.service;

import com.pessoal.financeiro.model.Despesa;
import com.pessoal.financeiro.repository.DespesaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    public Despesa salvar(Despesa despesa) {
        return despesaRepository.save(despesa);
    }

    public List<Despesa> listarTodas() {
        return despesaRepository.findAll();
    }

    public Despesa buscarPorId(Long id) {
        return despesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));
    }

    public void excluir(Long id) {
        despesaRepository.deleteById(id);
    }

    @Transactional
    public void excluirTodas() {
        despesaRepository.deleteAll();
    }

    public BigDecimal calcularTotal() {
        return despesaRepository.findAll().stream()
                .map(Despesa::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
