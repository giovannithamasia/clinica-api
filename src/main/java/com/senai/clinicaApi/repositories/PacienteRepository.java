package com.senai.clinicaApi.repositories;

import com.senai.clinicaApi.entities.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository <PacienteEntity, Long> {

   Optional<PacienteEntity> findByEmail(String email);

   void deleteByEmail(String email);

   boolean existsByEmail(String email);

   boolean existsByEmailAndIdNot(String email, Long id);
}
