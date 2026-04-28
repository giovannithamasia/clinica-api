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

    public boolean inserirPaciente(PacienteDto pacienteDto){

        PacienteEntity pacienteEntity = new PacienteEntity();

        pacienteEntity.setNome(pacienteDto.getNome());
        pacienteEntity.setEmail(pacienteDto.getEmail());

        pacienteRepository.save(pacienteEntity);
        return true;
    }
    public List<PacienteRespostaDto> obterPacientes(){

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
    public boolean atualizarPaciente(PacienteDto pacienteDto){

        PacienteEntity pacienteEntity = new PacienteEntity();

        pacienteEntity.setNome(pacienteDto.getNome());
        pacienteEntity.setEmail(pacienteDto.getEmail());

        pacienteRepository.save(pacienteEntity);
        return true;

    }
    public PacienteEntity obterPacientePorEmail(String email) {

        return pacienteRepository.findByEmail(email).orElse(null);
    }

     public boolean excluirPaciente(String email){

        PacienteEntity pacienteEntity = pacienteRepository.findByEmail(email).orElse(null);

        if (pacienteEntity != null) {
            pacienteRepository.delete(pacienteEntity);
            return true;
        }
        return false;

    }
}


