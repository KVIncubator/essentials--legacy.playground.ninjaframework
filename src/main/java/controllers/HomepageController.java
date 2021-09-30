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

import com.google.inject.Singleton;
import ninja.Result;
import ninja.Results;


@Singleton
public class HomepageController {

  public Result index() {

    return Results.html();

  }

  public Result helloWorldJson() {

    SimplePojo simplePojo = new SimplePojo();
    simplePojo.content = "Hello World! Hello Json!";
    simplePojo.index = 1;
    simplePojo.createdBy = "Dima Denisenko";

    return Results.json().render(simplePojo);

  }

  public static class SimplePojo {

    public String content;
    public Integer index;
    public String createdBy;

  }
}
