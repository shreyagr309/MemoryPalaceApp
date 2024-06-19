package com.example.memorypalaceapp.view.history;
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
    HistoryItemsButtonClickHandlers historyItemsButtonClickHandlers1;
    private String url;
    private SharedViewModel sharedViewModel;
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
                    sharedViewModel.setImageUrl(url);
                }
                // Use the uri to load the image
            } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                // Use ImagePicker.Companion.getError(result.getData()) to show an error
                Toast.makeText(requireContext(),"Error picking image: " + ImagePicker.Companion.getError(result.getData()),Toast.LENGTH_SHORT
                ).show();
            }
        });
        //context = requireActivity();//Get the Host, that is Activity
        roomsViewModel = new ViewModelProvider(requireActivity()).get(RoomsViewModel.class);
        sharedViewModel=new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        historyItemsButtonClickHandlers = new HistoryItemsButtonClickHandlers(roomsViewModel, historyItems, launcher,requireContext(),fragmentAddHistoryItemsBinding,sharedViewModel);
        //Link Data binding with classes
        fragmentAddHistoryItemsBinding.setHistoryItems(historyItems);
        fragmentAddHistoryItemsBinding.setHistoryitemclickHandler(historyItemsButtonClickHandlers);
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_add_history_items, container, false);
        //Linked SharedViewModel with Binding
        fragmentAddHistoryItemsBinding.setSharedviewmodel(sharedViewModel);
        // Observe the Changes in the Data, and make sure it survives, if the screen rotates.
        sharedViewModel.getName().observe(getViewLifecycleOwner(), new Observer<String>() {
            //since we have established two way data binding in the layout
            //therefore we are getting the historyItems object and setting the name by using set
            //name method, and since in text of EditText, we have used
            //@{historyItems.name}, so changes will be reflected in the EditText.
            @Override
            public void onChanged(String name)
            {
                if(name!=null)
                    fragmentAddHistoryItemsBinding.getHistoryItems().setName(name);
            }
        });
        sharedViewModel.getDesc().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String desc)
            {
                if(desc!=null)
                    fragmentAddHistoryItemsBinding.getHistoryItems().setDescription(desc);
            }
        });
        sharedViewModel.getImageUrl().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String Url)
            {
                if(Url!=null){
                    fragmentAddHistoryItemsBinding.getHistoryItems().setImageUrl(Url);
                    //Load the image
//                    Glide.with(fragmentAddHistoryItemsBinding.imageView) // Assuming you have a reference to the ImageView
//                            .load(Url)
//                            .into(fragmentAddHistoryItemsBinding.imageView);
                }
            }
        });
        sharedViewModel.getDate().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String date)
            {
                if(date!=null)
                {   // Because if the screen rotates, we need to save the info in the
                    //History Items.
                    //This line updates the date property within the historyItems object directly.
                    // This ensures the historyItems object has the latest
                    // date information before the save button is clicked.
                    fragmentAddHistoryItemsBinding.getHistoryItems().setDate(date);
                    //This line updates the selectedDate property within the
                    // HistoryItemsButtonClickHandlers class.
                    //Because in the text of Date, we have used @{historybuttonclickhandler.selecteddate}
                    // This property updates the UI (the TextView displaying the selected date)
                    // using data binding.
                    fragmentAddHistoryItemsBinding.getHistoryitemclickHandler().setSelectedDate(date);
                    Toast.makeText(requireContext(), "ok", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @BindingAdapter({"loadImage"})
    public static void loadImage(ImageView imageView, String url)
    {
        if (url == null || url.isEmpty()) {
//                int placeholderResId = imageView.getResources().getIdentifier(
//                        "baseline_add_a_photo_24.xml", "drawable", imageView.getContext().getPackageName());
            Glide.with(imageView.getRootView().getContext())
                    .load(R.drawable.baseline_add_a_photo_24) // Default or placeholder image
                    .apply(RequestOptions.circleCropTransform()) // Apply circular crop
                    .into(imageView);
        }
        else {
            Glide.with(imageView)
                    .load(url)
                    .into(imageView);
////                Picasso.get()
////                        .load(url)
////                        .into(imageView);
//
        }

//                Picasso.get()
//                        .load(placeholderResId)
//                        .into(imageView);
//        } else {
//            Glide.with(imageView)
//                    .load(url)
//                    .into(imageView);
////                Picasso.get()
////                        .load(url)
////                        .into(imageView);
//        }
    }
}