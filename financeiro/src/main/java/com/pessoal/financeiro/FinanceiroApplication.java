package com.pessoal.financeiro;

import com.pessoal.financeiro.model.Categoria;
import com.pessoal.financeiro.model.TipoCategoria;
import com.pessoal.financeiro.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pessoal.financeiro.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;

@SpringBootApplication
public class FinanceiroApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public static void main(String[] args) {
        SpringApplication.run(FinanceiroApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Categoria cat = Categoria.builder()
                .nome("Salário")
                .tipo(TipoCategoria.RECEITA)
                .build();

        categoriaRepository.save(cat);
    }
}
