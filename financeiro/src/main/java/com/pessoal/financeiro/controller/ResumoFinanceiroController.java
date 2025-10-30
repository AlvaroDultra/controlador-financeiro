package com.pessoal.financeiro.controller;

import com.pessoal.financeiro.service.DespesaService;
import com.pessoal.financeiro.service.ReceitaService;
import com.pessoal.financeiro.service.ResumoFinanceiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/resumo")
public class ResumoFinanceiroController {
    @Autowired
    private ReceitaService receitaService;

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private ResumoFinanceiroService resumoService;

    @GetMapping("/saldo")
    public BigDecimal saldoAtual() {
        return resumoService.calcularSaldoAtual();
    }

    @GetMapping("/despesas-por-categoria")
    public Map<String, BigDecimal> despesasPorCategoria() {
        return resumoService.despesasPorCategoria();
    }

    @GetMapping("/media-mensal-receitas")
    public BigDecimal mediaMensalReceitas() {
        return resumoService.mediaMensalReceitas();
    }

    @GetMapping("/media-mensal-despesas")
    public BigDecimal mediaMensalDespesas() {
        return resumoService.mediaMensalDespesas();
    }

    @DeleteMapping("/clear-all")
    public ResponseEntity<String> limparTudo() {
        receitaService.excluirTodas();
        despesaService.excluirTodas();
        return ResponseEntity.ok("Todas as receitas e despesas foram removidas.");
    }

}
