package com.pessoal.financeiro.service;

import com.pessoal.financeiro.model.Receita;
import com.pessoal.financeiro.repository.ReceitaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;

    public Receita salvar(Receita receita) {
        return receitaRepository.save(receita);
    }

    public List<Receita> listarTodas() {
        return receitaRepository.findAll();
    }

    public Receita buscarPorId(Long id) {
        return receitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));
    }

    public void excluir(Long id) {
        receitaRepository.deleteById(id);
    }

    @Transactional
    public void excluirTodas() {
        receitaRepository.deleteAll();
    }
    public BigDecimal calcularTotal() {
        return receitaRepository.findAll().stream()
                .map(Receita::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
