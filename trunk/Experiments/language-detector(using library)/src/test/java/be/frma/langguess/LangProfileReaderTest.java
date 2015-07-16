/*
 * Copyright 2011 Francois ROLAND
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package be.frma.langguess;

import com.cybozu.labs.langdetect.util.LangProfile;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class LangProfileReaderTest {
	private static final File PROFILE_DIR = new File(new File(new File(new File("src"), "main"), "resources"), "languages");

	@Test
	public void readEnFile() throws IOException {
		checkProfileFile("en", 3, 2301);
	}

	@Test
	public void readBnFile() throws IOException {
		checkProfileFile("bn", 3, 2846);
	}

	@Test
	public void readFrFile() throws IOException {
		checkProfileFile("fr", 3, 2232);
	}

	@Test
	public void readNlFile() throws IOException {
		checkProfileFile("nl", 3, 2163);
	}


	private static void checkProfileFile(String language, int nWordSize, int freqSize) throws IOException {
		File profileFile = new File(PROFILE_DIR, language);
		final LangProfile langProfile = new LangProfileReader().read(profileFile);
		assertThat(langProfile, is(notNullValue()));
		assertThat(langProfile.getName(), is(equalTo(language)));
		assertThat(langProfile.getNWords(), is(notNullValue()));
		assertThat(langProfile.getNWords().length, is(equalTo(nWordSize)));
		assertThat(langProfile.getFreq(), is(notNullValue()));
		assertThat(langProfile.getFreq().size(), is(equalTo(freqSize)));
	}

}
