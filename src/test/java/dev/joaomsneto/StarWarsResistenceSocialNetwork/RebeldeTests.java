package dev.joaomsneto.StarWarsResistenceSocialNetwork;

import dev.joaomsneto.StarWarsResistenceSocialNetwork.model.Genero;
import dev.joaomsneto.StarWarsResistenceSocialNetwork.model.Item;
import dev.joaomsneto.StarWarsResistenceSocialNetwork.model.Rebelde;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
public class RebeldeTests {

    @Test
    public void instantiateTest() {
        Rebelde rebelde = new Rebelde("Teste 1", 19, Genero.m,
                10.22, 23.10, "Lagartine", new ArrayList<>());

        assertNotNull(rebelde);
    }

    @Test(expected = Exception.class)
    public void addASiMesmoComoTraidorTest() throws Exception {
        Rebelde rebelde = new Rebelde("Teste 1", 19, Genero.m,
                10.22, 23.10, "Lagartine", new ArrayList<>());

        rebelde.addTraidor(rebelde);
    }

    @Test(expected = Exception.class)
    public void addMesmoTraidorVariasVezes() throws Exception {
        Rebelde rebelde = new Rebelde("Teste 1", 19, Genero.m,
                10.22, 23.10, "Lagartine", new ArrayList<>());

        Rebelde rebelde2 = new Rebelde("Teste 2", 61, Genero.f,
                81.22, 33.10, "Pompyn", new ArrayList<>());

        rebelde.addTraidor(rebelde2);
        rebelde.addTraidor(rebelde2);
        rebelde.addTraidor(rebelde2);
    }

    @Test
    public void atualizarLocalizacaoTest() {
        Rebelde rebelde = new Rebelde("Teste 1", 19, Genero.m,
                10.22, 23.10, "Lagartine", new ArrayList<>());

        rebelde.atualizarLocalizacao(222.11, 123.29, "Xevoi");

        assertEquals(222.11, rebelde.getLatitude(), 0);
        assertEquals(123.29, rebelde.getLongitude(), 0);
        assertEquals("Xevoi", rebelde.getNomeBase());
    }

    @Test
    public void efetuarTrocaTest() throws Exception {
        Item arma = new Item("Arma", 4);
        Item municao = new Item("Munição", 3);
        Item agua = new Item("Água", 2);
        Item comida = new Item("Comida", 1);

        ArrayList<Item> itensRebelde1 = new ArrayList<>();
        itensRebelde1.add(arma);
        itensRebelde1.add(comida);
        itensRebelde1.add(municao);
        itensRebelde1.add(arma);

        Rebelde rebelde1 = new Rebelde("Teste 1", 19, Genero.m,
                10.22, 23.10, "Lagartine", itensRebelde1);

        ArrayList<Item> itensRebelde2 = new ArrayList<>();
        itensRebelde2.add(comida);
        itensRebelde2.add(comida);
        itensRebelde2.add(municao);
        itensRebelde2.add(arma);
        itensRebelde2.add(agua);
        itensRebelde2.add(agua);

        Rebelde rebelde2 = new Rebelde("Teste 2", 35, Genero.f,
                101.22, 231.10, "Ghero", itensRebelde2);

        ArrayList<Item> itensNegociacaoRebelde1 = new ArrayList<>();
        itensNegociacaoRebelde1.add(arma);
        itensNegociacaoRebelde1.add(comida);

        ArrayList<Item> itensNegociacaoRebelde2 = new ArrayList<>();
        itensNegociacaoRebelde2.add(municao);
        itensNegociacaoRebelde2.add(agua);

        ArrayList<Item> itensRebelde1Esperado = new ArrayList<>();
        itensRebelde1Esperado.add(municao);
        itensRebelde1Esperado.add(arma);
        itensRebelde1Esperado.add(municao);
        itensRebelde1Esperado.add(agua);

        ArrayList<Item> itensRebelde2Esperado = new ArrayList<>();
        itensRebelde2Esperado.add(comida);
        itensRebelde2Esperado.add(comida);
        itensRebelde2Esperado.add(arma);
        itensRebelde2Esperado.add(agua);
        itensRebelde2Esperado.add(arma);
        itensRebelde2Esperado.add(comida);


        rebelde1.efetuarTroca(rebelde2, itensNegociacaoRebelde2, itensNegociacaoRebelde1);
        assertEquals(itensRebelde1Esperado, rebelde1.getItens());
        assertEquals(itensRebelde2Esperado, rebelde2.getItens());

    }

}
