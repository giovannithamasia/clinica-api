package com.senai.clinicaApi.dto;

import com.senai.clinicaApi.entities.Status;

import java.time.LocalDateTime;

public class ConsultaRespostaDto {

    private Long id;

    private String titulo;

    private LocalDateTime dataConsulta;

    private Status status;

    private PacienteRespostaDto paciente;

    public ConsultaRespostaDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public PacienteRespostaDto getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteRespostaDto paciente) {
        this.paciente = paciente;
    }
}
