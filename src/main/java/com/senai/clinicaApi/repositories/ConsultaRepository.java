package com.senai.clinicaApi.repositories;

import com.senai.clinicaApi.entities.ConsultaEntity;
import com.senai.clinicaApi.entities.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ConsultaRepository extends JpaRepository<ConsultaEntity,Long> {

    boolean existsByPacienteAndDataConsulta(PacienteEntity paciente, LocalDate dataConsulta);

    boolean existsByPacienteAndDataConsultaAndIdNot(PacienteEntity paciente, LocalDate dataConsulta, Long id);

    boolean existsByPacienteEmail(String email);
}
