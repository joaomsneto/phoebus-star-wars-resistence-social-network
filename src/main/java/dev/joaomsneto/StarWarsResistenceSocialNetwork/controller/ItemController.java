package dev.joaomsneto.StarWarsResistenceSocialNetwork.controller;

import dev.joaomsneto.StarWarsResistenceSocialNetwork.model.Item;
import dev.joaomsneto.StarWarsResistenceSocialNetwork.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/item"})
public class ItemController {

    @Autowired
    ItemRepository itemRepository;

    @GetMapping
    public ResponseEntity<List> itens(){
        List<Item> itens = itemRepository.findAll();
        return ResponseEntity.ok().body(itens);
    }
}
