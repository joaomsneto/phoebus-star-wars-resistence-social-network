package dev.joaomsneto.StarWarsResistenceSocialNetwork.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.joaomsneto.StarWarsResistenceSocialNetwork.data.RebeldeDeserializer;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(using = RebeldeDeserializer.class)
@Entity
public class Rebelde {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotNull
    @Min(1)
    private int idade;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    @NotBlank
    private String nomeBase;

    @NotEmpty
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="inventario",
            joinColumns={@JoinColumn(name="rebelde_id",
                           referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="item_id",
                           referencedColumnName="id")})
    private List<Item> itens = new ArrayList<>();

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="delacao",
            joinColumns={@JoinColumn(name="delator_id",
                           referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="traidor_id",
                           referencedColumnName="id")})
    @JsonIgnore
    private List<Rebelde> traidores = new ArrayList<>();

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="delacao",
            joinColumns={@JoinColumn(name="traidor_id",
                           referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="delator_id",
                           referencedColumnName="id")})
    @JsonIgnore
    private List<Rebelde> delatores = new ArrayList<>();

    public Rebelde(String nome, int idade, Genero genero,
            double latitude, double longitude, String nomeBase, ArrayList<Item> itens) {
        this.nome = nome;
        this.idade = idade;
        this.genero = genero;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nomeBase = nomeBase;
        this.itens = itens;
    }

    Rebelde() {

    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public Genero getGenero() {
        return genero;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getNomeBase() {
        return nomeBase;
    }

    public List<Item> getItens() {
        return itens;
    }

    public List<Rebelde> getTraidores() {
        return traidores;
    }

    public List<Rebelde> getDelatores() {
        return delatores;
    }

    public void addTraidor(Rebelde traidor) throws Exception {
        if( this.equals(traidor) ) {
            throw new Exception("Você não pode delatar a si mesmo");
        }

        if( this.traidores.contains(traidor) ) {
            throw new Exception("Você não pode delatar o mesmo rebelde mais de uma vez");
        }

        this.traidores.add(traidor);
    }

    private void addItem(Item item) {
        this.itens.add(item);
    }

    public void atualizarLocalizacao(double latitude, double longitude, String nomeBase) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.nomeBase = nomeBase;
    }

    public boolean ehTraidor() {
        return this.getDelatores().size() >= 3;
    }

    public void efetuarTroca(Rebelde negociante, ArrayList<Item> itensNegociante, ArrayList<Item> itensCliente) throws Exception {
        if( negociante.ehTraidor() ) {
            throw new Exception("O negociante é um traidor, não pode efetuar trocas");
        }

        if( this.ehTraidor() ) {
            throw new Exception("O cliente é um traidor, não pode efetuar trocas");
        }

        int totalPontosNegociante = Item.somaDePontos(itensNegociante);
        int totalPontosCliente = Item.somaDePontos(itensCliente);

        if( totalPontosCliente < totalPontosNegociante ) {
            throw new Exception("Os itens propostos pelo negociante possuem mais pontos que os do cliente");
        }

        if( totalPontosCliente > totalPontosNegociante ) {
            throw new Exception("Os itens propostos pelo negociante possuem menos pontos que os do cliente");
        }

        for( Item item : itensNegociante ) {
            if( !negociante.getItens().remove(item) ) {
                throw new Exception("O negociador não possui todos os itens propostos");
            }
        }

        for( Item item : itensCliente ) {
            if( !this.getItens().remove(item) ) {
                throw new Exception("O cliente não possui todos os itens propostos");
            }
        }

        for( Item item : itensNegociante ) {
            this.addItem(item);
        }

        for( Item item : itensCliente ) {
            negociante.addItem(item);
        }
    }
}
