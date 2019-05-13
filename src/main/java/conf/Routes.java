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


package conf;

import controllers.*;
import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import ninja.utils.NinjaProperties;

import com.google.inject.Inject;

public class Routes implements ApplicationRoutes {

    @Inject
    NinjaProperties ninjaProperties;

    /**
     * Using a (almost) nice DSL we can configure the router.
     *
     * The second argument NinjaModuleDemoRouter contains all routes of a
     * submodule. By simply injecting it we activate the routes.
     *
     * @param router
     *            The default router of this application
     */
    @Override
    public void init(Router router) {

        router.GET().route("/").with(HomepageController::index);
        router.GET().route("/hello_world.json").with(HomepageController::helloWorldJson);
        router.GET().route("/list").with(ApplicationController::index);

        ///////////////////////////////////////////////////////////////////////
        // Login / Logout
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/login").with(LoginLogoutController::login);
        router.POST().route("/login").with(LoginLogoutController::loginPost);
        router.GET().route("/logout").with(LoginLogoutController::logout);

        ///////////////////////////////////////////////////////////////////////
        // Create new post
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/post/new").with(PostController::postNew);
        router.POST().route("/post/new").with(PostController::postNewPost);

        ///////////////////////////////////////////////////////////////////////
        // Create new post
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/post/{id}").with(PostController::postShow);

        ///////////////////////////////////////////////////////////////////////
        // Api for management of software
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/api/{email}/posts.json").with(ApiController::getPostsJson);
        router.GET().route("/api/{email}/post/{id}.json").with(ApiController::getPostJson);
        router.GET().route("/api/{email}/posts.xml").with(ApiController::getPostsXml);
        router.POST().route("/api/{email}/post.json").with(ApiController::postPostJson);
        router.POST().route("/api/{email}/post.xml").with(ApiController::postPostXml);

        ///////////////////////////////////////////////////////////////////////
        // Assets (pictures / javascript)
        ///////////////////////////////////////////////////////////////////////    
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController::serveWebJars);
        router.GET().route("/assets/{fileName: .*}").with(AssetsController::serveStatic);
        
        ///////////////////////////////////////////////////////////////////////
        // Index / Catchall shows index page
        ///////////////////////////////////////////////////////////////////////
        //router.GET().route("/.*").with(HomepageController::index);
    }

}
