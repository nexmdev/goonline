package com.nexm.ghatanjionline.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.Main2Activity;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.Customer;
import com.nexm.ghatanjionline.util.OTPEditText;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUpFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SignUpFragment extends Fragment implements OTPEditText.Listener {

    private OnFragmentInteractionListener mListener;
    private TextView emailTextView,passwordTextView,generateOTP,skip,info,sms_charge_disclaimer,saveProfile,topTextView;

    private ImageView signin_image;
    private OTPEditText otp1,otp2,otp3,otp4,otp5,otp6;
    private LinearLayout otpLayout,monoLayout;
    private EditText mobileNo, profileName,profileMiddleName,profileSurname,profileAddress,profileAlternateMoNo,profileEmail;
    private String enteredMoNo,customerID;
    private ProgressBar progressBar;
    private Dialog progress_dialog;
    private ScrollView signUpView;
    private LinearLayout signInView;

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private FirebaseAuth mAuth;
    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);



        generateOTP = view.findViewById(R.id.signin_generate_otp);
        signin_image = view.findViewById(R.id.sign_in_image);
        skip = view.findViewById(R.id.sign_in_skip);
        info = view.findViewById(R.id.sign_in_info);
        otpLayout =  view.findViewById(R.id.sign_in_otp);
        monoLayout =  view.findViewById(R.id.sign_in_mono_layout);
        sms_charge_disclaimer = view.findViewById(R.id.signin_sms_charge_disclaimer);
        mobileNo = view.findViewById(R.id.signin_mobile_editText);
        progressBar = view.findViewById(R.id.sign_in_progressBar);
        otp1 = view.findViewById(R.id.otp1);
        otp2 = view.findViewById(R.id.otp2);
        otp3 = view.findViewById(R.id.otp3);
        otp4 = view.findViewById(R.id.otp4);
        otp5 = view.findViewById(R.id.otp5);
        otp6 = view.findViewById(R.id.otp6);

        profileName = view.findViewById(R.id.sign_up_edittext_user_name);
        profileMiddleName = view.findViewById(R.id.sign_up_user_middle_name);
        profileSurname = view.findViewById(R.id.sign_up_user_surname);
        profileAddress = view.findViewById(R.id.sign_up_user_address);
        profileAlternateMoNo = view.findViewById(R.id.sign_up_user_phone);
        profileEmail = view.findViewById(R.id.sign_up_user_email);
        saveProfile =  view.findViewById(R.id.save_address_button);
        signUpView =  view.findViewById(R.id.signup_view);
        signInView =  view.findViewById(R.id.signin_view);
        topTextView =  view.findViewById(R.id.sign_up_top_textview);

        signUpView.setVisibility(View.GONE);
        signInView.setVisibility(View.VISIBLE);

        emailTextView = (TextView)view.findViewById(R.id.sign_up_user_email);
       // passwordTextView = (TextView)view.findViewById(R.id.sign_up_user_password);
        progress_dialog = customProgressDialog();
        setupListener();

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        generateOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // progressBar.setVisibility(View.VISIBLE);
                if(generateOTP.getText().toString().matches("Generate OTP")) {
                    enteredMoNo = mobileNo.getText().toString();
                    if (enteredMoNo.length() != 10) {
                        mobileNo.setError("Enter correct mobile number");
                        progressBar.setVisibility(View.GONE);
                        return;
                    }
                    progress_dialog.show();
                    startPhoneNumberVerification("+91"+enteredMoNo);
                }else{
                   if(validateCode().matches("INVALID")){
                       return;
                   }else {
                       progress_dialog.show();
                        verifyPhoneNumberWithCode(mVerificationId,validateCode());
                   }
                }

            }
        });

        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_dialog.show();
                String email = emailTextView.getText().toString();
                String name = profileName.getText().toString();
                String middleName = profileMiddleName.getText().toString();
                String surname = profileSurname.getText().toString();
                String address = profileAddress.getText().toString();
                String altMoNo = profileAlternateMoNo.getText().toString();

                if(name.isEmpty()){
                    profileName.setError("Enter name");return;
                }
                if(middleName.isEmpty()){
                    profileMiddleName.setError("Enter name");return;
                }
                if(surname.isEmpty()){
                    profileSurname.setError("Enter name");return;
                }
                if(address.isEmpty()){
                    profileAddress.setError("Enter name");return;
                }
                if(altMoNo.isEmpty()){
                    profileAlternateMoNo.setError("Enter name");return;
                }
                if(email.isEmpty()){
                    email = "NA";
                }
                Customer customer = new Customer();
                customer.setName(name);
                customer.setMiddleName(middleName);
                customer.setSurname(surname);
                customer.setAddress(address);
                customer.setAltMoNo(altMoNo);
                customer.setEmail(email);

                GOApplication.databaseReference.child("CUSTOMERS")
                        .child(customerID)
                        .setValue(customer)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getActivity(),"Profile saved successfully",Toast.LENGTH_SHORT).show();
                                    goToHomePage(customerID);
                                }else {
                                    Toast.makeText(getActivity(),"Profile saving failed !",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

               /* FirebaseAuth mAuth = FirebaseAuth.getInstance();
                AuthCredential credential = EmailAuthProvider.getCredential(email,password);

                mAuth.getCurrentUser().linkWithCredential(credential)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d("AUTH", "linkWithCredential:success");

                                } else {
                                    Log.w("AUTH", "linkWithCredential:failure", task.getException());

                                }
                            }
                        });*/
            }
        });
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.

                mVerificationInProgress = false;
                progress_dialog.dismiss();
                showAutoRetrievedOTPDialog(credential);

               // signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.

                mVerificationInProgress = false;


                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded

                }


            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.

                sms_charge_disclaimer.setVisibility(View.GONE);
                signin_image.setImageResource(R.drawable.sign_in_enter_code);
                monoLayout.setVisibility(View.GONE);
                otpLayout.setVisibility(View.VISIBLE);
                info.setText("Enter 4 digit OTP received on phone : "+enteredMoNo);
                info.setTextColor(getActivity().getResources().getColor(R.color.blue));
                generateOTP.setText("VERIFY");
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                progress_dialog.dismiss();

            }
        };
        // [END phone_auth_callbacks]

        return view;
    }

    private void showAutoRetrievedOTPDialog(PhoneAuthCredential credential) {
        final Dialog dialog;

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.otp_dialog_layout);

        final TextView otp = (dialog).findViewById(R.id.otp_dialog_code);
        final TextView cancel = (dialog).findViewById(R.id.otp_dialog_cancel);
        final Button approve = (dialog).findViewById(R.id.otp_dialog_approve);

        otp.setText(credential.getSmsCode());
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_dialog.show();
                signInWithPhoneAuthCredential(credential);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        Objects.requireNonNull(dialog.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
    private Dialog customProgressDialog(){
        final Dialog dialog;

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        Objects.requireNonNull(dialog.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    private String validateCode() {
        String o1 = otp1.getText().toString();
        String o2 = otp2.getText().toString();
        String o3 = otp3.getText().toString();
        String o4 = otp4.getText().toString();
        String o5 = otp5.getText().toString();
        String o6 = otp6.getText().toString();

        if(o1.isEmpty()||o2.isEmpty()||o3.isEmpty()||o4.isEmpty()||o5.isEmpty()||o6.isEmpty()){
            Toast.makeText(getActivity(),"Enter Valid 6 digitcode",Toast.LENGTH_SHORT).show();
            return "INVALID";
        }else{
            return o1+o2+o3+o4+o5+o6;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (mVerificationInProgress) {
            startPhoneNumberVerification(enteredMoNo);
        }
        // [END_EXCLUDE]
    }
    // [END on_start_check_user]


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       // progressBar.setVisibility(View.GONE);
                        progress_dialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getActivity(),"Sign in successful",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = task.getResult().getUser();
                            getUserProfile(user.getUid());


                        } else {
                            // Sign in failed, display a message and update the UI

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(getActivity(),"Invalid Code !",Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }

    private void getUserProfile(String uid) {
        GOApplication.databaseReference.child("CUSTOMERS")
                .child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progressBar.setVisibility(View.GONE);
                        if(snapshot.exists()){
                            Toast.makeText(getActivity(),"Success fetching User Profile !",Toast.LENGTH_SHORT).show();
                            goToHomePage(uid);
                        }else{
                            customerID = uid;
                            signUpView.setVisibility(View.VISIBLE);
                            topTextView.setText("Mo.no. "+enteredMoNo+" registered.");
                            signInView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),"Error fetching User Profile !",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void goToHomePage(String uid) {
        Intent i = new Intent(getActivity(), Main2Activity.class);
        i.putExtra("CUSTOMER_UID",uid);
        startActivity(i);
        getActivity().finish();
    }



    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(getActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    private void setupListener() {
        otp1.setListener(this);
        otp2.setListener(this);
        otp3.setListener(this);
        otp4.setListener(this);
        otp5.setListener(this);
        otp6.setListener(this);
    }

    @Override
    public void onPaste(String s) {
        if (s != null && s.length() == 4) {
            // Then only paste.

            otp1.setText(s.charAt(0));
            otp2.setText(s.charAt(1));
            otp3.setText(s.charAt(2));
            otp4.setText(s.charAt(3));

            // Set cursor position in last edittext.
            // Unfortunately setText doesn't really handle cursor position in a good way.
            otp4.setSelection(1);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(1);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSubCategorySelected");
        }
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int code);
    }
}
