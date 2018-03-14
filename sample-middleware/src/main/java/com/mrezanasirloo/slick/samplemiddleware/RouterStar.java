/*
 * Copyright 2018. M. Reza Nasirloo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mrezanasirloo.slick.samplemiddleware;

import com.mrezanasirloo.slick.Middleware;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-29
 */

public class RouterStar {

    private RepositoryStar repositoryStar = new RepositoryStar();

    @Middleware({MiddlewareInternetAccess.class, MiddlewareLogin.class})
    public Observable<Boolean> star(String id) {
        return repositoryStar.star(id);
    }

    @Middleware({MiddlewareInternetAccess.class, MiddlewareLogin.class})
    public Completable unStar(String id) {
        return repositoryStar.unStar(id);
    }
}
