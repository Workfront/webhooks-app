package webhooks.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import webhooks.core.services.util.AccountList;
import webhooks.rest.mvc.AccountController;
import webhooks.rest.resources.AccountListResource;
import webhooks.rest.resources.AccountResource;

import java.util.List;

public class AccountListResourceAsm extends ResourceAssemblerSupport<AccountList, AccountListResource> {


    public AccountListResourceAsm() {
        super(AccountController.class, AccountListResource.class);
    }

    @Override
    public AccountListResource toResource(AccountList accountList) {
        List<AccountResource> resList = new AccountResourceAsm().toResources(accountList.getAccounts());
        AccountListResource finalRes = new AccountListResource();
        finalRes.setAccounts(resList);
        return finalRes;
    }
}
