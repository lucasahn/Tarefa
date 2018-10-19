package com.example.aluno.tarefa.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private String dtPedido;
    private Cliente cliente;
    private Double total;
    private List<ItemPedido>itens = new ArrayList<ItemPedido>();

    public Pedido(String dtPedido, Cliente cliente, Double total, List<ItemPedido> itens) {
        this.dtPedido = dtPedido;
        this.cliente = cliente;
        this.total = total;
        this.itens = itens;
    }

    public Pedido() {
    }

    public String getDtPedido() {
        return dtPedido;
    }

    public void setDtPedido(String dtPedido) {
        this.dtPedido = dtPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "dtPedido=" + dtPedido +
                ", cliente=" + cliente +
                ", total=" + total +
                ", itens=" + itens +
                '}';
    }
}
