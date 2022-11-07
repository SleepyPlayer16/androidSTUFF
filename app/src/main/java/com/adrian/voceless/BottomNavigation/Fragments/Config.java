package com.adrian.voceless.BottomNavigation.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adrian.voceless.Authentication.Login;
import com.adrian.voceless.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Config extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_config, container, false);

        Button changePassword = view.findViewById(R.id.changePass);
        Button deleteAccount = view.findViewById(R.id.btn_delete);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAlertDialog(1);
            }
        });
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAlertDialog(2);
            }
        });

        return view;
    }

    private void getAlertDialog(int option) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_authentication,null);

        EditText email = dialogView.findViewById(R.id.fieldEmail);
        EditText password = dialogView.findViewById(R.id.FieldPassword);
        EditText newPassword = dialogView.findViewById(R.id.FieldNewPassword);
        Button reauth = dialogView.findViewById(R.id.btnConfirmar);

        if (option == 1){
            newPassword.setVisibility(View.VISIBLE);
        } else {
            newPassword.setVisibility(View.GONE);
        }

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.60);
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setLayout(width, height);
        }
        alertDialog.show();

        reauth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( email.getText().toString().length() < 1 || password.getText().toString().length() < 1 ){
                    Toast.makeText(getContext(), "Faltan campos", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    AuthCredential credential = EmailAuthProvider
                            .getCredential(email.getText().toString(), password.getText().toString());
                    if (user !=null){
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            getOption(option, alertDialog, newPassword.getText().toString());
                                        } else {
                                            alertDialog.dismiss();
                                            Toast.makeText(getContext(), "Error" + password, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });
    }
    private void getOption(int i, AlertDialog alertDialog, String newpass) {
        if (i == 1){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            user.updatePassword(newpass)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                alertDialog.dismiss();
                                Toast.makeText(getContext(), "Contraseña actualizada", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        }else{
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        alertDialog.dismiss();
                        Toast.makeText(getContext(), "Cuenta eliminada", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getContext(), Login.class));
                        getActivity().finish();
                    }else{
                        alertDialog.dismiss();
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


}