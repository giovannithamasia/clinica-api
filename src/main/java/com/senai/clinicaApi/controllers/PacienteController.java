package com.senai.clinicaApi.controllers;


import com.senai.clinicaApi.dto.PacienteDto;
import com.senai.clinicaApi.dto.PacienteRespostaDto;
import com.senai.clinicaApi.services.PacienteService;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }
    @PostMapping
    public ResponseEntity<String>criarPaciente(@RequestBody @Valid PacienteDto pacienteDto){
        boolean sucesso = pacienteService.inserirPaciente(pacienteDto);
        if (sucesso) {
            return ResponseEntity.ok("Paciente inserido com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe paciente");
        }
    }
    @GetMapping
    public ResponseEntity<List<PacienteRespostaDto>> listar() {
        List<PacienteRespostaDto> lista = pacienteService.obterPacientes();
        return ResponseEntity.ok(lista);
    }

}