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

  @Test
  public void setWeightOfConnection(){
    Stad s1 = new Stad("Stad1");
    Stad s2 = new Stad("Stad2");

    graph.add(s1);
    graph.add(s2);

    graph.connect(s1, s2, "Båt", 5);
    graph.setConnectionWeight(s1, s2, 3);

    assertEquals(3, graph.getEdgeBetween(s1, s2).getWeight());
  }

  @Test(expected=NoSuchElementException.class)
  public void setWeightNoConnection(){
    Stad s1 = new Stad("Stad1");
    Stad s2 = new Stad("Stad2");

    graph.add(s1);
    graph.add(s2);

    graph.setConnectionWeight(s1, s2, 3);
  }

  @Test(expected=NoSuchElementException.class)
  public void setWeightWithoutNodesInGraph(){
    Stad s1 = new Stad("Stad1");
    Stad s2 = new Stad("Stad2");

    graph.setConnectionWeight(s1, s2, 3);
  }

  @Test(expected=IllegalArgumentException.class)
  public void setNegativeWeight(){
    Stad s1 = new Stad("Stad1");
    Stad s2 = new Stad("Stad2");

    graph.add(s1);
    graph.add(s2);

    graph.connect(s1, s2, "Båt", 5);
    graph.setConnectionWeight(s1, s2, -3);
  }

  @Test
  public void getNodesReturnsAListOfNodes(){
    Stad s1 = new Stad("Stad1");
    graph.add(s1);
    assertEquals(s1, graph.getNodes().get(0));
  }

  @Test
  public void getEdgesForNodeReturnsAllEdges(){
    Stad s1 = new Stad("Stad1");
    Stad s2 = new Stad("Stad2");
    Stad s3 = new Stad("Stad3");

    graph.add(s1);
    graph.add(s2);
    graph.add(s3);

    graph.connect(s1, s2, "Båt", 5);

    assertEquals(1, graph.getEdgesFrom(s1).size());

    graph.connect(s1,s3, "Bil", 6);

    assertEquals(2, graph.getEdgesFrom(s1).size());
  }

  @Test(expected=NoSuchElementException.class)
  public void getEdgesForNodeThrowsErrorIfNodeIsNotInGraph(){
    Stad s1 = new Stad("Stad1");
    graph.getEdgesFrom(s1);
  }

  @Test
  public void getEdgeBetweenReturnEdgeConnectingGivenNodes(){
    Stad s1 = new Stad("Stad1");
    Stad s2 = new Stad("Stad2");

    graph.add(s1);
    graph.add(s2);

    graph.connect(s1, s2, "Båt", 5);
    assertEquals(s2, graph.getEdgeBetween(s1, s2).getDestination());
  }

  @Test
  public void getEdgeBetweenReturnsNullIfNoConnection(){
    Stad s1 = new Stad("Stad1");
    Stad s2 = new Stad("Stad2");

    graph.add(s1);
    graph.add(s2);

    assertEquals(null, graph.getEdgeBetween(s1, s2));
  }

  @Test(expected=NoSuchElementException.class)
  public void getEdgeBetweenThrowsExceptionIfNodesAreNotInGraph(){
    Stad s1 = new Stad("Stad1");
    Stad s2 = new Stad("Stasd2");

    graph.getEdgeBetween(s1,s2);
  }
}
