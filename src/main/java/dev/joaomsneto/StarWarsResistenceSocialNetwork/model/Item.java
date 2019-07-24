package dev.joaomsneto.StarWarsResistenceSocialNetwork.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.joaomsneto.StarWarsResistenceSocialNetwork.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private int valor;

    @ManyToMany(cascade=CascadeType.ALL, mappedBy = "itens")
    @JsonIgnore
    private List<Rebelde> rebeldes;

    public Item(String nome, int valor) {
        this.nome = nome;
        this.valor = valor;
    }

    Item() {}

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getValor() {
        return valor;
    }

    public List<Rebelde> getRebeldes() {
        return rebeldes;
    }

    public static int somaDePontos(ArrayList<Item> itens) {
        return itens.stream().mapToInt(item -> item.getValor()).sum();
    }
}
