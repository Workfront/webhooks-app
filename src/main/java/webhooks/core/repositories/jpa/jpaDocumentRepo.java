package webhooks.core.repositories.jpa;

import org.springframework.stereotype.*;
import webhooks.core.models.entities.*;
import webhooks.core.repositories.*;

import javax.persistence.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: alextan
 * Date: 12/10/14
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */

@Repository
public class jpaDocumentRepo implements IDocumentRepo {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Document> findAllDocuments() {
		Query query = em.createQuery("SELECT a FROM Document a");
		return query.getResultList();
	}
	@Override
	public Document createDocument(Document data) {
		em.persist(data);
		return data;
	}

	@Override
	public Document findDocumentByID(String id) {
		Query query = em.createQuery("SELECT a FROM Document a WHERE a.id=?1");
		query.setParameter(1, id);
		List<Document> documents = query.getResultList();
		if (documents.size() == 0){
			return null;
		}

		return documents.get(0);
	}
}
