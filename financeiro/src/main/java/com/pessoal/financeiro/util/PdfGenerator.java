package com.pessoal.financeiro.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.pessoal.financeiro.model.Despesa;
import com.pessoal.financeiro.model.Receita;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;

public class PdfGenerator {

    public static ByteArrayInputStream gerarRelatorio(
            List<Receita> receitas,
            List<Despesa> despesas,
            BigDecimal saldo) {

        Document doc = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(doc, out);
            doc.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normal = FontFactory.getFont(FontFactory.HELVETICA, 11);

            Paragraph title = new Paragraph("Relatório Financeiro", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);

            doc.add(new Paragraph(" ")); // espaço

            // Receitas
            doc.add(new Paragraph("Receitas", bold));
            PdfPTable tableReceitas = new PdfPTable(3);
            tableReceitas.setWidthPercentage(100);
            tableReceitas.setSpacingBefore(5);

            addHeader(tableReceitas, "Descrição");
            addHeader(tableReceitas, "Valor");
            addHeader(tableReceitas, "Data");

            for (Receita r : receitas) {
                tableReceitas.addCell(r.getDescricao());
                tableReceitas.addCell("R$ " + r.getValor());
                tableReceitas.addCell(r.getData().toString());
            }

            doc.add(tableReceitas);
            doc.add(new Paragraph(" ")); // espaço

            // Despesas
            doc.add(new Paragraph("Despesas", bold));
            PdfPTable tableDespesas = new PdfPTable(3);
            tableDespesas.setWidthPercentage(100);
            tableDespesas.setSpacingBefore(5);

            addHeader(tableDespesas, "Descrição");
            addHeader(tableDespesas, "Valor");
            addHeader(tableDespesas, "Data");

            for (Despesa d : despesas) {
                tableDespesas.addCell(d.getDescricao());
                tableDespesas.addCell("R$ " + d.getValor());
                tableDespesas.addCell(d.getData().toString());
            }

            doc.add(tableDespesas);
            doc.add(new Paragraph(" "));

            // Saldo
            Paragraph saldoFinal = new Paragraph("Saldo Atual: R$ " + saldo, bold);
            saldoFinal.setAlignment(Element.ALIGN_RIGHT);
            doc.add(saldoFinal);

            doc.close();

        } catch (DocumentException ex) {
            throw new RuntimeException("Erro ao gerar PDF", ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private static void addHeader(PdfPTable table, String title) {
        PdfPCell cell = new PdfPCell();
        cell.setPhrase(new Phrase(title, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        table.addCell(cell);
    }
}
