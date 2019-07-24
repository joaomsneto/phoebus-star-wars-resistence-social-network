package dev.joaomsneto.StarWarsResistenceSocialNetwork.controller;

import dev.joaomsneto.StarWarsResistenceSocialNetwork.repository.ItemRepository;
import dev.joaomsneto.StarWarsResistenceSocialNetwork.repository.RebeldeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping({"/relatorio"})
public class RelatorioController {

    @Autowired
    private RebeldeRepository repository;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping(path = "/traidores")
    public ResponseEntity<?> traidores() {
        int totalTraidores = repository.findAllTraidores().size();
        int totalRebeldes = repository.findAll().size();
        double porcentagemTraidores = (totalTraidores * 100) / totalRebeldes;
        return ResponseEntity.ok().body(Collections.singletonMap("response", porcentagemTraidores));
    }

    @GetMapping(path = "/rebeldes")
    public ResponseEntity<?> rebeldes() {
        int totalTraidores = repository.findAllTraidores().size();
        int totalRebeldes = repository.findAll().size();
        double porcentagemRebeldes = ((totalRebeldes - totalTraidores) * 100) / totalRebeldes;
        return ResponseEntity.ok().body(Collections.singletonMap("response", porcentagemRebeldes));
    }

    @GetMapping(path = "/media_itens_rebeldes")
    public ResponseEntity<?> mediaItensRebelde() {
        List<Object[]> itens = itemRepository.findNomeQuantidadeItemGroupedRebeldes();
        int totalTraidores = repository.findAllTraidores().size();
        int totalRebeldes = repository.findAll().size();
        for(Object[] item: itens) {
            double mediaPorArma = Double.valueOf(item[1].toString()) / (totalRebeldes - totalTraidores);
            item[1] = mediaPorArma;
        }
        return ResponseEntity.ok().body(itens);
    }

    @GetMapping(path = "/pontos_perdidos_traidores")
    public ResponseEntity<?> pontosPerdidosTraidores() {
        Integer totalPontosGroupedTraidores = itemRepository.totalPontosGroupedTraidores();
        return ResponseEntity.ok().body(Collections.singletonMap("response", totalPontosGroupedTraidores));
    }

}
