package org.jander.estoque.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LucroDTO {
    private double lucro;
    private int quantidadeTotalSaida;


    public void setLucro(double lucro) {
        this.lucro = lucro;
    }

    public void setQuantidadeTotalSaida(int quantidadeTotalSaida) {
        this.quantidadeTotalSaida = quantidadeTotalSaida;
    }
}