package com.example.memorypalaceapp.view.history;
import static android.app.Activity.RESULT_OK;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.memorypalaceapp.R;
import com.example.memorypalaceapp.databinding.FragmentViewHistoryItemsBinding;
import com.example.memorypalaceapp.databinding.HistoryListItemBinding;
import com.example.memorypalaceapp.model.HistoryItems;
import com.example.memorypalaceapp.viewmodel.RoomsViewModel;
import com.example.memorypalaceapp.viewmodel.SharedViewModel;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ViewHistoryItemsFragment extends Fragment implements ItemClickListener {
    private SharedViewModel sharedViewModel;
    private HistoryItems deletedItem;
    private int position;
    private int currentImageUpdatePosition;
    private HistoryListItemBinding historyListItemBinding;
    private HistoryItems historyItems;
    private ActivityResultLauncher<Intent> launcher1;
    private HistoryItemsButtonClickHandlers historyItemsButtonClickHandlers;
    private FragmentViewHistoryItemsBinding fragmentViewHistoryItemsBinding;
    private RecyclerViewAdapterHistoryItems recyclerViewAdapterHistoryItems;
    private RecyclerView recyclerView;
    private ArrayList<HistoryItems> historyItemsArrayList;
    private RoomsViewModel roomsViewModel;

    public ViewHistoryItemsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentViewHistoryItemsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_history_items
                , container, false);
        View rootView = fragmentViewHistoryItemsBinding.getRoot();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Recycler View
        recyclerView = fragmentViewHistoryItemsBinding.recyclerView;
        // Initialize the adapter and set it to RecyclerView
        recyclerViewAdapterHistoryItems = new RecyclerViewAdapterHistoryItems();
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        historyItems = new HistoryItems();
        // historyListItemBinding.setHistoryitems(historyItems);
        //calling the setItemCLickListener method of Recycler View Adapter.
        launcher1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
            if (result.getResultCode() == RESULT_OK) {
                Uri uri = result.getData().getData();
                if (uri != null) {
                    updateImageUri(uri);
                }
                // Use the uri to load the image
            } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                // Use ImagePicker.Companion.getError(result.getData()) to show an error
                Toast.makeText(requireContext(), "Error picking image: " + ImagePicker.Companion.getError(result.getData()), Toast.LENGTH_SHORT
                ).show();
            }
        });
        historyItemsButtonClickHandlers = new HistoryItemsButtonClickHandlers(launcher1);
        //Initialize the arrayList
        historyItemsArrayList = new ArrayList<>();
        //Initialize the View Model
        roomsViewModel = new ViewModelProvider(requireActivity()).get(RoomsViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        // Loading the Data from ROOM DB
        roomsViewModel.getAllHistoryItems().observe(getViewLifecycleOwner(), new Observer<List<HistoryItems>>() {
            //Clears the content of arraylist, before showing the full data
            @Override
            public void onChanged(List<HistoryItems> historyItems) {
                historyItemsArrayList.clear();
                if (historyItems.isEmpty()) {
                    fragmentViewHistoryItemsBinding.emptyView.setVisibility(View.VISIBLE);
                } else {
                    fragmentViewHistoryItemsBinding.emptyView.setVisibility(View.GONE);
                    historyItemsArrayList.addAll(historyItems);
                    recyclerViewAdapterHistoryItems.notifyDataSetChanged();
                }
//                for(HistoryItems hi:historyItems){
//
//                    Log.v("TAGYS",""+hi.getName());
//                    historyItemsArrayList.add(hi);
//                }
//                recyclerViewAdapter.notifyDataSetChanged();


//                AppCompatActivity activity = (AppCompatActivity) requireActivity();
//                if (activity != null) {
//                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
//                    ViewNames viewNamesFragment = (ViewNames) fragmentManager.findFragmentByTag("ViewNames"); // Assuming ViewNames has a tag
//                    if (viewNamesFragment != null) {
//                        viewNamesFragment.notifyItemDeleted(deletedItem); // Call method
//                    }
//                }
            }
        });
        recyclerViewAdapterHistoryItems.setHistoryItems(historyItemsArrayList);
        recyclerView.setAdapter(recyclerViewAdapterHistoryItems);
        recyclerViewAdapterHistoryItems.setItemClickListener(this);
        // Adding Swipe to delete functionality
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
            {
                position = viewHolder.getAbsoluteAdapterPosition();
                //What will happen if we swipe left
                //historyItemsArrayList.get(position) retrieves
                // the actual HistoryItems object from the list
                // at the specified index.
                deletedItem = historyItemsArrayList.get(position);
                roomsViewModel.deleteHistoryItems(deletedItem);
                // Notify ViewNames fragment of the deletion
                sharedViewModel.setDeletedItem(deletedItem);
//                Toast.makeText(getContext(), "Items deleted", Toast.LENGTH_SHORT).show();
                //Added SnackBar, if the items deleted and added functionality for undo.
                Snackbar.make(view, "Item deleted", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                roomsViewModel.insertHistoryItems(deletedItem);
                                historyItemsArrayList.add(position, deletedItem);
                                recyclerViewAdapterHistoryItems.notifyItemInserted(position);
                            }
                        }).show();
            }
            // Find ViewNames fragment directly from the Activity (assuming it's added directly)
        }).attachToRecyclerView(recyclerView);
    }
    //onClick method of the Interface ItemClickListener
    @Override
    public void onCLick(View v, int position) {
        if (v.getId() == R.id.textViewName) {
            updateName(position);

        } else if (v.getId() == R.id.textViewDescription) {
            updateDescription(position);
        } else if (v.getId() == R.id.imageView) {
            currentImageUpdatePosition = position;
            updateImage();
        } else if (v.getId() == R.id.textViewDate) {
            int currentPos = historyItemsArrayList.get(position).getId();
            String currentDate = historyItemsArrayList.get(position).getDate();
            updateDate(v, currentPos, currentDate);
        }
    }
    private void updateDate(View view, int currentpos, String currentDate) {
        Context context = view.getContext();
        // Parse the currentDate string into year, month, and day components
        String[] parts = currentDate.split("/");
        int year = Integer.parseInt(parts[2]);
        int month = Integer.parseInt(parts[1]) - 1; // Month index starts from 0, so subtract 1
        int day = Integer.parseInt(parts[0]);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String formattedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                roomsViewModel.updateDate(formattedDate, currentpos);
            }
        }, year, month, day);
        datePickerDialog.show();
    }
    private void updateName(int position) {
        int itemIdName = historyItemsArrayList.get(position).getId();
        // sharedViewModel.setPos(itemIdName);
        Log.v("TAGYS", "" + itemIdName);
        String currentName = historyItemsArrayList.get(position).getName();
        //Show the alert dialog for updating the name
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Enter new name");
        // Set up the input for name with old name or current name
        final EditText input = new EditText(requireContext());
        input.setText(currentName); // Set the current name as the default text
        builder.setView(input);
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newName = input.getText().toString();
                Log.v("TAGYS", "" + itemIdName);
                // Update the name using ViewModel
                roomsViewModel.updateName(newName, itemIdName);
                //sharedViewModel.setupdatedName(newName);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
    private void updateDescription(int position) {
        String currentDesc = historyItemsArrayList.get(position).getDescription();
        int itemIdDesc = historyItemsArrayList.get(position).getId();
        Log.d("TAGYS", "Id is" + itemIdDesc);
        //Show the alert dialog for updating the description
        AlertDialog.Builder builder1 = new AlertDialog.Builder(requireContext());
        builder1.setTitle("Enter new Description");
        // Set up the input for name with old name or current name
        final EditText input1 = new EditText(requireContext());
        input1.setText(currentDesc); // Set the current name as the default text
        builder1.setView(input1);
        //Set Up ok and negative button
        builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newDesc = input1.getText().toString();
                //Update the desc, using View Model
                roomsViewModel.updateDesc(newDesc, itemIdDesc);
            }
        });
        builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder1.show();
    }
    private void updateImage() {
        historyItemsButtonClickHandlers.onImageButtonClickInViewItems(getView());
        //Toast.makeText(getContext(),"clicked iamgeview",Toast.LENGTH_SHORT).show();
    }
    //    private void loadImageIntoImageView(Uri uri) {
