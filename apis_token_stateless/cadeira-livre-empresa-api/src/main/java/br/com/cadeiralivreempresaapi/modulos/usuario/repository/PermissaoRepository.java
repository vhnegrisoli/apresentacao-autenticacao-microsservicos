package br.com.cadeiralivreempresaapi.modulos.usuario.repository;

import br.com.cadeiralivreempresaapi.modulos.usuario.enums.EPermissao;
import br.com.cadeiralivreempresaapi.modulos.usuario.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermissaoRepository extends JpaRepository<Permissao, Integer> {

    Optional<Permissao> findByPermissao(EPermissao permissao);

    List<Permissao> findByPermissaoIn(List<EPermissao> permissoes);
}
