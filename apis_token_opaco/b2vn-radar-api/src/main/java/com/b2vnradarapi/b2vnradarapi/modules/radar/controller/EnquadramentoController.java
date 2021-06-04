package com.b2vnradarapi.b2vnradarapi.modules.radar.controller;

import com.b2vnradarapi.b2vnradarapi.modules.radar.model.BaseRadares;
import com.b2vnradarapi.b2vnradarapi.modules.radar.service.EnquadramentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/radares/enquadramento")
public class EnquadramentoController {

    @Autowired
    private EnquadramentoService enquadramentoService;

    @GetMapping("{enquadramento}")
    public Page<BaseRadares> buscarPorEnquadramentos(@PathVariable String enquadramento,
                                                     @RequestParam("page") Integer page,
                                                     @RequestParam("size") Integer size) {
        return enquadramentoService.buscarPorEnquadramento(enquadramento, page, size);
    }

    @GetMapping
    public List<String> buscarEnquadramentos() {
        return enquadramentoService.buscarEnquadramentos();
    }

}
