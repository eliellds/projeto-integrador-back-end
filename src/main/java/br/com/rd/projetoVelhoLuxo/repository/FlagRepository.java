package br.com.rd.projetoVelhoLuxo.repository;

import br.com.rd.projetoVelhoLuxo.model.Flag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlagRepository extends JpaRepository<Flag, Long> {
}
