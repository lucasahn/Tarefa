package com.example.aluno.tarefa.model;

import java.io.Serializable;

public class ItemPedido implements Serializable {
   private Produto produto;
   private Double total;
   private Cliente cliente;

    public ItemPedido(Produto produto, Double total, Cliente cliente) {
        this.produto = produto;
        this.total = total;
        this.cliente = cliente;
    }

    public ItemPedido(Produto produto, Double total) {
        this.produto = produto;
        this.total = total;
        this.cliente = cliente;
    }

    public ItemPedido() {

    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "produto=" + produto +
                ", total=" + total +
                '}';
    }
}
