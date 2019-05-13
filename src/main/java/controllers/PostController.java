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

package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dao.PostDao;
import etc.LoggedInUser;
import models.Post;
import models.PostDto;
import ninja.*;
import ninja.params.PathParam;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;

@Singleton
public class PostController {
    
    @Inject
    PostDao postDao;

    ///////////////////////////////////////////////////////////////////////////
    // Show post
    ///////////////////////////////////////////////////////////////////////////
    public Result postShow(@PathParam("id") Long id) {

        Post post = null;

        if (id != null) {

            post = postDao.getPost(id);

        }

        return Results.html().render("post", post);

    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Create new post
    ///////////////////////////////////////////////////////////////////////////
    @FilterWith(SecureFilter.class)
    public Result postNew() {

        return Results.html();

    }

    @FilterWith(SecureFilter.class)
    public Result postNewPost(@LoggedInUser String email,
                                 Context context,
                                 @JSR303Validation PostDto postDto,
                                 Validation validation) {

        if (validation.hasViolations()) {

            context.getFlashScope().error("Please correct field.");
            context.getFlashScope().put("title", postDto.title);
            context.getFlashScope().put("content", postDto.content);

            return Results.redirect("/post/new");

        } else {
            
            postDao.postPost(email, postDto);
            
            context.getFlashScope().success("New post created.");
            
            return Results.redirect("/list");

        }

    }

}
