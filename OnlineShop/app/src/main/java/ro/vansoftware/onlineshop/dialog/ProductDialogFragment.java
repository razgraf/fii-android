package ro.vansoftware.onlineshop.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import ro.vansoftware.onlineshop.R;

public class ProductDialogFragment extends DialogFragment {

    public interface ProductDialogListener {
        public void PD_onDialogPositiveClick(String name, String description);
        public void PD_onDialogNegativeClick();
    }

    private ProductDialogListener productDialogListener;

    public void onAttach(Context context) {
        super.onAttach(context);
        productDialogListener = (ProductDialogListener) context;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_product, null);

        builder.setView(view)
                .setPositiveButton(R.string.words_create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = ((EditText) (view.findViewById(R.id.name))).getText().toString();
                        String description = ((EditText) (view.findViewById(R.id.description))).getText().toString();

                        if(title.length() < 3 || description.length() < 3)
                            Toast.makeText(getActivity(),R.string.warning_short, Toast.LENGTH_SHORT).show();
                        else {
                            productDialogListener.PD_onDialogPositiveClick(title, description);
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


