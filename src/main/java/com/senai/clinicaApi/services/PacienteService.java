package com.senai.clinicaApi.services;


import com.senai.clinicaApi.dto.PacienteDto;
import com.senai.clinicaApi.dto.PacienteRespostaDto;
import com.senai.clinicaApi.entities.PacienteEntity;
import com.senai.clinicaApi.exceptions.EmailDuplicadoException;
import com.senai.clinicaApi.exceptions.PacienteNaoEncontradoException;
import com.senai.clinicaApi.exceptions.PacientePossuiConsultasException;
import com.senai.clinicaApi.repositories.ConsultaRepository;
import com.senai.clinicaApi.repositories.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final ConsultaRepository consultaRepository;

    public PacienteService(PacienteRepository pacienteRepository, ConsultaRepository consultaRepository) {
        this.pacienteRepository = pacienteRepository;
        this.consultaRepository = consultaRepository;
    }

    @Transactional
    public boolean inserirPaciente(PacienteDto pacienteDto) {

        if (pacienteRepository.existsByEmail(pacienteDto.getEmail())) {
            throw new EmailDuplicadoException("Já existe paciente");
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

    public PacienteRespostaDto obterPaciente(String email) {

        PacienteEntity paciente = pacienteRepository.findByEmail(email)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));

        PacienteRespostaDto respostaDto = new PacienteRespostaDto();

        respostaDto.setId(paciente.getId());
        respostaDto.setNome(paciente.getNome());
        respostaDto.setEmail(paciente.getEmail());

        return respostaDto;
    }

    @Transactional
    public boolean atualizarPaciente(String email, PacienteDto pacienteDto) {

        PacienteEntity paciente = pacienteRepository.findByEmail(email)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));

        if (pacienteRepository.existsByEmailAndIdNot(pacienteDto.getEmail(), paciente.getId())) {
            throw new EmailDuplicadoException("Já existe paciente com esse email");
        }

        paciente.setNome(pacienteDto.getNome());
        paciente.setEmail(pacienteDto.getEmail());

        pacienteRepository.save(paciente);

        return true;

    }


    @Transactional
    public boolean excluirPaciente(String email) {
        pacienteRepository.findByEmail(email)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));

        if (consultaRepository.existsByPacienteEmail(email)) {
            throw new PacientePossuiConsultasException("Paciente vinculado em consultas");
        }

        pacienteRepository.deleteByEmail(email);

        return true;
    }
}


