package gov.hhs.aspr.ms.util.meta.codecount;
/**
 * A runner for the CodeCountReport that uses the command line arguments as directory strings.
 */
public class CodeCounts {
	public static void main(String[] args) {
		CodeCountReport.Builder codeCountReportBuilder = CodeCountReport.builder();
		for(String arg : args) {
			codeCountReportBuilder.addDirectory(arg);
		}
		CodeCountReport codeCountReport = codeCountReportBuilder.build();
		System.out.println(codeCountReport.getDetailsReport());
	}
}
