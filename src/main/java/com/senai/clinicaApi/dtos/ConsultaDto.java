package com.senai.clinicaApi.dtos;

import com.senai.clinicaApi.entities.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ConsultaDto {

    @NotBlank(message = "O titulo é obrigatório")
    private String titulo;

    @NotNull(message = "A data da consulta é obrigatória")
    private LocalDateTime dataConsulta;

    @NotNull(message = "O status é obrigatório")
    private Status status;

    @NotBlank(message = "O email do paciente é obrigatório")
    private String emailPaciente;

    public ConsultaDto(String titulo, LocalDateTime dataConsulta, Status status, String emailPaciente) {
        this.titulo = titulo;
        this.dataConsulta = dataConsulta;
        this.status = status;
        this.emailPaciente = emailPaciente;
    }

    public ConsultaDto() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDateTime dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getEmailPaciente() {
        return emailPaciente;
    }

    public void setEmailPaciente(String emailPaciente) {
        this.emailPaciente = emailPaciente;
    }
}
