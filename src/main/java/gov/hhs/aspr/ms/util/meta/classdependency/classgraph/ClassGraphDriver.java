package gov.hhs.aspr.ms.util.meta.classdependency.classgraph;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import gov.hhs.aspr.ms.util.meta.classdependency.classgraph.reports.CircularClassDependencyReport;
import gov.hhs.aspr.ms.util.meta.classdependency.classgraph.reports.CircularPackageDependencyReport;
import gov.hhs.aspr.ms.util.meta.classdependency.classgraph.reports.WildCardReport;
import gov.hhs.aspr.ms.util.meta.classdependency.classgraph.support.ClassDependencyScan;
import gov.hhs.aspr.ms.util.meta.classdependency.classgraph.support.ClassDependencyScanner;

public final class ClassGraphDriver {

	private ClassGraphDriver() {
	}

	public static void main(String[] args) throws IOException {
		String directoryName = args[0];
		Path path = Paths.get(directoryName);
		ClassDependencyScan classDependencyScan = ClassDependencyScanner.execute(path);

		WildCardReport.report(classDependencyScan);

		CircularPackageDependencyReport.report(classDependencyScan);

		CircularClassDependencyReport.report(classDependencyScan);
	}

}
