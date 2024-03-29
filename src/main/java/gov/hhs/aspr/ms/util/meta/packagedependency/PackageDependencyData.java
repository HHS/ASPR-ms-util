package gov.hhs.aspr.ms.util.meta.packagedependency;

import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

import gov.hhs.aspr.ms.util.graph.Graph;
import gov.hhs.aspr.ms.util.graph.MutableGraph;
import gov.hhs.aspr.ms.util.meta.packagedependency.reports.PackageDependencyReport;

/**
 * An immutable container for holding the primary level of package dependency
 * analysis data. It is generated by the
 * {@linkplain PackageDependencyDataGenerator} and is used by the
 * {@linkplain PackageDependencyReport}
 */
public final class PackageDependencyData {

	public final static class PackageRef {
		private String name;

		public PackageRef(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof PackageRef)) {
				return false;
			}
			PackageRef other = (PackageRef) obj;
			if (name == null) {
				if (other.name != null) {
					return false;
				}
			} else if (!name.equals(other.name)) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("PackageRef [name=");
			builder.append(name);
			builder.append("]");
			return builder.toString();
		}

	}

	public final static class PackageDependencyDetails {
		private final Set<String> classes;

		public PackageDependencyDetails(Set<String> classes) {
			this.classes = new LinkedHashSet<>(classes);
		}

		public Set<String> getClasses() {
			return new LinkedHashSet<>(classes);
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("PackageDependencyDetails [classes=");
			builder.append(classes);
			builder.append("]");
			return builder.toString();
		}

	}

	private static class Data {
		private Graph<PackageRef, PackageDependencyDetails> graph = new MutableGraph<PackageRef, PackageDependencyDetails>()
				.toGraph();
		private Set<String> wildCardClasses = new LinkedHashSet<>();
		private Set<String> packagelessClasses = new LinkedHashSet<>();
		private Set<String> uncoveredClasses = new LinkedHashSet<>();
		private Set<Path> inputDirectories = new LinkedHashSet<>();
		private Set<String> inputPackageNames = new LinkedHashSet<>();
		private Set<String> packageNamesFound = new LinkedHashSet<>();

		public Data() {
		}

		public Data(Data data) {
			MutableGraph<PackageRef, PackageDependencyDetails> m = new MutableGraph<PackageRef, PackageDependencyDetails>();
			for (PackageRef packageRef : data.graph.getNodes()) {
				m.addNode(packageRef);
			}
			for (PackageDependencyDetails packageDependencyDetails : data.graph.getEdges()) {
				PackageRef originNode = data.graph.getOriginNode(packageDependencyDetails);
				PackageRef destinationNode = data.graph.getDestinationNode(packageDependencyDetails);
				m.addEdge(packageDependencyDetails, originNode, destinationNode);
			}
			graph = m.toGraph();

			wildCardClasses.addAll(data.wildCardClasses);
			packagelessClasses.addAll(data.packagelessClasses);
			uncoveredClasses.addAll(data.uncoveredClasses);
			inputDirectories.addAll(data.inputDirectories);
			inputPackageNames.addAll(data.inputPackageNames);
			packageNamesFound.addAll(data.packageNamesFound);
		}
	}

	public final static Builder builder() {
		return new Builder();
	}

	public final static class Builder {
		private Builder() {

		}

		private Data data = new Data();

		public PackageDependencyData build() {
			return new PackageDependencyData(new Data(data));
		}

		/**
		 * Adds a constructor warning
		 * 
		 * @throws NullPointerException if the graph is null
		 */
		public Builder setGraph(Graph<PackageRef, PackageDependencyDetails> graph) {
			if (graph == null) {
				throw new NullPointerException("graph is null");
			}
			data.graph = graph;
			return this;
		}

		/**
		 * Adds a wildcard class reference. A wildcard class is one containing a
		 * wildcard character in its import statements
		 * 
		 * @throws NullPointerException if the wildcard class is null
		 */
		public Builder addWildCardClass(String wildcardClass) {
			if (wildcardClass == null) {
				throw new NullPointerException("wildcard class is null");
			}
			data.wildCardClasses.add(wildcardClass);
			return this;
		}

		/**
		 * Adds a packageless class reference. A packageless class is one having not
		 * package statement
		 * 
		 * @throws NullPointerException if the wildcard class is null
		 */
		public Builder addPackagelessClass(String packagelessClass) {
			if (packagelessClass == null) {
				throw new NullPointerException("packageless class is null");
			}
			data.packagelessClasses.add(packagelessClass);
			return this;

		}

		/**
		 * Adds an uncovered class reference. An uncovered class is a java class found
		 * in the scanned directories that does not match any of the contributed package
		 * names.
		 * 
		 * @throws NullPointerException if the wildcard class is null
		 */
		public Builder addUncoveredClass(String uncoveredClass) {
			if (uncoveredClass == null) {
				throw new NullPointerException("uncovered class is null");
			}
			data.uncoveredClasses.add(uncoveredClass);
			return this;
		}

		/**
		 * Adds an input directory.
		 * 
		 * @throws NullPointerException if the directory is null
		 */
		public Builder addInputDirectory(Path directory) {
			if (directory == null) {
				throw new NullPointerException("directory is null");
			}
			data.inputDirectories.add(directory);
			return this;
		}

		/**
		 * Adds an input package name.
		 * 
		 * @throws NullPointerException if the package name is null
		 */
		public Builder addInputPackageName(String packageName) {
			if (packageName == null) {
				throw new NullPointerException("package name is null");
			}
			data.inputPackageNames.add(packageName);
			return this;
		}

		/**
		 * Adds a found package name.
		 * 
		 * @throws NullPointerException if the package name is null
		 */
		public Builder addFoundPackageName(String packageName) {
			if (packageName == null) {
				throw new NullPointerException("package name is null");
			}
			data.packageNamesFound.add(packageName);
			return this;
		}

	}

	private final Data data;

	private PackageDependencyData(Data data) {
		this.data = data;
	}

	public Graph<PackageRef, PackageDependencyDetails> getPackageDependencyGraph() {
		return data.graph;
	}

	public Set<String> getWildcardClasses() {
		return new LinkedHashSet<>(data.wildCardClasses);
	}

	public Set<String> getPackagelessClasses() {
		return new LinkedHashSet<>(data.packagelessClasses);
	}

	public Set<String> getUncoveredClasses() {
		return new LinkedHashSet<>(data.uncoveredClasses);
	}

	public Set<Path> getCoveredDirectories() {
		return new LinkedHashSet<>(data.inputDirectories);
	}

	public Set<String> getCoveredPackageNames() {
		return new LinkedHashSet<>(data.inputPackageNames);
	}

	public Set<String> getPackageNamesFound() {
		return new LinkedHashSet<>(data.packageNamesFound);
	}

}
