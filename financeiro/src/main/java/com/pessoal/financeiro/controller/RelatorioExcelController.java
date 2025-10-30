package com.pessoal.financeiro.controller;

import com.pessoal.financeiro.model.Despesa;
import com.pessoal.financeiro.model.Receita;
import com.pessoal.financeiro.repository.DespesaRepository;
import com.pessoal.financeiro.repository.ReceitaRepository;
import com.pessoal.financeiro.service.ResumoFinanceiroService;
import com.pessoal.financeiro.util.ExcelGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/relatorio")
public class RelatorioExcelController {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private ResumoFinanceiroService resumoService;

    @GetMapping("/excel")
    public ResponseEntity<byte[]> gerarExcel() {
        List<Receita> receitas = receitaRepository.findAll();
        List<Despesa> despesas = despesaRepository.findAll();

        ByteArrayInputStream excel = ExcelGenerator.gerarRelatorio(receitas, despesas, resumoService.calcularSaldoAtual());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=relatorio-financeiro.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excel.readAllBytes());
    }
}
