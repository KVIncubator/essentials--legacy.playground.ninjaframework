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

package controllers;

import com.google.inject.Inject;
import dao.PostDao;
import etc.LoggedInUser;
import java.util.Collections;
import javax.persistence.NoResultException;
import models.Post;
import models.PostDto;
import models.PostsDto;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.SecureFilter;
import ninja.params.PathParam;

public class ApiController {

  private PostDao postDao;

  @Inject
  public ApiController(PostDao postDao) {
    this.postDao = postDao;
  }

  public Result getPostsJson() {

    PostsDto postsDto = postDao.getAllPosts();

    return Results.json().render(postsDto);

  }

  public Result getPostsXml() {

    PostsDto postsDto = postDao.getAllPosts();

    return Results.xml().render(postsDto);

  }

  public Result getPostJson(@PathParam("id") Long id) {

    try {
      Post post = postDao.getPost(id);
      return Results.json().render(post);
    } catch (NoResultException e) {
      return Results.notFound().json().render(Collections.emptyMap());
    }


  }

  @FilterWith(SecureFilter.class)
  public Result createPostJson(@LoggedInUser String email,
                               PostDto postDto) {

    boolean succeeded = postDao.createPost(email, postDto);

    if (!succeeded) {
      return Results.notFound().json().render(Collections.emptyMap());
    } else {
      return Results.json();
    }

  }

  @FilterWith(SecureFilter.class)
  public Result createPostXml(@LoggedInUser String email,
                              PostDto postDto) {

    boolean succeeded = postDao.createPost(email, postDto);

    if (!succeeded) {
      return Results.notFound().xml().render(Collections.emptyMap());
    } else {
      return Results.xml();
    }

  }
}
