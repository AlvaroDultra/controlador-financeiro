package com.pessoal.financeiro.repository;

import com.pessoal.financeiro.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {
}
