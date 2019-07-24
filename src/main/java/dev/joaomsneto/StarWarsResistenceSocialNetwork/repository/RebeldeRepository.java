package dev.joaomsneto.StarWarsResistenceSocialNetwork.repository;

import dev.joaomsneto.StarWarsResistenceSocialNetwork.model.Rebelde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RebeldeRepository extends JpaRepository<Rebelde, Long> {

    @Query("SELECT tr FROM Rebelde r INNER JOIN r.traidores tr GROUP BY tr.id HAVING COUNT(tr.id) >= 3")
    List<Rebelde> findAllTraidores();
}
