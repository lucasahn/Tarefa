package com.example.aluno.tarefa.model;

public class ItemPedido {
    Produto produto;
    Double total;

    public ItemPedido() {
    }

    public ItemPedido(Produto produto, Double total) {
        this.produto = produto;
        this.total = total;
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
