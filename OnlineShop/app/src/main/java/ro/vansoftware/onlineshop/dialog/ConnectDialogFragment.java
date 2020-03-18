package ro.vansoftware.onlineshop.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ro.vansoftware.onlineshop.R;

public class ConnectDialogFragment extends DialogFragment {

    public interface ConnectDialogListener {
        public void onDialogPositiveClick(String username, String password);
        public void onDialogNegativeClick();
    }

    private ConnectDialogListener connectDialogListener;

    public void onAttach(Context context) {
        super.onAttach(context);
        connectDialogListener = (ConnectDialogListener) context;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_connect, null);

        builder.setView(view)
                .setPositiveButton(R.string.words_connect, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String username = ((EditText) (view.findViewById(R.id.username))).getText().toString();
                        String password = ((EditText) (view.findViewById(R.id.password))).getText().toString();

                        if(username.length() < 3 || password.length() < 5)
                            Toast.makeText(getActivity(),R.string.warning_credentials, Toast.LENGTH_SHORT).show();
                        else {
                            connectDialogListener.onDialogPositiveClick(username, password);
                            dialog.cancel();
                        }
                    }
                })
                .setNegativeButton(R.string.words_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
}


