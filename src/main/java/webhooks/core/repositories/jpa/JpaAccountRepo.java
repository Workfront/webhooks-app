package webhooks.core.repositories.jpa;

import org.springframework.stereotype.Repository;
import webhooks.core.models.entities.Account;
import webhooks.core.repositories.IAccountRepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class JpaAccountRepo implements IAccountRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Account> getAllAccounts() {
        Query query = em.createQuery("SELECT a FROM Account a");
        return query.getResultList();
    }

    @Override
    public Account findAccountById(Long id) {
        return em.find(Account.class, id);
    }

    @Override
    public Account findAccountByName(String name) {
        Query query = em.createQuery("SELECT a FROM Account a WHERE a.name=?1");
        query.setParameter(1, name);
        List<Account> accounts = query.getResultList();
        if(accounts.size() == 0) {
            return null;
        } else {
            return accounts.get(0);
        }
    }

    @Override
    public Account createAccount(Account data) {
        em.persist(data);
        return data;
    }
}
