package com.b2vnradarapi.b2vnradarapi.modules.usuario.service;

import com.b2vnradarapi.b2vnradarapi.modules.log.dto.LogRequest;
import com.b2vnradarapi.b2vnradarapi.modules.usuario.client.UsuarioClient;
import com.b2vnradarapi.b2vnradarapi.modules.usuario.dto.UsuarioAutenticado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioClient usuarioClient;

    public UsuarioAutenticado getUsuarioAutenticado() {
        return usuarioClient.getUsuarioAutenticado();
    }

    public void enviarLogUsuario(LogRequest request) {
        usuarioClient.saveLog(request);
    }
}
