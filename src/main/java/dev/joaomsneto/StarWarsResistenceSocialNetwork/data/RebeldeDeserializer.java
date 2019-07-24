package dev.joaomsneto.StarWarsResistenceSocialNetwork.data;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.joaomsneto.StarWarsResistenceSocialNetwork.model.Genero;
import dev.joaomsneto.StarWarsResistenceSocialNetwork.model.Item;
import dev.joaomsneto.StarWarsResistenceSocialNetwork.model.Rebelde;
import dev.joaomsneto.StarWarsResistenceSocialNetwork.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;

public class RebeldeDeserializer extends JsonDeserializer<Rebelde> {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public Rebelde deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        Genero genero = Genero.valueOf(node.get("genero").asText());
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Integer> itensIds = mapper.convertValue(node.get("itens"), ArrayList.class);
        ArrayList<Item> itens = new ArrayList<>();
        for( int id : itensIds ) {
            itemRepository.findById(Long.valueOf(id)).map(item -> itens.add(item));
        }

        return new Rebelde(node.get("nome").asText(),
                node.get("idade").asInt(),
                genero,
                node.get("latitude").asDouble(),
                node.get("longitude").asDouble(),
                node.get("nomeBase").asText(),
                itens);
    }
}
