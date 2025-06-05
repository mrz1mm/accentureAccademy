package com.corsojava.esercizi.jpapresenze.michael.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corsojava.esercizi.jpapresenze.michael.dto.GiustificaAssenzaRequestDto;
import com.corsojava.esercizi.jpapresenze.michael.dto.PresenzaRequestDto;
import com.corsojava.esercizi.jpapresenze.michael.model.Presenza;
import com.corsojava.esercizi.jpapresenze.michael.model.StatoPresenza;
import com.corsojava.esercizi.jpapresenze.michael.repository.PresenzaRepository;


@Service
public class PresenzaService {

    private final PresenzaRepository presenzaRepository;
    private final StudenteService studenteService;

    public PresenzaService(PresenzaRepository presenzaRepository, StudenteService studenteService) {
        this.presenzaRepository = presenzaRepository;
        this.studenteService = studenteService;
    }

    @Transactional
    public Presenza registraPresenza(PresenzaRequestDto requestDto) {
        if (requestDto.getIdStudente() == null || requestDto.getData() == null || requestDto.getStato() == null) {
            return null;
        }

        if (!studenteService.esisteStudente(requestDto.getIdStudente())) {
            return null;
        }

        Presenza nuovaPresenza = new Presenza();
        
        nuovaPresenza.setIdStudente(requestDto.getIdStudente());
        nuovaPresenza.setData(requestDto.getData());
        nuovaPresenza.setStato(requestDto.getStato());
        nuovaPresenza.setNota(requestDto.getNota());
        return presenzaRepository.save(nuovaPresenza);
    }

    public long contaAssenzeStudente(Long idStudente, LocalDate dataInizio, LocalDate dataFine) {
        if (idStudente == null || dataInizio == null || dataFine == null || dataInizio.isAfter(dataFine)) {
            return -1;
        }

        if (!studenteService.esisteStudente(idStudente)) {
            return -1;
        }

        List<Presenza> assenze = presenzaRepository.findByIdStudenteAndDataBetweenAndStato(idStudente, dataInizio, dataFine, StatoPresenza.ASSENTE);
        return assenze.size();
    }

    @Transactional
    public Presenza giustificaAssenza(GiustificaAssenzaRequestDto requestDto) {
        if (requestDto.getIdStudente() == null || requestDto.getData() == null || requestDto.getNota() == null || requestDto.getNota().isBlank()) {
            return null;
        }

        if (!studenteService.esisteStudente(requestDto.getIdStudente())) {
            return null;
        }

        List<Presenza> presenzeTrovate = presenzaRepository.findByIdStudenteAndData(requestDto.getIdStudente(), requestDto.getData());

        if (presenzeTrovate.isEmpty()) {
            return null;
        }

        // Assumiamo che ci sia al massimo una registrazione per studente/data
        Presenza presenzaDaGiustificare = presenzeTrovate.stream()
            .filter(p -> p.getStato() == StatoPresenza.ASSENTE)
            .findFirst()
            .orElse(null);

        if (presenzaDaGiustificare == null) {
            return null;
        }

        presenzaDaGiustificare.setNota(requestDto.getNota());
        return presenzaRepository.save(presenzaDaGiustificare);
    }

    public List<Long> getStudentiConTroppeAssenze(LocalDate dataInizio, LocalDate dataFine, int sogliaAssenze) {
        if (dataInizio == null || dataFine == null || dataInizio.isAfter(dataFine) || sogliaAssenze < 0) {
            return List.of();
        }

        List<Presenza> tutteLeAssenzeNelPeriodo = presenzaRepository.findByDataBetweenAndStato(dataInizio, dataFine, StatoPresenza.ASSENTE);

        Map<Long, Long> conteggioAssenzePerStudente = tutteLeAssenzeNelPeriodo.stream()
            .collect(Collectors.groupingBy(Presenza::getIdStudente, Collectors.counting()));
        
        return conteggioAssenzePerStudente.entrySet().stream()
            .filter(entry -> entry.getValue() > sogliaAssenze)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }


}
