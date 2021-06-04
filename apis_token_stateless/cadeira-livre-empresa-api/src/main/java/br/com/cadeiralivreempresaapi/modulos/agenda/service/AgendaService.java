package br.com.cadeiralivreempresaapi.modulos.agenda.service;

import br.com.cadeiralivreempresaapi.modulos.agenda.model.Agenda;
import br.com.cadeiralivreempresaapi.modulos.agenda.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.cadeiralivreempresaapi.modulos.agenda.messages.AgendaHorarioMessages.AGENDA_NAO_ENCONTRADA;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    public Agenda buscarAgendaPorId(Integer id) {
        return agendaRepository.findById(id)
            .orElseThrow(() -> AGENDA_NAO_ENCONTRADA);
    }
}