package webhooks.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import webhooks.core.models.entities.Account;
import webhooks.rest.mvc.AccountController;
import webhooks.rest.resources.AccountResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

public class AccountResourceAsm extends ResourceAssemblerSupport<Account, AccountResource> {
    public AccountResourceAsm() {
        super(AccountController.class, AccountResource.class);
    }

    @Override
    public AccountResource toResource(Account account) {
        AccountResource res = new AccountResource();
        res.setName(account.getName());
        res.setPassword(account.getPassword());
		res.setApiKey(account.getApiKey());
        res.add(linkTo(methodOn(AccountController.class).getAccount(account.getId())).withSelfRel());
        return res;
    }
}
