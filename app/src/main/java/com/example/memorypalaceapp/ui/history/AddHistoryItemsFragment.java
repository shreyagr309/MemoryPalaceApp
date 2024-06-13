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
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.memorypalaceapp.R;
import com.example.memorypalaceapp.databinding.FragmentAddHistoryItemsBinding;
import com.example.memorypalaceapp.model.HistoryItems;
import com.example.memorypalaceapp.viewmodel.RoomsViewModel;
import com.example.memorypalaceapp.viewmodel.SharedViewModel;
import com.github.drjacky.imagepicker.ImagePicker;
public class AddHistoryItemsFragment extends Fragment
{
    private String url;
    SharedViewModel sharedViewModel;
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
            if (result.getResultCode() == RESULT_OK)
            {
                Uri uri = result.getData().getData();
                if (uri != null)
                {
                    url=uri.toString();
                    historyItems.setImageUrl(url);

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
        sharedViewModel=new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        historyItemsButtonClickHandlers = new HistoryItemsButtonClickHandlers(roomsViewModel, historyItems, launcher,requireContext(),fragmentAddHistoryItemsBinding);
        //Link Data binding with classes
        fragmentAddHistoryItemsBinding.setHistoryItems(historyItems);
        fragmentAddHistoryItemsBinding.setHistoryitemclickHandler(historyItemsButtonClickHandlers);
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_add_history_items, container, false);
        //Linked SharedViewModel with Binding
        fragmentAddHistoryItemsBinding.setSharedviewmodel(sharedViewModel);

        // Observe the Changes in the Data.

        sharedViewModel.getName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String name)
            {

                if(name!=null)
                fragmentAddHistoryItemsBinding.edtName.setText(name);

            }
        });

        sharedViewModel.getName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String name)
            {

                if(name!=null)
                    fragmentAddHistoryItemsBinding.edtName.setText(name);

            }
        });

        sharedViewModel.getName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String name)
            {

                if(name!=null)
                    fragmentAddHistoryItemsBinding.edtName.setText(name);

            }
        });

        sharedViewModel.getDesc().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String desc)
            {

                if(desc!=null)
                    fragmentAddHistoryItemsBinding.edtDescription.setText(desc);

            }
        });

        sharedViewModel.getImageUrl().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String Url)
            {
                Url=url;

                if(url!=null){
                    //Load the image

                    loadImage(fragmentAddHistoryItemsBinding.imageView,Url);

                }


            }
        });

        sharedViewModel.getDate().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String date)
            {

                if(date!=null){

                    fragmentAddHistoryItemsBinding.tvSelectedDate.setText(date);

                }
            }
        });
    }

    @BindingAdapter({"loadImage"})
    public static void loadImage(ImageView imageView, String url) {
        if (url == null || url.isEmpty()) {
//                int placeholderResId = imageView.getResources().getIdentifier(
//                        "baseline_add_a_photo_24.xml", "drawable", imageView.getContext().getPackageName());
            Glide.with(imageView.getRootView().getContext())
                    .load(R.drawable.baseline_add_a_photo_24) // Default or placeholder image
                    .apply(RequestOptions.circleCropTransform()) // Apply circular crop
                    .into(imageView);

//                Picasso.get()
//                        .load(placeholderResId)
//                        .into(imageView);
        } else {
            Glide.with(imageView)
                    .load(url)
                    .into(imageView);
//                Picasso.get()
//                        .load(url)
//                        .into(imageView);
        }
    }

}