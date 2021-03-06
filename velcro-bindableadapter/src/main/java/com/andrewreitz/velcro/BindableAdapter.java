/*
 * Copyright 2014 Andrew Reitz
 * Copyright 2014 Jake Wharton
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

package com.andrewreitz.velcro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * An implementation of {@link android.widget.BaseAdapter} which uses the new/bind pattern for its
 * views.
 *
 * @author Jake Wharton
 * @author Andrew Reitz
 * @since 1.0.0
 */
public abstract class BindableAdapter<T> extends BaseAdapter {
  private final Context context;
  private final LayoutInflater inflater;

  public BindableAdapter(Context context) {
    this.context = context;
    this.inflater = LayoutInflater.from(context);
  }

  public Context getContext() {
    return context;
  }

  /** {@inheritDoc} */
  @Override public abstract T getItem(int position);

  /** {@inheritDoc} */
  @Override public final View getView(int position, View view, ViewGroup container) {
    if (view == null) {
      view = newView(inflater, position, container);
      if (view == null) {
        throw new IllegalStateException("newView result must not be null.");
      }
    }
    bindView(getItem(position), position, view);
    return view;
  }

  /**
   * Default implementation returns the current position as the id. Override for customization.
   *
   * {@inheritDoc}
   */
  @Override public long getItemId(int position) {
    return position;
  }

  /** Create a new instance of a view for the specified position. */
  public abstract View newView(LayoutInflater inflater, int position, ViewGroup container);

  /** Bind the data for the specified {@code position} to the view. */
  public abstract void bindView(T item, int position, View view);

  /** {@inheritDoc} */
  @Override public final View getDropDownView(int position, View view, ViewGroup container) {
    if (view == null) {
      view = newDropDownView(inflater, position, container);
      if (view == null) {
        throw new IllegalStateException("newDropDownView result must not be null.");
      }
    }
    bindDropDownView(getItem(position), position, view);
    return view;
  }

  /** Create a new instance of a drop-down view for the specified position. */
  public View newDropDownView(LayoutInflater inflater, int position, ViewGroup container) {
    return newView(inflater, position, container);
  }

  /** Bind the data for the specified {@code position} to the drop-down view. */
  public void bindDropDownView(T item, int position, View view) {
    bindView(item, position, view);
  }
}