//        Glide.with(requireContext())
//                .load(uri)
//                .apply(new RequestOptions()
//                        .override(1000,1000)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .centerCrop())
//                .into(historyListItemBinding.imageView);
//    }
    private void updateImageUri(Uri uri) {
        String url = uri.toString();
        int id = historyItemsArrayList.get(currentImageUpdatePosition).getId();
        // Update the URL in the array list
        historyItemsArrayList.get(currentImageUpdatePosition).setImageUrl(url);
        recyclerViewAdapterHistoryItems.notifyItemChanged(currentImageUpdatePosition);
        // Update the URL in the ViewModel
        roomsViewModel.UpdateImageUrl(url, id);
        // Load the new image into the ImageView
        //loadImageIntoImageView(uri);
    }
//   We are accessing this method from XMl, fragment_add_history_items and from fragment_view_history_items.
    @BindingAdapter({"loadimage"})
    public static void loadImage(ImageView imageView, String url) {
//                Picasso.get()
//                        .load(placeholderResId)
//                        .into(imageView);
//        } else {
            Glide.with(imageView)
                    .load(url)
                  .into(imageView);
  //              Picasso.get()
                       //.load(url)
                   // .into(imageView);
//
    }
}
       //public int sendPosition()
//       {
//          return position;
//       }