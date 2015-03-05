/**
 * Copyright 2015 Workfront, Inc.
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
package webhooks.core.repositories;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;
import webhooks.core.models.entities.*;

import javax.transaction.*;

import static org.junit.Assert.assertNotNull;

/**
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/business-config.xml")
public class AccountRepoTest {

	@Autowired
	private IAccountRepo repo;

	private Account account;

	@Before
	@Transactional
	@Rollback(false)
	public void setup() {
		account = new Account();
		account.setName("name");
		account.setPassword("password");
		account.setApiKey("11111");
		repo.createAccount(account);
	}


	@Test
	@Transactional
	public void testFind() {

		assertNotNull(repo.findAccountById(account.getId()));
	}
}
