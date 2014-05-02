package graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class GraphMethods {
	public static <T> List<Edge<T>> getPath(T from, T to, Graph<T> graph) {
		return dfs3(from, to, graph, new HashSet<T>());
	}

	private static <T> ArrayList<Edge<T>> dfs3(T where, T to, Graph<T> graph,
			Set<T> visited) {
		if (where.equals(to))
			return new ArrayList<Edge<T>>();
		visited.add(where);
		ArrayList<Edge<T>> path = new ArrayList<Edge<T>>();
		TreeMap<Integer, ArrayList<Edge<T>>> paths = new TreeMap<Integer, ArrayList<Edge<T>>>();
		for (Edge<T> e : graph.getEdgesFrom(where)) {
			if (!visited.contains(e.getDestination())) {
				ArrayList<Edge<T>> subPath = new ArrayList<Edge<T>>();
				subPath.add(e);
				subPath.addAll(dfs3(e.getDestination(), to, graph,
						new HashSet<T>(visited)));
				if (subPath.get(subPath.size() - 1).getDestination().equals(to)) {
					paths.put(getPathLength(subPath, to), subPath);
				}
			}
		}
		if (!paths.isEmpty())
			path.addAll(paths.firstEntry().getValue());
		return path;
	}

	private static <T> int getPathLength(List<Edge<T>> edges, T to) {
		int length = 0;
		for (Edge<T> edge : edges) {
			length += edge.getWeight();
		}
		return length;
	}

	private static <T> void dfs(T where, Set<T> visited, Graph<T> graph) {
		visited.add(where);
		for (Edge<T> e : graph.getEdgesFrom(where))
			if (!visited.contains(e.getDestination()))
				dfs(e.getDestination(), visited, graph);
	}

	public static <T> boolean pathExists(T from, T to, Graph<T> graph) {
		Set<T> visited = new HashSet<T>();
		dfs(from, visited, graph);
		return visited.contains(to);
	}
}
