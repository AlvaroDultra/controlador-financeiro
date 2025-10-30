package com.pessoal.financeiro.util;

import com.pessoal.financeiro.model.Despesa;
import com.pessoal.financeiro.model.Receita;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class ExcelGenerator {

    public static ByteArrayInputStream gerarRelatorio(List<Receita> receitas, List<Despesa> despesas, BigDecimal saldo) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Relatório Financeiro");
            int rowIdx = 0;

            // Cabeçalho
            Row header = sheet.createRow(rowIdx++);
            header.createCell(0).setCellValue("Tipo");
            header.createCell(1).setCellValue("Descrição");
            header.createCell(2).setCellValue("Valor");
            header.createCell(3).setCellValue("Data");

            // Receitas
            for (Receita r : receitas) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue("Receita");
                row.createCell(1).setCellValue(r.getDescricao());
                row.createCell(2).setCellValue(r.getValor().doubleValue());
                row.createCell(3).setCellValue(r.getData().toString());
            }

            // Despesas
            for (Despesa d : despesas) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue("Despesa");
                row.createCell(1).setCellValue(d.getDescricao());
                row.createCell(2).setCellValue(d.getValor().doubleValue());
                row.createCell(3).setCellValue(d.getData().toString());
            }

            // Saldo
            sheet.createRow(rowIdx++); // linha em branco
            Row saldoRow = sheet.createRow(rowIdx);
            saldoRow.createCell(0).setCellValue("Saldo Atual");
            saldoRow.createCell(2).setCellValue(saldo.doubleValue());

            // Autoajuste de colunas
            for (int i = 0; i <= 3; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar Excel", e);
        }
    }
}
