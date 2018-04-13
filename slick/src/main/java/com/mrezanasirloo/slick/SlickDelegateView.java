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

package com.mrezanasirloo.slick;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.view.View;


/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2016-11-03
 */

public class SlickDelegateView<V, P extends SlickPresenter<V>> {

    private int id;
    private InternalOnDestroyListener listener;

    private P presenter;
    /**
     * to ensure not to call onViewDown after onDestroy
     */
    private boolean hasOnViewDownCalled = false;

    public SlickDelegateView(@NonNull P presenter, Class ignored, int id) {
        //noinspection ConstantConditions
        if (presenter == null) {
            throw new IllegalStateException("Presenter cannot be null.");
        }
        this.presenter = presenter;
        this.id = id;
    }

    private static final String TAG = SlickDelegateView.class.getSimpleName();

    public void onAttach(@NonNull V view) {
        presenter.onViewUp(view);
        hasOnViewDownCalled = false;
    }

    public void onDetach(Object ignored) {
        presenter.onViewDown();
        hasOnViewDownCalled = true;
    }

    public void onDestroy(@NonNull V view) {
        destroy(getActivity((View) view));
    }

    public void onDestroy(@NonNull Activity activity) {
        destroy(activity);
    }

    private void destroy(@NonNull Activity activity) {
        if (!activity.isChangingConfigurations()) {
            if (!hasOnViewDownCalled) onDetach(null);
            presenter.onDestroy();
            if (listener != null) {
                listener.onDestroy(id);
            }
            presenter = null;
        }
    }

    public P getPresenter() {
        return presenter;
    }

    public void setListener(@NonNull InternalOnDestroyListener listener) {
        this.listener = listener;
    }


    public static int getId(@NonNull Object view) {
        if (view instanceof SlickUniqueId) return ((SlickUniqueId) view).getUniqueId().hashCode();
        return -1;
    }

    @NonNull
    public static Activity getActivity(@NonNull View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        throw new IllegalStateException("Cannot find the activity, Are you sure the supplied view was attached?");
    }

}
