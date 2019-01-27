package me.darkpiv.gnanis;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import me.darkpiv.gnanis.utils.ActionFunction;

public class MainActivity extends AppCompatActivity {

  private User getUserProfile() {
    User user = new User();
    user.setFacebookAccount(new FacebookAccount());
    user.setId(String.valueOf(System.currentTimeMillis()));
    user.setName(null);
    user.setBirthday("25/02/1994");
    user.setLocation("Viet Nam");
    user.setPhone("012125215");
    return user;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.sadasd);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
          }
        });

    Gnanis.of(getUserProfile())
        .call(User::getFacebookAccount)
        .call(FacebookAccount::isExpired)
        .eventually(new ActionFunction<Boolean>() {
            @Override
            public void ifMatch(Boolean value) {

            }

            @Override
            public void ifNot() {

            }
        });
  }

  public class User {
    String id;
    String name;
    String birthday;
    String location;
    String phone;

    FacebookAccount facebookAccount;

    public FacebookAccount getFacebookAccount() {
      return facebookAccount;
    }

    public User setFacebookAccount(FacebookAccount facebookAccount) {
      this.facebookAccount = facebookAccount;
      return this;
    }

    public String getId() {
      return id;
    }

    public User setId(String id) {
      this.id = id;
      return this;
    }

    public String getName() {
      return name;
    }

    public User setName(String name) {
      this.name = name;
      return this;
    }

    public String getBirthday() {
      return birthday;
    }

    public User setBirthday(String birthday) {
      this.birthday = birthday;
      return this;
    }

    public String getLocation() {
      return location;
    }

    public User setLocation(String location) {
      this.location = location;
      return this;
    }

    public String getPhone() {
      return phone;
    }

    public User setPhone(String phone) {
      this.phone = phone;
      return this;
    }
  }

  public class FacebookAccount {
    String facebookId;
    boolean isExpired;

    public String getFacebookId() {
      return facebookId;
    }

    public FacebookAccount setFacebookId(String facebookId) {
      this.facebookId = facebookId;
      return this;
    }

    public boolean isExpired() {
      return isExpired;
    }

    public FacebookAccount setExpired(boolean expired) {
      isExpired = expired;
      return this;
    }
  }
}
