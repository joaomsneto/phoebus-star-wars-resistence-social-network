package dev.joaomsneto.StarWarsResistenceSocialNetwork.repository;

import dev.joaomsneto.StarWarsResistenceSocialNetwork.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.ResultSet;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i.nome, COUNT(r.id) FROM Item i INNER JOIN i.rebeldes r WHERE r.id NOT IN (SELECT tr.id FROM Rebelde re JOIN re.traidores tr GROUP BY tr.id HAVING COUNT(tr.id) >= 3) GROUP BY i.id")
    List<Object[]> findNomeQuantidadeItemGroupedRebeldes();

    @Query("SELECT SUM(i.valor) FROM Item i INNER JOIN i.rebeldes r WHERE r.id IN (SELECT tr.id FROM Rebelde re JOIN re.traidores tr GROUP BY tr.id HAVING COUNT(tr.id) >= 3)")
    Integer totalPontosGroupedTraidores();
}
