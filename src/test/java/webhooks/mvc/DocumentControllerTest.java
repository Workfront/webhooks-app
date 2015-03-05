/**
 * Copyright 2014 AtTask, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package webhooks.mvc;

import org.junit.*;
import org.mockito.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import webhooks.core.models.entities.*;
import webhooks.core.services.*;
import webhooks.core.services.util.*;
import webhooks.rest.mvc.*;
import webhooks.rest.resources.*;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 */
public class DocumentControllerTest {
	@InjectMocks
	private DocumentController controller;

	@InjectMocks
	private AccountController accountController;

	@Mock
	private IDocumentService service;

	private MockMvc mockMvc;
	private ArgumentCaptor<Document> documentCaptor;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(controller, accountController).build();

		documentCaptor = ArgumentCaptor.forClass(Document.class);
	}

/*	@Test
	public void getListFiles() throws Exception {
		List<Document> list = new ArrayList<Document>();
		Document documentA = new Document();
		documentA.setId("W301RZblpnQ2h0MkopWRokpt8yMEwEo4JEioCff1E9/fkKHm0F7B1PzOhYsnhou8");
		documentA.setKind("file");
		documentA.setSize(100L);
		documentA.setName("docA");
		list.add(documentA);

		Document documentB = new Document();
		documentB.setId("IehDHr6AZr03pkxQYtkXiGwoxIzhvrM1LJ35icT/BcC50Dv/QNfddn9OwcGznWw2");
		documentB.setKind("file");
		documentB.setSize(100L);
		documentB.setName("docB");
		list.add(documentB);

		DocumentList documentList = new DocumentList(list);
		when(service.getRootFiles(100, 0)).thenReturn(documentList);
		when(service.getFiles(anyString(), anyInt(), anyInt())).thenReturn(documentList);

		mockMvc.perform(get("/rest/api/files").param("accessToken", "1"))
			.andDo(print())
			.andExpect(jsonPath("$.[*].title",
				hasItems(endsWith("docA"), endsWith("docB"))))
			.andExpect(status().isOk());
	}
	*/

}
