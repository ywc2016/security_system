package securitySystem;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestSomething {
	@Test
	public void deleteByDirTest() {
		try {
			FileUtils.deleteDirectory(new File("F:/desktop/JM715453687R90250000000 (1).files"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
