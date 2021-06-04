package br.com.cadeiralivreempresaapi.modulos.usuario.utils;

import br.com.cadeiralivreempresaapi.modulos.usuario.enums.EPermissao;

import java.util.List;

import static br.com.cadeiralivreempresaapi.modulos.usuario.enums.EPermissao.PROPRIETARIO;
import static br.com.cadeiralivreempresaapi.modulos.usuario.enums.EPermissao.SOCIO;

public interface PermissaoUtils {

    List<EPermissao> PERMISSOES_SOCIO_PROPRIETARIO = List.of(SOCIO, PROPRIETARIO);
}
