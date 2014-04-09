package graphs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ListGraphTests {

  ListGraph graph;

  @Before
  public void initialize(){
    graph = new ListGraph();
  }

  @Test
  public void addANodeToTheGraph(){

    int pretestcount = graph.count();
    graph.add(new Stad("StadTest"));

    assertEquals(pretestcount+1, graph.count());
  }

  @Test
  public void doesNotAddSameNodeToTheGraph(){
    Stad s = new Stad("StadTest");

    graph.add(s);
    int pretestcount = graph.count();
    graph.add(s);

    assertEquals(pretestcount, graph.count());
  }

  @Test
  public void connectsTwoNodes(){
    Stad s1 = new Stad("Stad1");
    Stad s2 = new Stad("Stad2");

    graph.add(s1);
    graph.add(s2);

    graph.connect(s1, s2, "Båt", 5);

    assertNotNull(graph.getEdgesFrom(s1));
    assertNotNull(graph.getEdgesFrom(s2));
  }

  @Test(expected=NoSuchElementException.class)
  public void connectsUnavailableNodes(){
    Stad s1 = new Stad("Stad1");
    Stad s2 = new Stad("Stad2");

    graph.add(s1);

    graph.connect(s1, s2, "Båt", 5);

  }

  @Test(expected=IllegalArgumentException.class)
  public void negativeWeight(){
    Stad s1 = new Stad("Stad1");
    Stad s2 = new Stad("Stad2");

    graph.add(s1);
    graph.add(s2);

    graph.connect(s1, s2, "Båt", -5);
  }

  @Test(expected=IllegalStateException.class)
  public void connectionAlreadyExists(){
    Stad s1 = new Stad("Stad1");
    Stad s2 = new Stad("Stad2");

    graph.add(s1);
    graph.add(s2);

    graph.connect(s1, s2, "Båt", 5);
    graph.connect(s1, s2, "Båt", 5);

  }
}
