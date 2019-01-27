package me.darkpiv.gnanis.utils;

/*
 * Created by darkpiv on 1/27/19.
 */
public final class Indexed<T> {
  private final int index;
  private final T value;

  public Indexed(int index, T value) {
    this.index = index;
    this.value = value;
  }

  public int getIndex() {
    return index;
  }

  public T getValue() {
    return value;
  }
}
