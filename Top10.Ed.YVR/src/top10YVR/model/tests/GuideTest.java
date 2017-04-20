package top10YVR.model.tests;


import org.junit.Before;
import org.junit.Test;
import top10YVR.model.Attraction;
import top10YVR.model.Filter;
import top10YVR.model.Guide;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

// unit tests for Guide class
public class GuideTest {
    private Guide testGuide;
    private Attraction a1;
    private Attraction a2;
    private Attraction a3;
    private Attraction a4;

    @Before
    public void runBefore() {
        testGuide = new Guide();
        a1 = new Attraction("at1", null);
        a2 = new Attraction("at2", null);
        a3 = new Attraction("at3", null);
        a4 = new Attraction("at4", null);
    }
    @Test
    public void testAddOneAttraction() {
        testGuide.addAttraction(a1);
        List<Attraction> resources = testGuide.getAttractions();
        assertEquals(1, resources.size());
        assertTrue(resources.contains(a1));
    }

    @Test
    public void testAddManyAttractions() {
        loadAttractions();
        List<Attraction> attractions = testGuide.getAttractions();
        assertEquals(4, attractions.size());
        assertTrue(attractions.contains(a1));
        assertTrue(attractions.contains(a2));
        assertTrue(attractions.contains(a3));
        assertTrue(attractions.contains(a4));
    }

    @Test
    public void testGetAttractionsOfferingFood() {
        loadAttractions();
        Set<Attraction> attractions = testGuide.getAttractionWithFilter(Filter.FOOD);
        assertEquals(3, attractions.size());
        assertTrue(attractions.contains(a1));
        assertTrue(attractions.contains(a2));
        assertTrue(attractions.contains(a4));
    }

    @Test
    public void testGetResourcesOfferingFitness() {
        loadAttractions();
        Set<Attraction> attractions = testGuide.getAttractionWithFilter(Filter.FITNESS);
        assertEquals(1, attractions.size());
        assertTrue(attractions.contains(a3));
    }

    @Test
    public void testGetResourcesOfferingAllServices() {
        loadAttractions();
        Set<Filter> filters = new HashSet<Filter>();
        filters.add(Filter.FOOD);
        filters.add(Filter.FITNESS);


        Set<Attraction> resources = testGuide.getAttractionsWithAllFiltersInSet(filters);
        assertEquals(2, resources.size());
        assertTrue(resources.contains(a1));
        assertTrue(resources.contains(a4));
    }



    // MODIFIES: this
    // EFFECTS:  adds filters to resources and resources to registry
    private void loadAttractions() {
        a1.addFilter(Filter.FOOD);
        a1.addFilter(Filter.FITNESS);
        a2.addFilter(Filter.ARCHITECTURE);
        a2.addFilter(Filter.SCENERY);
        a3.addFilter(Filter.SHOP);
        a4.addFilter(Filter.SCENERY);
        a4.addFilter(Filter.FOOD);
        a4.addFilter(Filter.ACTIVITY);

        testGuide.addAttraction(a1);
        testGuide.addAttraction(a2);
        testGuide.addAttraction(a3);
        testGuide.addAttraction(a4);
    }
}