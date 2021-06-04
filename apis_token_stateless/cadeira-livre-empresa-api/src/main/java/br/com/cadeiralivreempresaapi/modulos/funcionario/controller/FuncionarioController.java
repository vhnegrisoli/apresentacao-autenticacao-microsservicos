package br.com.cadeiralivreempresaapi.modulos.funcionario.controller;

import br.com.cadeiralivreempresaapi.modulos.comum.dto.PageRequest;
import br.com.cadeiralivreempresaapi.modulos.funcionario.dto.FuncionarioFiltros;
import br.com.cadeiralivreempresaapi.modulos.funcionario.dto.FuncionarioPageResponse;
import br.com.cadeiralivreempresaapi.modulos.funcionario.dto.FuncionarioResponse;
import br.com.cadeiralivreempresaapi.modulos.funcionario.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public Page<FuncionarioPageResponse> buscarTodos(PageRequest pageable, FuncionarioFiltros filtros) {
        return funcionarioService.buscarTodos(pageable, filtros);
    }

    @GetMapping("{id}")
    public FuncionarioResponse buscarPorId(@PathVariable Integer id) {
        return funcionarioService.buscarPorId(id);
    }
}
