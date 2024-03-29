package gov.hhs.aspr.ms.util.meta.classdependency.classgraph.reports;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import gov.hhs.aspr.ms.util.graph.Graph;
import gov.hhs.aspr.ms.util.graph.GraphDepthEvaluator;
import gov.hhs.aspr.ms.util.graph.Graphs;
import gov.hhs.aspr.ms.util.graph.MutableGraph;
import gov.hhs.aspr.ms.util.meta.classdependency.classgraph.support.ClassDependencyScan;
import gov.hhs.aspr.ms.util.meta.classdependency.classgraph.support.JavaDependency;

public final class CircularPackageDependencyReport {
	private CircularPackageDependencyReport() {
	}

	public static void report(ClassDependencyScan classDependencyScan) {
		System.out.println();
		System.out.println("circular package dependency report");

		Set<JavaDependency> javaDependencies = classDependencyScan.getJavaDependencies();
		Set<String> localPackageNames = classDependencyScan.getLocalPackageNames();

		MutableGraph<String, Object> m = new MutableGraph<>();
		for (JavaDependency javaDependency : javaDependencies) {
			String sourcePackageName = javaDependency.getDependentRef().getPackageName();
			String importPackageName = javaDependency.getSupportRef().getPackageName();
			if (localPackageNames.contains(importPackageName)) {
				m.addEdge(new Object(), sourcePackageName, importPackageName);
			}
		}

		Optional<GraphDepthEvaluator<String>> optional = GraphDepthEvaluator.getGraphDepthEvaluator(m.toGraph());
		if (optional.isPresent()) {
			System.out.println();
			System.out.println("acyclic layers");

			GraphDepthEvaluator<String> graphDepthEvaluator = optional.get();
			int maxDepth = graphDepthEvaluator.getMaxDepth();
			for (int i = 0; i <= maxDepth; i++) {
				List<String> nodesForDepth = graphDepthEvaluator.getNodesForDepth(i);
				for (String node : nodesForDepth) {
					System.out.println(i + "\t" + node);
				}
			}
		} else {
			System.out.println();
			System.out.println("cyclic groups");

			Graph<String, Object> g = m.toGraph();

			g = Graphs.getSourceSinkReducedGraph(g);

			g = Graphs.getEdgeReducedGraph(g);

			g = Graphs.getSourceSinkReducedGraph(g);

			List<Graph<String, Object>> cutGraphs = Graphs.cutGraph(g);
			StringBuilder sb = new StringBuilder();
			String lineSeparator = System.getProperty("line.separator");

			for (Graph<String, Object> cutGraph : cutGraphs) {
				sb.append(lineSeparator);
				Set<String> nodes = cutGraph.getNodes().stream().collect(Collectors.toCollection(LinkedHashSet::new));

				for (String node : nodes) {
					for (Object edge : cutGraph.getOutboundEdges(node)) {
						String dependencyNode = cutGraph.getDestinationNode(edge);
						sb.append(node);
						sb.append(",");
						sb.append(dependencyNode);
						sb.append(lineSeparator);
					}
				}
			}
			System.out.println(sb);
		}

	}
}
