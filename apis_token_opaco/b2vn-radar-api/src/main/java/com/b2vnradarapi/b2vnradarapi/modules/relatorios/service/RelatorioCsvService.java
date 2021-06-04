package com.b2vnradarapi.b2vnradarapi.modules.relatorios.service;

import com.b2vnradarapi.b2vnradarapi.modules.radar.repository.BaseRadaresRepository;
import com.b2vnradarapi.b2vnradarapi.modules.radar.repository.ContagensRepository;
import com.b2vnradarapi.b2vnradarapi.modules.relatorios.dto.CsvContagensResponse;
import com.b2vnradarapi.b2vnradarapi.modules.relatorios.dto.CsvRadaresResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class RelatorioCsvService {

    private static final Integer TRES_DIAS_ATRAS = 3;

    @Autowired
    private BaseRadaresRepository baseRadaresRepository;
    @Autowired
    private ContagensRepository contagensRepository;

    public String gerarRelatorioCsvRadares() {
        AtomicReference<String> csv = new AtomicReference<>("");
        csv.set(CsvRadaresResponse.getCabecalho());
        baseRadaresRepository.findAll()
            .forEach(item -> {
                csv.set(csv + CsvRadaresResponse.getLinha(item));
                csv.set(csv + "\n");
            });
        return csv.get();
    }

    public String gerarRelatorioCsvContagens(String dataInicial, String dataFinal) {
        var dataI = LocalDateTime.now().minusDays(TRES_DIAS_ATRAS);
        var dataF = LocalDateTime.now().minusDays(TRES_DIAS_ATRAS);
        if (!isEmpty(dataInicial)) {
            dataI = LocalDateTime.parse(dataInicial);
        }
        if (!isEmpty(dataFinal)) {
            dataF = LocalDateTime.parse(dataFinal);
        }
        AtomicReference<String> csv = new AtomicReference<>("");
        csv.set(CsvContagensResponse.getCabecalho());
        contagensRepository.findByDataHoraBetween(dataI, dataF)
            .forEach(item -> {
                csv.set(csv + CsvContagensResponse.getLinha(item));
                csv.set(csv + "\n");
            });
        return csv.get();
    }
}
