package com.first.choice.Tab;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.first.choice.Activity.HomActivity;
import com.first.choice.R;
import com.first.choice.Rest.ApiClient;
import com.first.choice.Rest.ApiInterface;
import com.first.choice.Rest.Datum;
import com.first.choice.Rest.Responce;

import com.first.choice.util.IntentUtil;
import com.first.choice.util.PrefUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.widget.LoginButton;


public class LoginFragment extends Fragment {
    private static final String EMAIL = "email";
    TextInputEditText inputEmail, inputPassword;
    TextInputLayout inputLayoutEmail, inputLayoutPassword;
    TextView tvForgot, tvbtnLogin;
    ApiInterface apiService;
    String Mail;
    ImageView ivimage, ivimagef;
    ImageView gmail_login;
    GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = LoginFragment.class.getSimpleName();
    private static final int RC_SIGN_IN = 110;
    private TextView textView;
    private LoginButton loginButton;
    private PrefUtil prefUtil;
    private IntentUtil intentUtil;
    private CallbackManager callbackManager;
    ProgressDialog pDialog;
    TextView tvbtnSubmit;
    public LoginFragment() {
        // Required empty public constructor
    }


    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity());
        callbackManager = CallbackManager.Factory.create();
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        SharedPreferences preferences = this.getActivity().getSharedPreferences("pref", MODE_PRIVATE);
        final Boolean Login = preferences.getBoolean("LOGIN", false);
//        ivimagef = (ImageView) view.findViewById(R.id.ivimagef);
        prefUtil = new PrefUtil(getActivity());
        intentUtil = new IntentUtil(getActivity());
//        loginButton = (LoginButton) view.findViewById(R.id.login_button);
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Profile profile = Profile.getCurrentProfile();
//                //info.setText(message(profile));
//
//                String userId = loginResult.getAccessToken().getUserId();
//                String accessToken = loginResult.getAccessToken().getToken();
//
//                // save accessToken to SharedPreference
//                prefUtil.saveAccessToken(accessToken);
//
//                String profileImgUrl = "https://graph.facebook.com/" + userId + "/picture?type=large";
//
//
//                Glide.with(getActivity())
//                        .load(profileImgUrl)
//                        .into(ivimagef);
//            }

//            @Override
//            public void onCancel() {
//                Toast.makeText(getActivity(), "Login attempt cancelled.", Toast.LENGTH_LONG).show();
//              //  info.setText("Login attempt cancelled.");
//            }
//
//            @Override
//            public void onError(FacebookException e) {
//                e.printStackTrace();
//                Toast.makeText(getActivity(), "Login attempt failed.", Toast.LENGTH_LONG).show();
//                //info.setText("Login attempt failed.");
//            }
//        });
        inputLayoutEmail = (TextInputLayout) view.findViewById(R.id.input_layout_email);

