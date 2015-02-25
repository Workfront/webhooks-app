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
