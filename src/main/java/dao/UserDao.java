/*
 * Copyright (C) 2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import models.User;
import ninja.jpa.UnitOfWork;


public class UserDao {

  Provider<EntityManager> entityManagerProvider;

  @Inject
  public UserDao(Provider<EntityManager> entityManagerProvider) {
    this.entityManagerProvider = entityManagerProvider;
  }

  /*
   * Get single result without throwing NoResultException, you can of course just catch the
   * exception and return null, it's up to you.
   */
  private static <T> T getSingleResult(TypedQuery<T> query) {
    query.setMaxResults(1);
    List<T> list = query.getResultList();
    if (list == null || list.isEmpty()) {
      return null;
    }

    return list.get(0);
  }

  @UnitOfWork
  public boolean isUserAndPasswordValid(String email, String password) {

    if (email != null && password != null) {

      EntityManager entityManager = entityManagerProvider.get();

      TypedQuery<User> q = entityManager.createQuery("SELECT x FROM User x WHERE email = :emailParam", User.class);
      User user = getSingleResult(q.setParameter("emailParam", email));

      if (user != null) {

        if (user.password.equals(password)) {

          return true;
        }

      }

    }

    return false;

  }

}
