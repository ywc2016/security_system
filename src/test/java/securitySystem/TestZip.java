package securitySystem;

import org.junit.Test;

import edu.buaa.sem.document.service.DocumentBackupService;

public class TestZip {
	private DocumentBackupService documentBackupService = new DocumentBackupService();

	@Test
	public void ZipMultiFileTest() {
		long a = System.currentTimeMillis();
		documentBackupService.ZipMultiFile("F:/security_space/document", "F:/security_space/backup/test.zip");
		System.out.println(System.currentTimeMillis() - a);
	}
}
