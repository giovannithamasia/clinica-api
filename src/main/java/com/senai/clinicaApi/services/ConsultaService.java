package com.senai.clinicaApi.services;

import com.senai.clinicaApi.dtos.ConsultaDto;
import com.senai.clinicaApi.dtos.ConsultaRespostaDto;
import com.senai.clinicaApi.dtos.PacienteRespostaDto;
import com.senai.clinicaApi.entities.ConsultaEntity;
import com.senai.clinicaApi.entities.PacienteEntity;
import com.senai.clinicaApi.exceptions.ConflitoAgendamentoException;
import com.senai.clinicaApi.exceptions.ConsultaNaoEncontradaException;
import com.senai.clinicaApi.exceptions.PacienteNaoEncontradoException;
import com.senai.clinicaApi.repositories.ConsultaRepository;
import com.senai.clinicaApi.repositories.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;

    public ConsultaService(ConsultaRepository consultaRepository, PacienteRepository pacienteRepository) {
        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public boolean inserirConsulta(ConsultaDto dto){
        PacienteEntity paciente = pacienteRepository.findByEmail(dto.getEmailPaciente())
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente da consulta não encontrado"));

        if (consultaRepository.existsByPacienteAndDataConsulta(paciente,dto.getDataConsulta())){
            throw new ConflitoAgendamentoException("Paciente já possui consulta agendada para a data informada");
        }

        ConsultaEntity consulta = new ConsultaEntity();

        consulta.setTitulo(dto.getTitulo());
        consulta.setDataConsulta(dto.getDataConsulta());
        consulta.setStatus(dto.getStatus());
        consulta.setPaciente(paciente);

        consultaRepository.save(consulta);

        return true;
    }

    public List<ConsultaRespostaDto> obterConsultas(){
        List<ConsultaRespostaDto> listaDtos = new ArrayList<>();

        List<ConsultaEntity> listaEntities = consultaRepository.findAll();

        for(ConsultaEntity consulta:listaEntities){
            ConsultaRespostaDto consultaRespostaDto = new ConsultaRespostaDto();

            consultaRespostaDto.setId(consulta.getId());
            consultaRespostaDto.setTitulo(consulta.getTitulo());
            consultaRespostaDto.setDataConsulta(consulta.getDataConsulta());
            consultaRespostaDto.setStatus(consulta.getStatus());
            consultaRespostaDto.setPaciente(new PacienteRespostaDto
                    (consulta.getPaciente().getId(),
                            consulta.getPaciente().getNome(),
                            consulta.getPaciente().getEmail()));

            listaDtos.add(consultaRespostaDto);
        }

        return listaDtos;
    }

    public boolean atualizarConsulta(Long consultaId, ConsultaDto dto){
        ConsultaEntity consulta = consultaRepository.findById(consultaId)
                .orElseThrow(() -> new ConsultaNaoEncontradaException("Consulta não encontrada"));

        PacienteEntity paciente = pacienteRepository.findByEmail(dto.getEmailPaciente())
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente da consulta não encontrado"));


        if (consultaRepository.existsByPacienteAndDataConsultaAndIdNot(paciente,dto.getDataConsulta(),consultaId)){
            throw new ConflitoAgendamentoException
                    ("Paciente já possui consulta agendada para a data informada");
        }

        consulta.setTitulo(dto.getTitulo());
        consulta.setStatus(dto.getStatus());
        consulta.setPaciente(paciente);
        consulta.setDataConsulta(dto.getDataConsulta());

        consultaRepository.save(consulta);

        return true;
    }

    public boolean excluirConsulta(Long consultaId){
        consultaRepository.findById(consultaId)
                .orElseThrow(() -> new ConsultaNaoEncontradaException("Consulta não encontrada"));

        consultaRepository.deleteById(consultaId);

        return true;
    }
}
