package com.example.aluno.tarefa.model;

import java.util.Date;

public class Compra {
    private Long idCliente;
    private Long idProduto;
    private Integer quantidade;
    private String dtCompra;

    public Compra(Long idCliente, Long idProduto, Integer quantidade, String dtCompra) {
        this.idCliente = idCliente;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.dtCompra = dtCompra;
    }

    public Compra() {
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getDtCompra() {
        return dtCompra;
    }

    public void setDtCompra(String dtCompra) {
        this.dtCompra = dtCompra;
    }
}
