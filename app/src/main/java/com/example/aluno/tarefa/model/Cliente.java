package com.example.aluno.tarefa.model;

import java.io.Serializable;

public class Cliente implements Serializable {
    private Long codigo;
    private String nome;
    private Long cpf;
    private String urlFoto;
    private String dataInscricao;

    public Cliente() {
    }

    public Cliente(Long codigo, String nome, Long cpf, String urlFoto, String dataInscricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.urlFoto = urlFoto;
        this.dataInscricao = dataInscricao;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(String dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", urlFoto='" + urlFoto + '\'' +
                ", dataInscricao='" + dataInscricao + '\'' +
                '}';
    }
}
