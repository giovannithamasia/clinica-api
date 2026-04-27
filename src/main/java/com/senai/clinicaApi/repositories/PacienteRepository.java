package com.senai.clinicaApi.repositories;

import com.senai.clinicaApi.entities.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository <PacienteEntity, Long> {

}
