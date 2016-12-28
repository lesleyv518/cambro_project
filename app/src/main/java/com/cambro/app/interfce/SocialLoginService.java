package com.cambro.app.interfce;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.cambro.app.DashboardActivity;
import com.cambro.app.R;
import com.cambro.app.fragment.persnalize.PersonalizeLastFragment;
import com.cambro.app.utils.Utils;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.parse.signpost.http.HttpResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class SocialLoginService {

    private static SocialLoginService instance = null;
    /**
     * @author Vinod Morya Handle operations regarding Facebook.
     */

    public static String[] permissions;
    //private static final List<String>	PERMISSIONS	= Arrays.asList("email", "friends_location", "friends_photos");
    private static final List<String> PERMISSIONS = Arrays.asList("public_profile", "email", "user_friends");

    /**
     * @param activity return SocialLoginService
     * @static this method is use to return facebook instance
     */

    public static SocialLoginService getInstance(Activity activity) {
        if (instance == null) {
            instance = new SocialLoginService(activity);
        }
        return instance;
    }


    private Activity activity;
    private Fragment mFragment;
    private String fbaccessToken = "";

    private SocialLoginService(Activity activity) {
        this.activity = activity;
    }

    public void facebookLogin(final Activity activity, final Fragment fragment) {

        this.activity = activity;
        this.mFragment = fragment;
        loginParseUserFacebook(activity, fragment);
//		LoginManager.getInstance().logInWithReadPermissions(this.activity, Arrays.asList("public_profile", "user_friends", "email"));
//		LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//
////				Toast.makeText(activity, "Login Successed.", Toast.LENGTH_LONG).show();
//                final String accessToken = loginResult.getAccessToken().getToken();
//                GraphRequest request = GraphRequest.newMeRequest(
//                        loginResult.getAccessToken(),
//                        new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
//                                try {
//
//                                    Reg.facebookId = jsonObject.getString("id");
//                                    Reg.email_address = jsonObject.getString("email");
//                                    Reg.user_name = jsonObject.getString("name");
//                                    Reg.gender = jsonObject.getString("gender");
//                                    Reg.first_name = jsonObject.getString("first_name");
//                                    Reg.last_name = jsonObject.getString("last_name");
//
////                                    loginUserToParseFromFB();
//
//                                    if (activity instanceof DashboardActivity) {
//
//                                    } else if (fragment instanceof PersonalizeLastFragment) {
//
//                                    }
//
//                                } catch (Exception e) {
//                                    Log.e("FacebookLogin", e.toString());
//                                }
//                            }
//                        });
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id, name, email, gender, first_name, last_name");
//                request.setParameters(parameters);
//                request.executeAsync();
//            }
//
//            @Override
//            public void onCancel() {
//                Toast.makeText(activity, "Login canceled.", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onError(FacebookException e) {
//                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
    }

    public void twitterLogin(final Activity activity, final Fragment fragment) {

        this.activity = activity;
        this.mFragment = fragment;
        loginParseUserTwitter(activity, fragment);
    }

    private void loginParseUserTwitter(final Activity ac, final Fragment fragment)
    {
        ParseTwitterUtils.logIn(ac, new LogInCallback() {
            @Override
            public void done(final ParseUser user, com.parse.ParseException e) {
                if (user == null) {
                    Log.d("Twitter", "Uh oh. The user cancelled the Twitter login.");
                } else if (user.isNew()) {
                    Log.d("MyApp", "User signed up and logged in through Twitter!");
                } else {

                    if (!ParseTwitterUtils.isLinked(user)) {
                        ParseTwitterUtils.link(user, ac, new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (ParseTwitterUtils.isLinked(user)) {
                                    new GetTwitterUserInfo().execute();
                                }
                            }
                        });
                    } else {
                        new GetTwitterUserInfo().execute();
                    }

                }
            }
        });
    }

    private void loginParseUserFacebook(final Activity ac, final Fragment fragment)
    {
        List<String> permissionNeeds = Arrays.asList("public_profile", "user_friends", "email");
        ParseFacebookUtils.logInWithReadPermissionsInBackground(ac, permissionNeeds, new LogInCallback() {
            @Override
            public void done(final ParseUser user, ParseException e) {
                if (user == null) {
                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                } else {
                    AccessToken token = AccessToken.getCurrentAccessToken();
                    GraphRequest request = GraphRequest.newMeRequest(
                            token,
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                                    try {

                                        Reg.facebookId = jsonObject.getString("id");
                                        Reg.email_address = jsonObject.getString("email");
                                        Reg.user_name = jsonObject.getString("name");
                                        Reg.gender = jsonObject.getString("gender");
                                        Reg.first_name = jsonObject.getString("first_name");
                                        Reg.last_name = jsonObject.getString("last_name");

                                        user.setUsername(Reg.email_address);
                                        user.setPassword(Reg.facebookId);
                                        user.setEmail(Reg.email_address);
                                        user.put("name", Reg.first_name + " " + Reg.last_name);
                                        user.saveInBackground();

                                        SharedPreferences prefs = activity.getSharedPreferences("Cambro_Login", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.putString("username", Reg.facebookId);
                                        editor.putString("fullname", Reg.first_name + " " + Reg.last_name);
                                        editor.putString("email", Reg.email_address);
                                        editor.commit();

                                        if (activity instanceof DashboardActivity) {

                                            FragmentTransaction tr = ((DashboardActivity) activity).getSupportFragmentManager().beginTransaction();
                                            tr.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
                                            tr.replace(R.id.fragment_container, ((DashboardActivity) activity).adp.getItem(4));
                                            tr.addToBackStack(null);
                                            tr.commit();
                                        } else if (fragment instanceof PersonalizeLastFragment) {
                                            SharePhoto photo = new SharePhoto.Builder()
                                                    .setBitmap(Reg.ptLastImage)
                                                    .setCaption("Welcome To Facebook Photo Sharing on Cambro Product!")
                                                    .build();

                                            SharePhotoContent content = new SharePhotoContent.Builder()
                                                    .addPhoto(photo)
                                                    .build();
                                            ShareDialog shareDialog = new ShareDialog(fragment);
                                            shareDialog.show(fragment, content);
                                        }

                                    } catch (Exception e) {
                                        Log.e("FacebookLogin", e.toString());
                                    }
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id, name, email, gender, first_name, last_name");
                    request.setParameters(parameters);
                    request.executeAsync();
                }
            }
        });
    }

    private void loginParseUserFromFB() {
        ParseUser.logInInBackground(Reg.facebookId, Reg.facebookId, new LogInCallback() { //
            @Override
            public void done(final ParseUser parseUser, ParseException e) {
                if (parseUser != null) {
                    if(activity instanceof DashboardActivity)
                    {
                        FragmentTransaction tr = ((DashboardActivity)activity).getSupportFragmentManager().beginTransaction();
                        tr.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
                        tr.replace(R.id.fragment_container, ((DashboardActivity)activity).adp.getItem(4));
                        tr.addToBackStack(null);
                        tr.commit();
                    }
                } else {
                    ParseUser user = ParseUser.getCurrentUser();
                    if (user != null) return;
                    Log.e("fb", Reg.facebookId + Reg.email_address + ":" + Reg.first_name);
                    user = new ParseUser();
                    user.setUsername(Reg.email_address);
                    user.setPassword(Reg.facebookId);
                    user.setEmail(Reg.email_address);
                    user.put("name", Reg.first_name + " " + Reg.last_name);
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if (e == null) {
                                // Show a simple Toast message upon successful registration
                                SharedPreferences prefs = ((DashboardActivity)activity).getSharedPreferences("Cambro_Login", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("username", Reg.facebookId);
                                editor.putString("email", Reg.email_address);
                                editor.commit();

                                FragmentTransaction tr = ((DashboardActivity)activity).getSupportFragmentManager().beginTransaction();
                                tr.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
                                tr.replace(R.id.fragment_container, ((DashboardActivity)activity).adp.getItem(4));
                                tr.addToBackStack(null);
                                tr.commit();
                            } else {
                                Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }


    /**
     * this method is use to logout from facebook a/c
     */
    public void fbLogout() {
//        LoginManager.getInstance().logOut();
    }

    public class GetTwitterUserInfo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String str = null;
            HttpClient httpclient = new DefaultHttpClient();// Utils.createHttpClient();
            HttpGet verifyGet = new HttpGet("https://api.twitter.com/1.1/account/verify_credentials.json?include_email=true");

                ParseTwitterUtils.getTwitter().signRequest(verifyGet);
                try {
                    org.apache.http.HttpResponse response = httpclient.execute(verifyGet);
                    str = EntityUtils.toString(response.getEntity());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }


            return str;
        }

        @Override
        protected void onPostExecute(String result) {

            if(result == null) return;
//            Log.d("twitter", result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                Reg.twitterId = jsonObject.getString("id_str");
                if (!jsonObject.isNull("email"))
                    Reg.email_address = jsonObject.getString("email");
                Reg.user_name = jsonObject.getString("name");
                String[] s = Reg.user_name.split(" ");
                if (s.length > 1)
                {
                    Reg.first_name = s[0];
                    Reg.last_name = s[0];
                }
                else
                {
                    Reg.first_name = Reg.user_name;
                }

                ParseUser user = ParseUser.getCurrentUser();
                user.setUsername(Reg.twitterId);
                user.setPassword(Reg.twitterId);
                if (Reg.email_address != null)
                    user.setEmail(Reg.email_address);
                user.put("name", Reg.user_name);
                user.saveInBackground();

                SharedPreferences prefs = activity.getSharedPreferences("Cambro_Login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", Reg.twitterId);
                editor.putString("fullname", Reg.user_name);
                if (Reg.email_address != null)
                    editor.putString("email", Reg.email_address);
                else
                    editor.putString("email", null);
                editor.commit();

                if (activity instanceof DashboardActivity) {

                    FragmentTransaction tr = ((DashboardActivity) activity).getSupportFragmentManager().beginTransaction();
                    tr.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
                    tr.replace(R.id.fragment_container, ((DashboardActivity) activity).adp.getItem(4));
                    tr.addToBackStack(null);
                    tr.commit();
                } else if (mFragment instanceof PersonalizeLastFragment) {
                    SharePhoto photo = new SharePhoto.Builder()
                            .setBitmap(Reg.ptLastImage)
                            .setCaption("Welcome To Facebook Photo Sharing on Cambro Product!")
                            .build();

                    SharePhotoContent content = new SharePhotoContent.Builder()
                            .addPhoto(photo)
                            .build();
                    ShareDialog shareDialog = new ShareDialog(mFragment);
                    shareDialog.show(mFragment, content);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
