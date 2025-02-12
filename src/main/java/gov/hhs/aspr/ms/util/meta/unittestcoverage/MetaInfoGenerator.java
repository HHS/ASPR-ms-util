package gov.hhs.aspr.ms.util.meta.unittestcoverage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.RecordComponent;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestField;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.meta.unittestcoverage.warnings.ConstructorWarning;
import gov.hhs.aspr.ms.util.meta.unittestcoverage.warnings.FieldWarning;
import gov.hhs.aspr.ms.util.meta.unittestcoverage.warnings.MethodWarning;
import gov.hhs.aspr.ms.util.meta.unittestcoverage.warnings.WarningType;

/**
 * A utility class for generating various warnings on the coverage deficiencies
 * of the the unit test suite.
 */
public class MetaInfoGenerator {

	private static boolean isJavaFile(Path file) {
		return Files.isRegularFile(file) && file.toString().endsWith(".java");
	}

	private static String getClassName(Path sourcePath, Path file) {
		return file.toString().substring(sourcePath.toString().length() + 1, file.toString().length() - 5)
				.replace(File.separator, ".");
	}

	/**
	 * Assumes that the source path and file are consistent
	 */
	private static Class<?> getClassFromFile(Path sourcePath, Path file) {
		try {
			String className = getClassName(sourcePath, file);
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private MetaInfoContainer.Builder warningContainerBuilder = MetaInfoContainer.builder();
	
	private void probeClass(Class<?> c) {
		final Method[] methods = c.getMethods();
		boolean isEnum = c.isEnum();

		Set<Method> recordMethods = new HashSet<>();
		Constructor<?> recordConstructor = null;

		if (c.isRecord()) {
			RecordComponent[] recordComponents = c.getRecordComponents();

			for (RecordComponent rc : recordComponents) {
				recordMethods.add(rc.getAccessor());
			}

			for (Method m : methods) {
				if (m.getName().equals("equals") || m.getName().equals("hashCode") || m.getName().equals("toString")) {
					if(m.getAnnotation(Override.class) == null) {
						recordMethods.add(m);
					}
				}
			}

			Class<?>[] componentTypes = Arrays.stream(recordComponents).map(rc -> rc.getType())
					.toArray(Class<?>[]::new);
			try {
				recordConstructor = c.getDeclaredConstructor(componentTypes);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new RuntimeException(e);
			}
		}

		for (final Method method : methods) {

			boolean addRec = method.getDeclaringClass().equals(c);

			addRec &= !method.isBridge();
			addRec &= !method.isSynthetic();
			addRec &= !(Modifier.isAbstract(method.getModifiers()) && !isEnum);
			addRec &= !recordMethods.contains(method);

			if (isEnum) {
				if (method.getName().equals("values")) {
					if (method.getParameters().length == 0) {
						addRec = false;
					}
				} else if (method.getName().equals("valueOf")) {
					Parameter[] parameters = method.getParameters();
					if (parameters.length == 1) {
						Parameter parameter = parameters[0];
						if (parameter.getType() == String.class) {
							addRec = false;
						}
					}
				}
			}

			if (addRec) {
				sourceMethods.add(method);
			}
		}

		Constructor<?>[] constructors = c.getConstructors();
		for (final Constructor<?> constructor : constructors) {
			boolean addRec = constructor.getDeclaringClass().equals(c);
			addRec &= !constructor.isSynthetic();
			addRec &= !(recordConstructor != null && constructor.equals(recordConstructor));

			if (addRec) {
				sourceConstructors.add(constructor);
			}
		}

		for (Field field : c.getFields()) {
			if (!isEnum) {
				sourceFields.add(field);
			}
		}

	}

	private Set<Method> sourceMethods = new LinkedHashSet<>();
	private Set<Method> coveredSourceMethods = new LinkedHashSet<>();
	private Set<Constructor<?>> sourceConstructors = new LinkedHashSet<>();
	private Set<Constructor<?>> coveredSourceConstructors = new LinkedHashSet<>();
	private Set<Field> sourceFields = new LinkedHashSet<>();
	private Set<Field> coveredSourceFields = new LinkedHashSet<>();

	private Set<Class<?>> getClasses(Class<?> c) {
		Set<Class<?>> result = new LinkedHashSet<>();
		getClasses(c, result);
		return result;
	}

	private void getClasses(Class<?> c, Set<Class<?>> set) {
		if (c.isAnnotation()) {
			return;
		}
		set.add(c);
		Class<?>[] declaredClasses = c.getDeclaredClasses();
		for (Class<?> subClass : declaredClasses) {
			if (Modifier.isPublic(subClass.getModifiers())) {
				if (Modifier.isStatic(subClass.getModifiers())) {
					getClasses(subClass, set);
				} else {
					warningContainerBuilder
							.addGeneralWarning(WarningType.NONSTATIC_SUBCLASS.getDescription() + " " + subClass);
				}
			}
		}
	}

	private final class SourceFileVisitor extends SimpleFileVisitor<Path> {
		@Override
		public FileVisitResult visitFile(final Path file, final BasicFileAttributes attr) {
			if (isJavaFile(file)) {
				final Class<?> c = getClassFromFile(data.sourcePath, file);
				for (Class<?> c2 : getClasses(c)) {
					probeClass(c2);
				}
			}
			return FileVisitResult.CONTINUE;
		}
	}

	private void probeConstructorTest(Method testMethod, UnitTestConstructor unitTestConstructor) {
		Constructor<?> sourceConstructor = null;
		try {
			sourceConstructor = unitTestConstructor.target().getConstructor(unitTestConstructor.args());
		} catch (NoSuchMethodException | SecurityException e) {
			sourceConstructor = null;
		}
		if (sourceConstructor != null) {
			if (!sourceConstructors.contains(sourceConstructor)) {
				warningContainerBuilder.addMethodWarning(
						new MethodWarning(testMethod, WarningType.TEST_METHOD_NOT_MAPPED_TO_PROPER_SOURCE_CONSTRUCTOR));
			}
			coveredSourceConstructors.add(sourceConstructor);
			warningContainerBuilder.addUnitTestConstructor(unitTestConstructor);
		} else {
			warningContainerBuilder
					.addMethodWarning(new MethodWarning(testMethod, WarningType.SOURCE_CONSTRUCTOR_CANNOT_BE_RESOLVED));
		}
	}

	private void probeFieldTest(Method testMethod, UnitTestField unitTestField) {

		Field sourceField = null;
		String fieldExceptionMessage = "";
		try {
			sourceField = unitTestField.target().getField(unitTestField.name());
		} catch (NoSuchFieldException | SecurityException e) {
			fieldExceptionMessage = e.getMessage();
			sourceField = null;
		}
		if (sourceField != null) {
			if (!sourceFields.contains(sourceField)) {
				warningContainerBuilder.addMethodWarning(new MethodWarning(testMethod,
						WarningType.TEST_METHOD_NOT_MAPPED_TO_PROPER_SOURCE_FIELD, sourceField.toString()));
			} else {
				coveredSourceFields.add(sourceField);
				warningContainerBuilder.addUnitTestField(unitTestField);
			}
		} else {
			warningContainerBuilder.addMethodWarning(
					new MethodWarning(testMethod, WarningType.SOURCE_FIELD_CANNOT_BE_RESOLVED, fieldExceptionMessage));
		}
	}

	private void probeMethodTest(Method testMethod, UnitTestMethod unitTestMethod) {

		Method sourceMethod = null;
		String methodExceptionMessage = "";
		try {
			sourceMethod = unitTestMethod.target().getMethod(unitTestMethod.name(), unitTestMethod.args());
		} catch (NoSuchMethodException | SecurityException e) {
			methodExceptionMessage = e.getMessage();
			sourceMethod = null;
		}
		if (sourceMethod != null) {
			if (!sourceMethods.contains(sourceMethod)) {
				warningContainerBuilder.addMethodWarning(new MethodWarning(testMethod,
						WarningType.TEST_METHOD_NOT_MAPPED_TO_PROPER_SOURCE_METHOD, sourceMethod.toString()));
			} else {
				coveredSourceMethods.add(sourceMethod);
				warningContainerBuilder.addUnitTestMethod(unitTestMethod);
			}
		} else {
			warningContainerBuilder.addMethodWarning(new MethodWarning(testMethod,
					WarningType.SOURCE_METHOD_CANNOT_BE_RESOLVED, methodExceptionMessage));
		}
	}

	private void probeTestClass(Class<?> c) {

		final Method[] methods = c.getMethods();
		for (final Method testMethod : methods) {

			final Test test = testMethod.getAnnotation(Test.class);
			final UnitTestMethod unitTestMethod = testMethod.getAnnotation(UnitTestMethod.class);
			final UnitTestConstructor unitTestConstructor = testMethod.getAnnotation(UnitTestConstructor.class);
			final UnitTestField unitTestField = testMethod.getAnnotation(UnitTestField.class);
			final UnitTestForCoverage unitTestForCoverage = testMethod.getAnnotation(UnitTestForCoverage.class);

			int caseIndex = 0;
			if (test != null) {
				caseIndex += 16;
			}
			if (unitTestForCoverage != null) {
				caseIndex += 8;
			}
			if (unitTestConstructor != null) {
				caseIndex += 4;
			}
			if (unitTestMethod != null) {
				caseIndex += 2;
			}
			if (unitTestField != null) {
				caseIndex += 1;
			}

			switch (caseIndex) {
			case 16:
				warningContainerBuilder.addMethodWarning(
						new MethodWarning(testMethod, WarningType.TEST_ANNOTATION_WITHOUT_UNIT_ANNOTATION));
				break;
			case 3:// fall through
			case 5:// fall through
			case 6:// fall through
			case 7:// fall through
			case 9:// fall through
			case 10:// fall through
			case 11:// fall through
			case 12:// fall through
			case 13:// fall through
			case 14:// fall through
			case 15:// fall through
			case 19:// fall through
			case 21:// fall through
			case 22:// fall through
			case 23:// fall through
			case 25:// fall through
			case 26:// fall through
			case 27:// fall through
			case 28:// fall through
			case 29:// fall through
			case 30:// fall through
			case 31:
				warningContainerBuilder
						.addMethodWarning(new MethodWarning(testMethod, WarningType.MULTIPLE_UNIT_ANNOTATIONS_PRESENT));
				break;
			case 1:
				warningContainerBuilder.addMethodWarning(
						new MethodWarning(testMethod, WarningType.UNIT_FIELD_ANNOTATION_WITHOUT_TEST_ANNOTATION));
				break;
			case 2:
				warningContainerBuilder.addMethodWarning(
						new MethodWarning(testMethod, WarningType.UNIT_METHOD_ANNOTATION_WITHOUT_TEST_ANNOTATION));
				break;

			case 4:
				warningContainerBuilder.addMethodWarning(
						new MethodWarning(testMethod, WarningType.UNIT_CONSTRUCTOR_ANNOTATION_WITHOUT_TEST_ANNOTATION));
				break;

			case 8:
				warningContainerBuilder.addMethodWarning(
						new MethodWarning(testMethod, WarningType.UNIT_COVERAGE_ANNOTATION_WITHOUT_TEST_ANNOTATION));
				break;

			case 0:// fall through
			case 24:
				// ignore
				break;
			case 17:
				probeFieldTest(testMethod, unitTestField);
				break;
			case 18:
				probeMethodTest(testMethod, unitTestMethod);
				break;
			case 20:
				probeConstructorTest(testMethod, unitTestConstructor);
				break;

			default:
				throw new RuntimeException("unhandled case index " + caseIndex);
			}
		}
	}

	private final class TestFileVisitor extends SimpleFileVisitor<Path> {
		@Override
		public FileVisitResult visitFile(final Path file, final BasicFileAttributes attr) {
			if (isJavaFile(file)) {
				final Class<?> c = getClassFromFile(data.testPath, file);
				probeTestClass(c);
			}
			return FileVisitResult.CONTINUE;
		}
	}

	private final Data data;

	private static class Data {
		private Path sourcePath;

		private Path testPath;

		public Data() {
		}

		public Data(Data data) {
			sourcePath = data.sourcePath;
			testPath = data.testPath;
		}
	}

	public final static Builder builder() {
		return new Builder();
	}

	public final static class Builder {
		private Builder() {
		}

		private Data data = new Data();

		public MetaInfoGenerator build() {
			validate();
			return new MetaInfoGenerator(new Data(data));
		}

		private void validate() {

		}

		public Builder setSourcePath(Path sourcePath) {
			data.sourcePath = sourcePath;
			return this;
		}

		public Builder setTestPath(Path testPath) {
			data.testPath = testPath;
			return this;
		}

	}

	private MetaInfoGenerator(Data data) {
		this.data = data;
	}

	private void checkSourceMethodCoverage() {
		for (Method method : sourceMethods) {
			if (!coveredSourceMethods.contains(method)) {
				warningContainerBuilder
						.addMethodWarning(new MethodWarning(method, WarningType.SOURCE_METHOD_REQUIRES_TEST));
			}
		}
	}

	private void checkSourceFieldCoverage() {
		for (Field field : sourceFields) {
			if (!coveredSourceFields.contains(field)) {
				warningContainerBuilder
						.addFieldWarning(new FieldWarning(field, WarningType.SOURCE_FIELD_REQUIRES_TEST));
			}
		}
	}

	private void checkSourceConstructorCoverage() {
		for (Constructor<?> constructor : sourceConstructors) {
			if (!coveredSourceConstructors.contains(constructor)) {
				this.warningContainerBuilder.addConstructorWarning(
						new ConstructorWarning(constructor, WarningType.SOURCE_CONSTRUCTOR_REQUIRES_TEST));
			}
		}
	}

	private void loadSourceClasses() {

		final SourceFileVisitor sourceFileVisitor = new SourceFileVisitor();
		try {
			Files.walkFileTree(data.sourcePath, sourceFileVisitor);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}

	}

	private void loadTestClasses() {
		final TestFileVisitor testFileVisitor = new TestFileVisitor();
		try {
			Files.walkFileTree(data.testPath, testFileVisitor);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	public MetaInfoContainer execute() {

		loadSourceClasses();

		loadTestClasses();

		checkSourceFieldCoverage();

		checkSourceMethodCoverage();

		checkSourceConstructorCoverage();

		return warningContainerBuilder.build();

	}

}
