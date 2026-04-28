package com.senai.clinicaApi.services;


import com.senai.clinicaApi.dto.PacienteDto;
import com.senai.clinicaApi.dto.PacienteRespostaDto;
import com.senai.clinicaApi.entities.PacienteEntity;
import com.senai.clinicaApi.repositories.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public boolean inserirPaciente(PacienteDto pacienteDto) {

        if (pacienteRepository.existsByEmail(pacienteDto.getEmail())) {
            throw new RuntimeException("Paciente já existe");
        }

        PacienteEntity pacienteEntity = new PacienteEntity();

        pacienteEntity.setNome(pacienteDto.getNome());
        pacienteEntity.setEmail(pacienteDto.getEmail());

        pacienteRepository.save(pacienteEntity);
        return true;
    }

    public List<PacienteRespostaDto> obterPacientes() {

        List<PacienteEntity> entidades = pacienteRepository.findAll();
        List<PacienteRespostaDto> listaResposta = new ArrayList<>();

        for (PacienteEntity pacientes : entidades) {
            PacienteRespostaDto dto = new PacienteRespostaDto();
            dto.setId(pacientes.getId());
            dto.setNome(pacientes.getNome());
            dto.setEmail(pacientes.getEmail());

            listaResposta.add(dto);
        }
        return listaResposta;
    }

    public boolean atualizarPaciente(String email, PacienteDto pacienteDto) {

        PacienteEntity paciente = pacienteRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        if (pacienteRepository.existsByEmailAndIdNot(pacienteDto.getEmail(), paciente.getId())) {
            throw new IllegalArgumentException("Já existe paciente com esse email");
        }

        paciente.setNome(pacienteDto.getNome());
        paciente.setEmail(pacienteDto.getEmail());

        pacienteRepository.save(paciente);
        return true;

    }

    public PacienteRespostaDto obterPacientePorEmail(String email) {

        PacienteEntity paciente = pacienteRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        PacienteRespostaDto respostaDto = new PacienteRespostaDto();
        respostaDto.setId(paciente.getId());
        respostaDto.setNome(paciente.getNome());
        respostaDto.setEmail(paciente.getEmail());

        return respostaDto;
    }

    public boolean excluirPaciente(String email) {

        pacienteRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        pacienteRepository.deleteByEmail(email);
        return true;


    }
}


