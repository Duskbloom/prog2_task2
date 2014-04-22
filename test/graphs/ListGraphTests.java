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

  ListGraph<Object> graph;

  @Before
  public void initialize(){
    graph = new ListGraph<Object>();
  }

  @Test
  public void addANodeToTheGraph(){

    int pretestcount = graph.count();
    graph.add(new Object());

    assertEquals(pretestcount+1, graph.count());
  }

  @Test
  public void doesNotAddSameNodeToTheGraph(){
    Object s = new Object();

    graph.add(s);
    int pretestcount = graph.count();
    graph.add(s);

    assertEquals(pretestcount, graph.count());
  }

  @Test
  public void connectsTwoNodes(){
    Object s1 = new Object();
    Object s2 = new Object();

    graph.add(s1);
    graph.add(s2);

    graph.connect(s1, s2, "Båt", 5);

    assertNotNull(graph.getEdgesFrom(s1));
    assertNotNull(graph.getEdgesFrom(s2));
  }

  @Test
  public void connectThreeNodes(){
    Object s1 = new Object();
    Object s2 = new Object();
    Object s3 = new Object();

    graph.add(s1);
    graph.add(s2);
    graph.add(s3);

    graph.connect(s1, s2, "Båt", 5);
    graph.connect(s2, s3, "Båt", 3);
    graph.connect(s3, s1, "Båt", 2);

    assertEquals(2, graph.getEdgesFrom(s1).size());
    assertEquals(2, graph.getEdgesFrom(s2).size());
    assertEquals(2, graph.getEdgesFrom(s3).size());
  }

  @Test(expected=NoSuchElementException.class)
  public void connectsUnavailableNodes(){
    Object s1 = new Object();
    Object s2 = new Object();

    graph.add(s1);

    graph.connect(s1, s2, "Båt", 5);

  }

  @Test(expected=IllegalArgumentException.class)
  public void negativeWeight(){
    Object s1 = new Object();
    Object s2 = new Object();
    graph.add(s1);
    graph.add(s2);

    graph.connect(s1, s2, "Båt", -5);
  }

  @Test(expected=IllegalStateException.class)
  public void connectionAlreadyExists(){
    Object s1 = new Object();
    Object s2 = new Object();

    graph.add(s1);
    graph.add(s2);

    graph.connect(s1, s2, "Båt", 5);
    graph.connect(s1, s2, "Båt", 5);

  }

  @Test
  public void setWeightOfConnection(){
    Object s1 = new Object();
    Object s2 = new Object();

    graph.add(s1);
    graph.add(s2);

    graph.connect(s1, s2, "Båt", 5);
    graph.setConnectionWeight(s1, s2, 3);

    assertEquals(3, graph.getEdgeBetween(s1, s2).getWeight());
  }

  @Test(expected=NoSuchElementException.class)
  public void setWeightNoConnection(){
    Object s1 = new Object();
    Object s2 = new Object();

    graph.add(s1);
    graph.add(s2);

    graph.setConnectionWeight(s1, s2, 3);
  }

  @Test(expected=NoSuchElementException.class)
  public void setWeightWithoutNodesInGraph(){
    Object s1 = new Object();
  Object s2 = new Object();
  

    graph.setConnectionWeight(s1, s2, 3);
  }

  @Test(expected=IllegalArgumentException.class)
  public void setNegativeWeight(){
    Object s1 = new Object();
    Object s2 = new Object();

    graph.add(s1);
    graph.add(s2);

    graph.connect(s1, s2, "Båt", 5);
    graph.setConnectionWeight(s1, s2, -3);
  }

  @Test
  public void getNodesReturnsAListOfNodes(){
    Object s1 = new Object();
    graph.add(s1);
    assertEquals(s1, graph.getNodes().get(0));
  }

  @Test
  public void getEdgesForNodeReturnsAllEdges(){
    Object s1 = new Object();
    Object s2 = new Object();
    Object s3 = new Object();


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
    Object s1 = new Object();
    graph.getEdgesFrom(s1);
  }

  @Test
  public void getEdgeBetweenReturnEdgeConnectingGivenNodes(){
    Object s1 = new Object();
    Object s2 = new Object();

    graph.add(s1);
    graph.add(s2);

    graph.connect(s1, s2, "Båt", 5);
    assertEquals(s2, graph.getEdgeBetween(s1, s2).getDestination());
  }

  @Test
  public void getEdgeBetweenReturnsNullIfNoConnection(){
    Object s1 = new Object();
    Object s2 = new Object();

    graph.add(s1);
    graph.add(s2);

    assertEquals(null, graph.getEdgeBetween(s1, s2));
  }

  @Test(expected=NoSuchElementException.class)
  public void getEdgeBetweenThrowsExceptionIfNodesAreNotInGraph(){
    Object s1 = new Object();
    Object s2 = new Object();
    graph.getEdgeBetween(s1,s2);
  }
  
  @Test
  public void disconnectNodes(){
    Object s1 = new Object();
    Object s2 = new Object();

    graph.add(s1);
    graph.add(s2);
    
    graph.connect(s1, s2, "Test", 5);
    graph.disconnect(s1, s2);
    
    assertEquals(null, graph.getEdgeBetween(s1, s2));
  }
  
  @Test
  public void disconnectNodesInReverseOrder(){
    Object s1 = new Object();
    Object s2 = new Object();

    graph.add(s1);
    graph.add(s2);
    
    graph.connect(s1, s2, "Test", 5);
    graph.disconnect(s2, s1);
    
    assertEquals(null, graph.getEdgeBetween(s1, s2));
  }
  
  @Test(expected=IllegalStateException.class)
  public void connectionAlreadyExistsReverseOrder(){
    Object s1 = new Object();
    Object s2 = new Object();

    graph.add(s1);
    graph.add(s2);
    
    graph.connect(s1, s2, "Test", 5);
    graph.connect(s2, s1, "Test", 5);
  }
  
  @Test
  public void removeNodeOnly(){
    Object s1 = new Object();
    graph.add(s1);
    graph.remove(s1);
    
    assertEquals(graph.count(), 0);
  }
  
  public void removeNodeAndEdges(){
    Object s1 = new Object();
    Object s2 = new Object();

    graph.add(s1);
    graph.add(s2);
    graph.connect(s1, s2, "Test", 5);
    graph.remove(s1);
    
    assertEquals(graph.getEdgesFrom(s2), null);
  }
}
