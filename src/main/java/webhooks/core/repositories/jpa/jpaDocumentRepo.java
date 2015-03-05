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
