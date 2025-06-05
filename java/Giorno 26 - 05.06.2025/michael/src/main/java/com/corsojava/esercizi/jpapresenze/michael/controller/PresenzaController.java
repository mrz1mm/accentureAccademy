package com.corsojava.esercizi.jpapresenze.michael.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.corsojava.esercizi.jpapresenze.michael.dto.GiustificaAssenzaRequestDto;
import com.corsojava.esercizi.jpapresenze.michael.dto.PresenzaRequestDto;
import com.corsojava.esercizi.jpapresenze.michael.model.Presenza;
import com.corsojava.esercizi.jpapresenze.michael.service.PresenzaService;

@RestController
@RequestMapping("/api/presenze")
public class PresenzaController {

    private final PresenzaService presenzaService;

    public PresenzaController(PresenzaService presenzaService) {
        this.presenzaService = presenzaService;
    }

    @GetMapping("assenze/count")
    public long contaAssenzeStudente(
        @RequestParam Long idStudente,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInizio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFine) {
    return presenzaService.contaAssenzeStudente(idStudente, dataInizio, dataFine);
    }

    @GetMapping("/assenze/studenti-con-molte-assenze")
    public List<Long> getStudentiConTroppeAssenze(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInizio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFine,
        @RequestParam int sogliaAssenze) {
        return presenzaService.getStudentiConTroppeAssenze(dataInizio, dataFine, sogliaAssenze);
    }

    @PostMapping("/registra")
    public Presenza registraPresenza(@RequestBody PresenzaRequestDto requestDto) {
        return presenzaService.registraPresenza(requestDto);
    }

    @PostMapping("/giustifica")
    public Presenza giustificaAssenza(@RequestBody GiustificaAssenzaRequestDto requestDto) {
        return presenzaService.giustificaAssenza(requestDto);
    }

}
