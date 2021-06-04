package br.com.b2vnauthapi.b2vnauthapi.modules.log.controller;

import br.com.b2vnauthapi.b2vnauthapi.modules.log.model.Log;
import br.com.b2vnauthapi.b2vnauthapi.modules.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/log")
public class LogController {

    @Autowired
    private LogService logService;

    @PostMapping
    public void saveLog(@RequestBody Log log) {
        logService.processarLogDeUsuario(log);
    }

    @GetMapping("metodo/{metodo}/todos/paginacao")
    public Page<Log> buscarTodosPaginadosPorMetodo(@PathVariable String metodo,
                                                   @PathParam("page") Integer page,
                                                   @PathParam("size") Integer size) {
        return logService.buscarTodosPaginados(metodo, page, size);
    }
}
