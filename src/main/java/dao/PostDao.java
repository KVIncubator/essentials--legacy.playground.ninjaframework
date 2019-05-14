/**
 * Copyright (C) 2019 the original author or authors.
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

package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.Post;
import models.PostDto;
import models.PostsDto;
import models.User;
import ninja.jpa.UnitOfWork;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public class PostDao {

    Provider<EntityManager> entitiyManagerProvider;

    @Inject
    public PostDao(Provider<EntityManager> entitiyManagerProvider) {
        this.entitiyManagerProvider = entitiyManagerProvider;
    }

    @UnitOfWork
    public PostsDto getAllPosts() {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Post> q = entityManager.createQuery("SELECT x FROM Post x", Post.class);
        List<Post> posts = q.getResultList();

        PostsDto postsDto = new PostsDto();
        postsDto.posts = posts;
        
        return postsDto;
        
    }
    
    @UnitOfWork
    public Post getFirstPostForFrontPage() {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Post> q = entityManager.createQuery("SELECT x FROM Post x ORDER BY x.createdAt DESC", Post.class);
        Post post = q.setMaxResults(1).getSingleResult();
        
        return post;
        
        
    }
    
    @UnitOfWork
    public List<Post> getOlderPostsForFrontPage() {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Post> q = entityManager.createQuery("SELECT x FROM Post x ORDER BY x.createdAt DESC", Post.class);
        List<Post> posts = q.setFirstResult(1).setMaxResults(10).getResultList();
        
        return posts;
        
        
    }
    
    @UnitOfWork
    public Post getPost(Long id) {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Post> q = entityManager.createQuery("SELECT x FROM Post x WHERE x.id = :idParam", Post.class);
        Post post = q.setParameter("idParam", id).getSingleResult();
        
        return post;
        
        
    }
    
    /**
     * Returns false if user cannot be found in database.
     */
    @Transactional
    public boolean createPost(String email, PostDto postDto) {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<User> q = entityManager.createQuery("SELECT x FROM User x WHERE email = :emailParam", User.class);
        User user = q.setParameter("emailParam", email).getSingleResult();
        
        if (user == null) {
            return false;
        }
        
        Post post = new Post(user, postDto.title, postDto.content);
        entityManager.persist(post);
        
        return true;
        
    }

}
