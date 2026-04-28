package com.senai.clinicaApi.controllers;

import com.senai.clinicaApi.dto.PacienteDto;
import com.senai.clinicaApi.dto.PacienteRespostaDto;
import com.senai.clinicaApi.exceptions.EmailDuplicadoException;
import com.senai.clinicaApi.exceptions.PacienteNaoEncontradoException;
import com.senai.clinicaApi.services.PacienteService;
import jakarta.validation.Valid;
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
    public ResponseEntity<String> criarPaciente(@RequestBody @Valid PacienteDto pacienteDto) {
        try {
            pacienteService.inserirPaciente(pacienteDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Paciente inserido com sucesso");
        } catch (EmailDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> obterPacientes() {
        List<PacienteRespostaDto> lista = pacienteService.obterPacientes();

        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista vazia de pacientes");
        }

        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Object> obterPaciente(@PathVariable String email) {
        try {
            PacienteRespostaDto paciente = pacienteService.obterPaciente(email);
            return ResponseEntity.status(HttpStatus.OK).body(paciente);
        } catch (PacienteNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{email}")
    public ResponseEntity<String> atualizarPaciente(@PathVariable String email, @RequestBody @Valid PacienteDto pacienteDto) {
        try {
            pacienteService.atualizarPaciente(email, pacienteDto);
            return ResponseEntity.status(HttpStatus.OK).body("Paciente atualizado com sucesso");
        } catch (EmailDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (PacienteNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> excluirPaciente(@PathVariable String email) {
        try {
            pacienteService.excluirPaciente(email);
            return ResponseEntity.status(HttpStatus.OK).body("Paciente excluído com sucesso");
        } catch (PacienteNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}