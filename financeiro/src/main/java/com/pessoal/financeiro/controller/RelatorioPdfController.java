package com.pessoal.financeiro.controller;

import com.pessoal.financeiro.model.Despesa;
import com.pessoal.financeiro.model.Receita;
import com.pessoal.financeiro.repository.DespesaRepository;
import com.pessoal.financeiro.repository.ReceitaRepository;
import com.pessoal.financeiro.service.ResumoFinanceiroService;
import com.pessoal.financeiro.util.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/relatorio")
public class RelatorioPdfController {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private ResumoFinanceiroService resumoService;

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> gerarPdf() {

        List<Receita> receitas = receitaRepository.findAll();
        List<Despesa> despesas = despesaRepository.findAll();

        ByteArrayInputStream pdf = PdfGenerator.gerarRelatorio(
                receitas,
                despesas,
                resumoService.calcularSaldoAtual()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=relatorio-financeiro.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf.readAllBytes());
    }
}
