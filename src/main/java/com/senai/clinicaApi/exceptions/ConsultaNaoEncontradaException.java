package com.senai.clinicaApi.exceptions;

public class ConsultaNaoEncontradaException extends RuntimeException {
    public ConsultaNaoEncontradaException(String message) {
        super(message);
    }
}
