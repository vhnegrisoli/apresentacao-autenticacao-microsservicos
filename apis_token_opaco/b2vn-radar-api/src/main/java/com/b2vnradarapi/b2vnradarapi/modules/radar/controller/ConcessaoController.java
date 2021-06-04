package com.b2vnradarapi.b2vnradarapi.modules.radar.controller;

import com.b2vnradarapi.b2vnradarapi.modules.radar.model.BaseRadares;
import com.b2vnradarapi.b2vnradarapi.modules.radar.service.ConcessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/radares/concessoes")
public class ConcessaoController {

    @Autowired
    private ConcessaoService concessaoService;

    @GetMapping
    public List<Integer> buscarTodosOsLotes() {
        return concessaoService.buscarLotes();
    }

    @GetMapping("{lote}")
    public Page<BaseRadares> buscarPorLote(@PathVariable Integer lote,
                                           @RequestParam("page") Integer page,
                                           @RequestParam("size") Integer size) {
        return concessaoService.buscarPorLote(lote, page, size);
    }
}
