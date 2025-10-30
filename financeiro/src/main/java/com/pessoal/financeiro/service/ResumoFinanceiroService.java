package com.pessoal.financeiro.service;

import com.pessoal.financeiro.model.Despesa;
import com.pessoal.financeiro.model.Receita;
import com.pessoal.financeiro.model.Categoria;
import com.pessoal.financeiro.repository.DespesaRepository;
import com.pessoal.financeiro.repository.ReceitaRepository;
import com.pessoal.financeiro.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResumoFinanceiroService {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // 1. Saldo atual
    public BigDecimal calcularSaldoAtual() {
        BigDecimal totalReceitas = receitaRepository.findAll().stream()
                .map(Receita::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDespesas = despesaRepository.findAll().stream()
                .map(Despesa::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalReceitas.subtract(totalDespesas);
    }

    // 2. Total de despesas por categoria
    public Map<String, BigDecimal> despesasPorCategoria() {
        List<Despesa> despesas = despesaRepository.findAll();

        return despesas.stream()
                .collect(Collectors.groupingBy(
                        d -> d.getCategoria().getNome(),
                        Collectors.mapping(Despesa::getValor, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
                ));
    }

    // 3. Média mensal de receitas
    public BigDecimal mediaMensalReceitas() {
        List<Receita> receitas = receitaRepository.findAll();

        Map<YearMonth, List<Receita>> agrupadoPorMes = receitas.stream()
                .collect(Collectors.groupingBy(r -> YearMonth.from(r.getData())));

        BigDecimal total = receitas.stream()
                .map(Receita::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return agrupadoPorMes.isEmpty()
                ? BigDecimal.ZERO
                : total.divide(BigDecimal.valueOf(agrupadoPorMes.size()), 2, BigDecimal.ROUND_HALF_UP);
    }

    // 4. Média mensal de despesas
    public BigDecimal mediaMensalDespesas() {
        List<Despesa> despesas = despesaRepository.findAll();

        Map<YearMonth, List<Despesa>> agrupadoPorMes = despesas.stream()
                .collect(Collectors.groupingBy(d -> YearMonth.from(d.getData())));

        BigDecimal total = despesas.stream()
                .map(Despesa::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return agrupadoPorMes.isEmpty()
                ? BigDecimal.ZERO
                : total.divide(BigDecimal.valueOf(agrupadoPorMes.size()), 2, BigDecimal.ROUND_HALF_UP);
    }
}
