package dev.joaomsneto.StarWarsResistenceSocialNetwork;

import dev.joaomsneto.StarWarsResistenceSocialNetwork.model.Item;
import dev.joaomsneto.StarWarsResistenceSocialNetwork.repository.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class StarWarsResistenceSocialNetworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarWarsResistenceSocialNetworkApplication.class, args);
	}

	@Bean
    public CommandLineRunner demoData(ItemRepository itemRepository) {
        return args -> {
          if( itemRepository.count() == 0 ) {
          	List itens = new ArrayList<Item>();
          	itens.add(new Item("Arma", 4));
          	itens.add(new Item("Munição", 3));
          	itens.add(new Item("Água", 2));
          	itens.add(new Item("Comida", 1));

          	itemRepository.saveAll(itens);
		  }
        };
    }

}