//        ivimage = (ImageView) view.findViewById(R.id.ivimage);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        inputLayoutPassword = (TextInputLayout) view.findViewById(R.id.input_layout_password);
        gmail_login = (ImageView) view.findViewById(R.id.plus_sign_in_button);
        gmail_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        inputEmail = (TextInputEditText) view.findViewById(R.id.etEmail);
        inputPassword = (TextInputEditText) view.findViewById(R.id.etPassword);
        tvForgot = (TextView) view.findViewById(R.id.tvforgot);
        tvbtnLogin = (TextView) view.findViewById(R.id.tvbtnLogin);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
                View promptsView1 = li.inflate(R.layout.forget, null);

                AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(getActivity());

                // set prompts.xml to alertdialog builder
                alertDialogBuilder1.setView(promptsView1);
                inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
                alertDialogBuilder1.setCancelable(true);
                final AlertDialog alertDialog1 = alertDialogBuilder1.create();



                inputLayoutEmail = (TextInputLayout) promptsView1.findViewById(R.id.input_layout_email);
                inputEmail = (TextInputEditText)promptsView1.findViewById(R.id.etEmail);

                tvbtnSubmit=(TextView)promptsView1.findViewById(R.id.tvbtnSubmit);
                tvbtnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Mail=="")
                        {
                            Toast.makeText(getActivity(), "Enter valid email.", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            pDialog = new ProgressDialog(getActivity());
                            pDialog.setMessage("Please wait...");
                            pDialog.setIndeterminate(false);
                            pDialog.setCancelable(false);
                            pDialog.show();
                            // Toast.makeText(LoginActivity.this,Mail, Toast.LENGTH_SHORT).show();

                            Call<Responce> call = apiService.forGetPassWord(Mail);
                            call.enqueue(new Callback<Responce>() {
                                @Override
                                public void onResponse(Call<Responce> call, Response<Responce> response) {
                                    // Toast.makeText(LoginActivity.this, "Please check the email sent to you to change password", Toast.LENGTH_SHORT).show();
                                    pDialog.dismiss();
                                    alertDialog1.dismiss();
                                    LayoutInflater li = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
                                    View promptsView1 = li.inflate(R.layout.password_reset, null);

                                    AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(getActivity());

                                    // set prompts.xml to alertdialog builder
                                    alertDialogBuilder1.setView(promptsView1);


                                    TextView tvbtnOk;


                                    tvbtnOk=(TextView)promptsView1.findViewById(R.id.tvbtnOk);

                                    alertDialogBuilder1
                                            .setCancelable(true);

                                    final AlertDialog alertDialog1 = alertDialogBuilder1.create();
                                    // show it
                                    alertDialog1.show();
                                    ((ViewGroup)alertDialog1.getWindow().getDecorView()).getChildAt(0).startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.shake));

                                    tvbtnOk.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            alertDialog1.dismiss();
                                        }
                                    });


                                }

                                @Override
                                public void onFailure(Call<Responce>call, Throwable t) {
                                    // Log error here since request failed
                                    Log.e(TAG, t.toString());
                                    pDialog.dismiss();
                                    Toast.makeText(getActivity(), "Something wrong try again.", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                });


                // show it
                alertDialog1.show();
            }
        });
        tvbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String mail = String.valueOf(inputEmail.getText());
                String password = String.valueOf(inputPassword.getText());
                if (mail == null || mail.isEmpty() || password == null || password.isEmpty()) {
                    if (mail == null || mail.isEmpty()) {
                        inputLayoutEmail.setError("Enter Your User EmailId");
                    } else {
                        inputLayoutEmail.setErrorEnabled(false);
                    }
                    if (password == null || password.isEmpty()) {
                        inputLayoutPassword.setError("Enter Your User password");
                    } else {
                        inputLayoutPassword.setErrorEnabled(false);
                    }
                } else {
                    login(mail, password);
                }


            }
        });
        return view;
    }


    private void login(final String mail, final String password) {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        Call<Responce> call = apiService.checkLogin(mail, password);
        call.enqueue(new Callback<Responce>() {

            @Override
            public void onResponse(Call<Responce> call, retrofit2.Response<Responce> response) {
                List<Datum> users = response.body().getData();
                pDialog.dismiss();
                if (response.body().getSuccess() == 1) {

                    Log.d(TAG, "USER ID :" + users.get(0).getCustomerId());
                    Log.d(TAG, "USER NAME:" + users.get(0).getCustomerName());
                    Log.d(TAG, "USER EMAIL:" + users.get(0).getCustomerEmail());
                    Log.d(TAG, "USER SATUS:" + response.body().getSuccess());

                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("myPref", MODE_PRIVATE).edit();
                    editor.putString("USER_ID", users.get(0).getCustomerId());
                    editor.putString("USER_NAME", users.get(0).getCustomerName());
                    editor.putString("USER_EMAIL", users.get(0).getCustomerEmail());
                    editor.putBoolean("LOGIN", true);
                    editor.commit();

                    Intent intent = new Intent(getActivity(), HomActivity.class);
                    startActivity(intent);

                    // Toast.makeText(getActivity(), "\n" + mail + "\n" + password + "\n" + "successss", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Invalid ID PASSWORD", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Toast.makeText(getActivity(), "Try....", Toast.LENGTH_SHORT).show();
            }
        });
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
        // Toast.makeText(LoginActivity.this,"\n"+mail+"\n"+password, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        deleteAccessToken();
        Profile profile = Profile.getCurrentProfile();

    }

    private void deleteAccessToken() {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null) {
                    //User logged out
                    prefUtil.clearToken();

                }
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (FacebookSdk.isInitialized()) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                Glide.with(getActivity()).load(personPhoto).placeholder(R.drawable.icon)
                        .into(ivimage);
                Toast.makeText(getActivity(), "google :- " + personName + personGivenName + personFamilyName + personEmail + personPhoto, Toast.LENGTH_SHORT).show();
            }
            // Signed in successfully, show authenticated UI.
            // updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            // updateUI(null);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.etEmail:
                    validateEmail();
                    break;
                case R.id.etPassword:
                    validatePassword();
                    break;
            }
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {

            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            Mail = inputEmail.getText().toString();
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}
