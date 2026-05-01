package com.senai.clinicaApi.repositories;

import com.senai.clinicaApi.entities.ConsultaEntity;
import com.senai.clinicaApi.entities.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConsultaRepository extends JpaRepository<ConsultaEntity,Long> {

    boolean existsByPacienteAndDataConsulta(PacienteEntity paciente, LocalDateTime dataConsulta);

    boolean existsByPacienteAndDataConsultaAndIdNot(PacienteEntity paciente, LocalDateTime dataConsulta, Long id);

    boolean existsByPacienteEmail(String email);
}
