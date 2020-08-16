package com.reco.view.ui;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.reco.R;
import com.reco.service.model.UserPasswordChangeModel;
import com.reco.service.repository.APIService;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePasswordFragment extends Fragment {
    private APIService apiService;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (savedInstanceState != null) {
            return;
        }

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl( getString( R.string.API_URL ) )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        apiService = mRetrofit.create( APIService.class );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        TextView oldPassword = view.findViewById( R.id.fragment_change_password_current_password );
        TextView newPassword = view.findViewById( R.id.fragment_change_password_new_password );
        TextView repeatPassword = view.findViewById( R.id.fragment_change_password_repeat_password );

        ImageButton mOldPasswordVisibility = view.findViewById( R.id.fragment_change_password_current_password_visibility_button );
        ImageButton mNewPasswordVisibility = view.findViewById( R.id.fragment_change_password_new_password_visibility_button );
        ImageButton mRepeatPasswordVisibility = view.findViewById( R.id.fragment_change_password_repeat_password_visibility_button );

        AppCompatButton changePasswordButton = view.findViewById( R.id.fragment_change_password_change_password_button );
        NavController navController = Navigation.findNavController( view );


        mOldPasswordVisibility.setOnClickListener( view12 -> {
            if (oldPassword.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                oldPassword.setTransformationMethod( HideReturnsTransformationMethod.getInstance() );
                mOldPasswordVisibility.setImageResource( R.drawable.ic_baseline_visibility_off_24 );
            } else {
                oldPassword.setTransformationMethod( PasswordTransformationMethod.getInstance() );
                mOldPasswordVisibility.setImageResource( R.drawable.ic_baseline_remove_red_eye_24 );
            }
        } );

        mNewPasswordVisibility.setOnClickListener( view13 -> {
            if (newPassword.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                newPassword.setTransformationMethod( HideReturnsTransformationMethod.getInstance() );
                mNewPasswordVisibility.setImageResource( R.drawable.ic_baseline_visibility_off_24 );
            } else {
                newPassword.setTransformationMethod( PasswordTransformationMethod.getInstance() );
                mNewPasswordVisibility.setImageResource( R.drawable.ic_baseline_remove_red_eye_24 );
            }
        } );

        mRepeatPasswordVisibility.setOnClickListener( view14 -> {
            if (repeatPassword.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                repeatPassword.setTransformationMethod( HideReturnsTransformationMethod.getInstance() );
                mRepeatPasswordVisibility.setImageResource( R.drawable.ic_baseline_visibility_off_24 );
            } else {
                repeatPassword.setTransformationMethod( PasswordTransformationMethod.getInstance() );
                mRepeatPasswordVisibility.setImageResource( R.drawable.ic_baseline_remove_red_eye_24 );
            }
        } );


        oldPassword.setOnTouchListener( (view15, motionEvent) -> {

            oldPassword.setError( null );
            mOldPasswordVisibility.setVisibility( View.VISIBLE );
            return false;
        } );


        newPassword.setOnTouchListener( (view16, motionEvent) -> {

            newPassword.setError( null );
            mNewPasswordVisibility.setVisibility( View.VISIBLE );
            return false;
        } );


        repeatPassword.setOnTouchListener( (view17, motionEvent) -> {

            repeatPassword.setError( null );
            mRepeatPasswordVisibility.setVisibility( View.VISIBLE );
            return false;
        } );


        changePasswordButton.setOnClickListener( view1 -> {
            String oldPass, newPass, repeatPass;
            oldPass = oldPassword.getText().toString();
            newPass = newPassword.getText().toString();
            repeatPass = repeatPassword.getText().toString();

            if (oldPass.isEmpty()) {
                mOldPasswordVisibility.setVisibility( View.INVISIBLE );
                oldPassword.setError( getResources().getString( R.string.field_required ) );
            } else if (newPass.isEmpty()) {
                mNewPasswordVisibility.setVisibility( View.INVISIBLE );
                newPassword.setError( getResources().getString( R.string.field_required ) );
            } else if (repeatPass.isEmpty()) {
                mRepeatPasswordVisibility.setVisibility( View.INVISIBLE );
                repeatPassword.setError( getResources().getString( R.string.field_required ) );
            } else {
                if (oldPass.equals( newPass )) {
                    Toast.makeText( getContext(), R.string.passwords_cannot_be_same, Toast.LENGTH_SHORT ).show();
                } else if (!newPass.equals( repeatPass )) {
                    mRepeatPasswordVisibility.setVisibility( View.INVISIBLE );
                    repeatPassword.setError( getResources().getString( R.string.passwords_do_not_match ) );
                } else {
                    apiService.changeUserPassword( new UserPasswordChangeModel( oldPass, newPass ) ).enqueue( new Callback<Void>() {
                        @Override
                        public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                            if (response.isSuccessful()) {
                                navController.navigateUp();
                                Toast.makeText( getContext(), R.string.password_changed_successfully, Toast.LENGTH_SHORT ).show();
                            } else {
                                Toast.makeText( getContext(), response.message(), Toast.LENGTH_SHORT ).show();
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                            Toast.makeText( getContext(), t.getMessage(), Toast.LENGTH_SHORT ).show();
                        }
                    } );
                }
            }
        } );


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_change_password, container, false );
    }
}