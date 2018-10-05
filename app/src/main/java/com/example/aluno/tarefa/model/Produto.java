package com.example.aluno.tarefa.model;

import java.io.Serializable;

public class Produto implements Serializable {
    private Long id;
    private String nome;
    private Double valor;
    private String descricao;
    private boolean situacao;
    private Integer estoque;
    private Integer quantidade = null;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Double getValor() {
        return valor;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public boolean isSituacao() {
        return situacao;
    }
    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }


    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Produto(Long id, String nome, Double valor, String descricao, boolean situacao, Integer quantidade, Integer estoque) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.descricao = descricao;
        this.situacao = situacao;
        this.estoque = estoque;
        this.quantidade = quantidade;
    }
    public Produto() {

    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", valor=" + valor +
                ", descricao='" + descricao + '\'' +
                ", situacao=" + situacao +
                ", estoque=" + estoque +
                ", quantidade=" + quantidade +
                '}';
    }
}
