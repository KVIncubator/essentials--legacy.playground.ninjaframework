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

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import dao.PostDao;
import java.util.List;
import java.util.Map;
import models.Post;
import ninja.Result;
import ninja.Results;

public class ApplicationController {

  PostDao postDao;

  @Inject
  public ApplicationController(PostDao postDao) {
    this.postDao = postDao;
  }

  public Result index() {

    Post frontPost = postDao.getFirstPostForFrontPage();

    List<Post> olderPosts = postDao.getOlderPostsForFrontPage();

    Map<String, Object> map = Maps.newHashMap();
    map.put("frontPost", frontPost);
    map.put("noRecordsFound", !olderPosts.isEmpty());
    map.put("olderPosts", olderPosts);

    return Results.html().render("frontPost", frontPost)
        .render("olderPosts", olderPosts);

  }
}
