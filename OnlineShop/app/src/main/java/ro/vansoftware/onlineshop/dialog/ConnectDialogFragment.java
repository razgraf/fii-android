package ro.vansoftware.onlineshop.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ro.vansoftware.onlineshop.R;

public class ConnectDialogFragment extends DialogFragment {
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
                        Log.e("Connect Dialog","Yes");
                        String username = ((EditText) (view.findViewById(R.id.username))).getText().toString();
                        String password = ((EditText) (view.findViewById(R.id.password))).getText().toString();

                        if(username.length() < 3 || password.length() < 5)
                            Toast.makeText(getActivity(),R.string.warning_credentials, Toast.LENGTH_SHORT).show();

                        dialog.cancel();

                    }
                })
                .setNegativeButton(R.string.words_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("Connect Dialog","No");
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
}
