package br.com.b2vnauthapi.b2vnauthapi.modules.usuario.repository;

import br.com.b2vnauthapi.b2vnauthapi.modules.usuario.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissaoRepository extends JpaRepository<Permissao, Integer> {
}
