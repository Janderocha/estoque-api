package org.jander.estoque.dto;

public class LucroDTO {
    private double lucro;
    private int quantidadeTotalSaida;

    public LucroDTO(double lucro, int quantidadeTotalSaida) {
        this.lucro = lucro;
        this.quantidadeTotalSaida = quantidadeTotalSaida;
    }

    public double getLucro() {
        return lucro;
    }

    public void setLucro(double lucro) {
        this.lucro = lucro;
    }

    public int getQuantidadeTotalSaida() {
        return quantidadeTotalSaida;
    }

    public void setQuantidadeTotalSaida(int quantidadeTotalSaida) {
        this.quantidadeTotalSaida = quantidadeTotalSaida;
    }
}