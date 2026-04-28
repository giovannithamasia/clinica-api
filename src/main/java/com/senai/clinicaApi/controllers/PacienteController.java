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
       try {
              pacienteService.inserirPaciente(pacienteDto);
              return ResponseEntity.status(HttpStatus.CREATED).body("Paciente criado com sucesso");
       }catch (RuntimeException e) {
           return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
       }
    }
    @GetMapping
    public ResponseEntity<List<PacienteRespostaDto>> listar() {
        List<PacienteRespostaDto> lista = pacienteService.obterPacientes();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Object> obterPacientePorEmail(@PathVariable String email) {
        try {
            PacienteRespostaDto paciente = pacienteService.obterPacientePorEmail(email);
            return ResponseEntity.ok(paciente);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("{email}")
    public ResponseEntity<String>atualizarPaciente(@PathVariable String email, @RequestBody @Valid PacienteDto pacienteDto){

       try {
           pacienteService.atualizarPaciente(email, pacienteDto);
              return ResponseEntity.ok("Paciente atualizado com sucesso");
       }catch (IllegalArgumentException e){
           return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

    }catch (RuntimeException e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
       }
    }
    @DeleteMapping("{email}")
    public ResponseEntity<String>excluirPaciente(@PathVariable String email){
        try {
            pacienteService.excluirPaciente(email);
            return ResponseEntity.ok("Paciente excluído com sucesso");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}