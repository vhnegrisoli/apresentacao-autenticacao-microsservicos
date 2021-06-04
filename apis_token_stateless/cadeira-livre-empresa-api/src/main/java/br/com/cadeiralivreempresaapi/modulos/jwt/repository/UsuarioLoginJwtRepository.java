package br.com.cadeiralivreempresaapi.modulos.jwt.repository;

import br.com.cadeiralivreempresaapi.modulos.jwt.model.UsuarioLoginJwt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioLoginJwtRepository extends JpaRepository<UsuarioLoginJwt, String> {
}
