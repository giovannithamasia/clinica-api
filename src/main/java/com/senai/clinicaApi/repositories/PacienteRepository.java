package com.senai.clinicaApi.repositories;

import com.senai.clinicaApi.entities.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository <PacienteEntity, Long> {

   Optional<PacienteEntity> findByEmail(String email);

   @Modifying
   @Transactional
   void deleteByEmail(String email);

   boolean existsByEmail(String email);

   boolean existsByEmailAndIdNot(String email, Long id);

}
