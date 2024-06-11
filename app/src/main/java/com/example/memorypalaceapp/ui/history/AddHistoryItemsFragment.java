package com.example.memorypalaceapp.ui.history;
import static android.app.Activity.RESULT_OK;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.memorypalaceapp.R;
import com.example.memorypalaceapp.databinding.FragmentAddHistoryItemsBinding;
import com.example.memorypalaceapp.model.HistoryItems;
import com.example.memorypalaceapp.viewmodel.RoomsViewModel;
import com.github.drjacky.imagepicker.ImagePicker;
public class AddHistoryItemsFragment extends Fragment
{
    private RoomsViewModel roomsViewModel;
    private HistoryItemsButtonClickHandlers historyItemsButtonClickHandlers;
     private FragmentAddHistoryItemsBinding fragmentAddHistoryItemsBinding;
    private HistoryItems historyItems;
    private ActivityResultLauncher<Intent> launcher;
    public AddHistoryItemsFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAddHistoryItemsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_history_items,
                container, false);

        View rootView = fragmentAddHistoryItemsBinding.getRoot();
        return rootView;
    }
//    public void loadImageIntoImageView(Uri uri)
//    {
//            Glide.with(fragmentAddHistoryItemsBinding.getRoot().getContext())
//                    .load(uri)
//                    //.apply(new RequestOptions().circleCrop())
//                    //.apply(new RequestOptions().sizeMultiplier(1.0f))
//                    .override(1000, 1000)
//                    .into(fragmentAddHistoryItemsBinding.imageView);
//
//    }
//    public void navigateBack() {
//        requireActivity().getSupportFragmentManager().popBackStackImmediate();
//    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        historyItems = new HistoryItems();
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
            if (result.getResultCode() == RESULT_OK) {
                Uri uri = result.getData().getData();
                if (uri != null)
                {
                    historyItems.setImageUrl(uri.toString());

                    //loadImageIntoImageView(uri); // Call method from the click handler
                }
                // Use the uri to load the image
            } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                // Use ImagePicker.Companion.getError(result.getData()) to show an error
                Toast.makeText(requireContext(),"Error picking image: " + ImagePicker.Companion.getError(result.getData()),Toast.LENGTH_SHORT
                ).show();
            }
        });

        //context = requireActivity();//Get the Host, that is Activity
        roomsViewModel = new ViewModelProvider(this).get(RoomsViewModel.class);
        historyItemsButtonClickHandlers = new HistoryItemsButtonClickHandlers(roomsViewModel, historyItems, launcher,requireContext(),fragmentAddHistoryItemsBinding);
        //Link Data binding with classes
        fragmentAddHistoryItemsBinding.setHistoryItems(historyItems);
        fragmentAddHistoryItemsBinding.setHistoryitemclickHandler(historyItemsButtonClickHandlers);
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_add_history_items, container, false);
    }
}