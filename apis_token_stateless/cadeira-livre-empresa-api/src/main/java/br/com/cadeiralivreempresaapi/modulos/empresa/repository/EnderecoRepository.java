package br.com.cadeiralivreempresaapi.modulos.empresa.repository;

import br.com.cadeiralivreempresaapi.modulos.empresa.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    List<Endereco> findByEmpresaId(Integer empresaId);
}
