package dev.joaomsneto.StarWarsResistenceSocialNetwork.controller;

import dev.joaomsneto.StarWarsResistenceSocialNetwork.model.Item;
import dev.joaomsneto.StarWarsResistenceSocialNetwork.model.Rebelde;
import dev.joaomsneto.StarWarsResistenceSocialNetwork.repository.ItemRepository;
import dev.joaomsneto.StarWarsResistenceSocialNetwork.repository.RebeldeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping({"/rebelde"})
public class RebeldeController {

    @Autowired
    private RebeldeRepository repository;

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Rebelde rebelde){
        Rebelde rebeldeCriado = repository.save(rebelde);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> atualizarLocalizacao(@PathVariable long id,
                                                       @RequestBody Rebelde rebelde) {
        return repository.findById(id)
                .map(registro -> {
                    registro.atualizarLocalizacao(rebelde.getLatitude(), rebelde.getLongitude(), rebelde.getNomeBase());
                    Rebelde rebeldeAtualizado = repository.save(registro);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{delatorId}/delatar/{traidorId}")
    public ResponseEntity<?> delatar(@PathVariable Long delatorId,
                                           @PathVariable Long traidorId) {
        return repository.findById(delatorId)
                .map(delator ->
                    repository.findById(traidorId)
                            .map(traidor -> {
                                try {
                                    delator.addTraidor(traidor);
                                } catch (Exception e) {
                                    return ResponseEntity.badRequest().body(Collections.singletonMap("response", e.getMessage()));
                                }
                                Rebelde delatorAtualizado = repository.save(delator);
                                return ResponseEntity.ok().body(Collections.singletonMap("response", "Traidor delatado com sucesso"));
                            }).orElse(ResponseEntity.notFound().build())
                ).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{negocianteId}/trocar/{clienteId}")
    public ResponseEntity<?> trocar(@PathVariable Long negocianteId,
                                         @PathVariable Long clienteId,
                                      @RequestBody HashMap<String, ArrayList<Long>> body) {
        return repository.findById(negocianteId)
                .map(negociante ->
                        repository.findById(clienteId)
                                .map(cliente -> {
                                    ArrayList<Long> itensNegocianteIds = body.get("itens_negociante");
                                    ArrayList<Item> itensNegociante = new ArrayList<>();
                                    for(Long id: itensNegocianteIds) {
                                        itemRepository.findById(id)
                                                .map(item -> itensNegociante.add(item));
                                    }
                                    ArrayList<Long> itensClienteIds = body.get("itens_cliente");
                                    ArrayList<Item> itensCliente = new ArrayList<>();
                                    for(Long id: itensClienteIds) {
                                        itemRepository.findById(id)
                                                .map(item -> itensCliente.add(item));
                                    }
                                    try {
                                        cliente.efetuarTroca(negociante, itensNegociante, itensCliente);
                                        repository.save(cliente);
                                        repository.save(negociante);
                                    } catch( Exception e) {
                                        return ResponseEntity.badRequest().body(Collections.singletonMap("response", e.getMessage()));
                                    }
                                    return ResponseEntity.ok().body(Collections.singletonMap("response", "Negociação efetuada com sucesso"));
                                }).orElse(ResponseEntity.notFound().build())
                ).orElse(ResponseEntity.notFound().build());
    }

}
