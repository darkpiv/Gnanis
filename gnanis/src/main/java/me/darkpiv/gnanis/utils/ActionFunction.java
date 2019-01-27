package me.darkpiv.gnanis.utils;

/*
 * Created by darkpiv on 1/27/19.
 */
public interface ActionFunction<T> {
  default void ifMatch(T value) {}

  default void ifNot() {}
}
