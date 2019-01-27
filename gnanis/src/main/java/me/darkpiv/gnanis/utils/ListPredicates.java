package me.darkpiv.gnanis.utils;

import com.annimon.stream.function.Predicate;

import java.util.List;

/*
 * Created by darkpiv on 1/27/19.
 */
public class ListPredicates {

  public static final <T> Predicate<List<T>> isEmpty() {
    return value -> value == null || value.isEmpty();
  }

  public static final <T> Predicate<List<T>> contains(T value) {
    return x -> x.contains(value);
  }
}
