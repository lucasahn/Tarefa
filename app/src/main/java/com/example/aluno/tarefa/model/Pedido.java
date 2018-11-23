package com.example.aluno.tarefa.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private Date dtCriacao;
    private Date dtModificacao;
    private Cliente cliente;
    private Double total;
    private List<ItemPedido>itens = new ArrayList<ItemPedido>();

    public Pedido(Date dtPedido, Date dtModificacao, Cliente cliente, Double total, List<ItemPedido> itens) {
        this.dtCriacao = dtPedido;
        this.dtModificacao = dtModificacao;
        this.cliente = cliente;
        this.total = total;
        this.itens = itens;
    }

    public Pedido() {
    }

    public Date getDtCricao() {
        return dtCriacao;
    }

    public void setDtCriacao(Date dtCriacao) {
        this.dtCriacao = dtCriacao;
    }

    public Date getDtModificacao() {
        return dtModificacao;
    }

    public void setDtModificacao(Date dtModificacao) {
        this.dtModificacao = dtModificacao;
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
                "dtPedido=" + dtCriacao +
                "dtModificacao=" + dtModificacao +
                ", cliente=" + cliente +
                ", total=" + total +
                ", itens=" + itens +
                '}';
    }
}
