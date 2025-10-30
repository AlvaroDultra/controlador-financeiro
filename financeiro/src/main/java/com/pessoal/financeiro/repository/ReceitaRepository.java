package com.pessoal.financeiro.repository;

import com.pessoal.financeiro.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
}
