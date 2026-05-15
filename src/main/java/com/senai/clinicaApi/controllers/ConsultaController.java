package com.senai.clinicaApi.controllers;

import com.senai.clinicaApi.dtos.ConsultaDto;
import com.senai.clinicaApi.dtos.ConsultaRespostaDto;
import com.senai.clinicaApi.exceptions.ConflitoAgendamentoException;
import com.senai.clinicaApi.exceptions.ConsultaNaoEncontradaException;
import com.senai.clinicaApi.exceptions.PacienteNaoEncontradoException;
import com.senai.clinicaApi.services.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ConsultaController {

    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @PostMapping("/consulta")
    public ResponseEntity<String> criarConsulta(@RequestBody @Valid ConsultaDto consultaDto){
        if (!consultaDto.getTipo().equals("ONLINE") && !consultaDto.getTipo().equals("PRESENCIAL")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de consulta inválido");
        }

        try{
            service.inserirConsulta(consultaDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Consulta inserida com sucesso");
        }catch(PacienteNaoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(ConflitoAgendamentoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/consultas")
    public ResponseEntity<Object> obterConsultas(){
        List<ConsultaRespostaDto> lista = service.obterConsultas();

        if (lista.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Lista vazia de Consultas");
        }

        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @PutMapping("/consulta/{id}")
    public ResponseEntity<String> atualizarConsulta(@PathVariable Long id,
                                                    @RequestBody @Valid ConsultaDto consultaDto){
        try{
            service.atualizarConsulta(id,consultaDto);
            return ResponseEntity.status(HttpStatus.OK).body("Consulta atualizada com sucesso");
        }catch(ConsultaNaoEncontradaException | PacienteNaoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(ConflitoAgendamentoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/consulta/{id}")
    public ResponseEntity<String> excluirConsulta(@PathVariable Long id){
        try{
            service.excluirConsulta(id);
            return ResponseEntity.status(HttpStatus.OK).body("Consulta excluída com sucesso");
        }catch(ConsultaNaoEncontradaException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/consulta/cancelar/{id}")
    public ResponseEntity<String> cancelarConsulta(@PathVariable Long id){
        try{
            service.cancelarConsulta(id);
            return ResponseEntity.status(HttpStatus.OK).body("Consulta cancelada com sucesso");
        }catch (ConsultaNaoEncontradaException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
