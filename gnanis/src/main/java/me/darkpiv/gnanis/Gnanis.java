package me.darkpiv.gnanis;

import com.annimon.stream.Objects;
import com.annimon.stream.function.Function;
import com.annimon.stream.function.Predicate;

import me.darkpiv.gnanis.utils.ActionFunction;

/**
 * A fluent interface used for replacing nested conditional checks for null. Provides similar
 * functionality to null-safe navigation / null-conditional operators seen in some languages (e.g.
 * in C#, user?.getEmail()?.getDomain()?.length()).
 *
 * <p>Here is an example of safely retrieving user.getEmail().getDomain().length() without a
 * NullPointerException:
 *
 * <pre>{@code
 * int length = Gnanis.of(user)
 *                       .call(User::getEmail)
 *                       .call(Email::getDomain)
 *                       .call(String::length)
 *                       .getOrDefault(0));
 *
 * }</pre>
 *
 * The above would replace the equivalent nested conditional statements:
 *
 * <pre>{@code
 * int length = 0;
 * if(user != null) {
 *     Email userEmail = user.getEmail();
 *     if(userEmail != null ) {
 *         String domain = userEmail.getDomain();
 *         if(domain != null) {
 *             length = domain.length();
 *         }
 *     }
 * }
 *
 * }</pre>
 *
 * @param <T> a type which may be null
 */
public final class Gnanis<T> {
  private T val;

  private Gnanis(T val) {
    // static method use only
    this.val = val;
  }

  /**
   * Static factory method for obtaining a Gnanis instance.
   *
   * @param t an instance of a data class
   * @param <T> the type of the data class
   * @return a Gnanis&lt;T&gt; instance wrapping an instance of T
   */
  public static <T> Gnanis<T> of(T t) {
    return new Gnanis<>(t);
  }

  /**
   * Applies the supplied method reference to the wrapped value if it is not null.
   *
   * @param methodRef a reference to a method that can be applied to the wrapped instance of T
   * @param <U> the resulting type of the method reference
   * @return if the wrapped instance of T is null, returns an instance of Gnanis&lt;U&gt; wrapping
   *     null; otherwise, returns an instance of Gnanis&lt;U&gt; wrapping the result of applying the
   *     method reference
   */
  public <U> Gnanis<U> call(Function<T, U> methodRef) {
    U nextStep = get(methodRef);
    return of(nextStep);
  }

  /**
   * Get the value wrapped by this instance.
   *
   * @return the wrapped value, even if it is null
   */
  public T get() {
    return val;
  }

  /**
   * Useful when not chaining calls with the call method. Let's say that you have an object user
   * with a getEmail() method but user may be null. In that case, you can save some code by calling:
   *
   * <pre>{@code
   * Gnanis.of(user).get(User::getEmail);
   *
   * }</pre>
   *
   * This is a shorter alternative than:
   *
   * <pre>{@code
   * Gnanis.of(user).call(User::getEmail).get();
   *
   * }</pre>
   *
   * @param methodRef a method reference to apply to the wrapped instance of T
   * @param <U> the resulting type of the method reference
   * @return if the wrapped instance of T is null, returns null; otherwise returns the result of
   *     applying the method reference to the wrapped instance of T
   */
  public <U> U get(Function<T, U> methodRef) {
    Objects.requireNonNull(methodRef);
    return (val == null) ? null : methodRef.apply(val);
  }

  /**
   * Extends of function get but invoke action if it's null or it's not null;
   *
   * @param action: a action funtion invoke if null or if not.
   */
  public void eventually(ActionFunction<T> action) {
    eventually(action, Objects::nonNull);
  }

  /**
   * Extends of get func.
   *
   * @param action : action if match predicate or not
   * @param predicate : predicate to invoke action.
   */
  public void eventually(ActionFunction<T> action, Predicate<T> predicate) {
    Objects.requireNonNull(action);
    if (predicate.test(val)) {
      action.ifMatch(val);
    } else {
      action.ifNot();
    }
  }

  /**
   * Get the value wrapped by this instance of Gnanis if it is not null; otherwise return the
   * default value.
   *
   * @param defaultVal the value to return when the wrapped value is null
   * @return the wrapped value if not null; otherwise return the default value
   */
  public T getOrDefault(T defaultVal) {
    return (val == null) ? defaultVal : val;
  }

  /**
   * Get the value wrapped by this instance of Gnanis if it is not null; otherwise throw the passed
   * throwable
   *
   * @param throwable a throwable that is thrown if the wrapped value is null
   * @return the value wrapped by this instance of Gnanis if it is not null
   * @throws Throwable when the wrapped value is null
   */
  public T getOrThrow(Throwable throwable) throws Throwable {
    if (val == null) {
      throw throwable;
    } else {
      return val;
    }
  }
}
