package com.example.aluno.tarefa.model;

import java.io.Serializable;

public class Produto implements Serializable {
    public Long getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(Long codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    private Long codigoDeBarras;
    private String key;
    private String nome;
    private Double valor;
    private String descricao;
    private boolean situacao;
    private Integer estoque;
    private Integer quantidade = null;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
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

    public Produto(Long codigoDeBarras, String key,  String nome, Double valor, String descricao, boolean situacao, Integer quantidade, Integer estoque) {
        this.codigoDeBarras = codigoDeBarras;
        this.key = key;
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
                "id=" + codigoDeBarras +
                "key=" + key +
                ", nome='" + nome + '\'' +
                ", valor=" + valor +
                ", descricao='" + descricao + '\'' +
                ", situacao=" + situacao +
                ", estoque=" + estoque +
                ", quantidade=" + quantidade +
                '}';
    }
}
