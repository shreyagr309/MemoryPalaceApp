package com.example.memorypalaceapp.ui.history;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;

import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.activity.result.ActivityResultLauncher;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.memorypalaceapp.BR;
import com.example.memorypalaceapp.R;
import com.example.memorypalaceapp.databinding.FragmentAddHistoryItemsBinding;
import com.example.memorypalaceapp.databinding.HistoryListItemBinding;
import com.example.memorypalaceapp.model.HistoryItems;
import com.example.memorypalaceapp.ui.MainActivity;
import com.example.memorypalaceapp.viewmodel.RoomsViewModel;
import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
public class HistoryItemsButtonClickHandlers extends BaseObservable {
    private ActivityResultLauncher<Intent> launcher;
    private ActivityResultLauncher<Intent>launcher1;
    private  RecyclerViewAdapter recyclerViewAdapter;
    private Context context;
    private FragmentAddHistoryItemsBinding fragmentAddHistoryItemsBinding;
    private String selectedDate;
    private RoomsViewModel historyItemsViewModel;
    private HistoryItems historyItems;

    public HistoryItemsButtonClickHandlers(RoomsViewModel historyItemsViewModel, HistoryItems historyItems, ActivityResultLauncher<Intent> launcher, Context context, FragmentAddHistoryItemsBinding fragmentAddHistoryItemsBinding) {
        this.historyItemsViewModel = historyItemsViewModel;
        this.historyItems = historyItems;
        this.context = context;
        this.launcher = launcher;
        this.fragmentAddHistoryItemsBinding=fragmentAddHistoryItemsBinding;

    }
    public HistoryItemsButtonClickHandlers(ActivityResultLauncher<Intent>launcher1)
    {
        this.launcher1=launcher1;

    }

    @Bindable
    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
        notifyPropertyChanged(BR.selectedDate);
    }

    public void onImageButtonClick(View view) {
        ImagePicker.Companion.with((Activity) view.getContext())
                .crop()
                //.cropOval()
                .cropFreeStyle()
                .maxResultSize(512, 512, true)
                .provider(ImageProvider.BOTH) //Or bothCameraGallery()
                .createIntentFromDialog((Function1) new Function1() {
                    public Object invoke(Object var1) {
                        this.invoke((Intent) var1);
                        return Unit.INSTANCE;
                    }

                    public final void invoke(@NotNull Intent it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        launcher.launch(it);
                    }
                });


    }

    public void onAudioButtonClick(View view) {

    }

    public void OnSaveButtonClicked(View view) {
        if (historyItems.getName() == null || historyItems.getDescription() == null ||historyItems.getImageUrl()==null
                ||historyItems.getDate()==null) {
            Toast.makeText(context, "Fields can not be empty", Toast.LENGTH_SHORT).show();
        } else {
            historyItems.setTimeStamp(new Date().getTime()); // Set the current timestamp before inserting
            HistoryItems addhistoryItems = new HistoryItems(historyItems.getDescription(), historyItems.getName()
                    , historyItems.getImageUrl(), historyItems.getAudioUrl(), historyItems.getDate(), historyItems.getTimeStamp());
            historyItemsViewModel.insertHistoryItems(addhistoryItems);
            // Use contextActions for toast
            // Intent intent = new Intent(view.getContext(),AddHistoryItemsFragment.class); // No need to specify the target activity class here
            //contextActions.startactivity(intent); // Use contextActions for starting activity
            // (potentially navigate back), calling the method of Fragment
            //Because we are passing this when creating the object of ClickHandler.
            // Navigate back to the host activity
            //getParentFragmentManager().popBackStack();
            Toast.makeText(context, "Items Inserted", Toast.LENGTH_SHORT).show();
            // fragment.onDestroy();
            //fragment.onDestroy();
            reset();
        }
    }
        public void onDateButtonClicked (View view)
        {
            Context context = view.getContext();
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH)-1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String formattedDate = dayOfMonth + "/" + (month+1) + "/" + year;
                    setSelectedDate(formattedDate);
                    historyItems.setDate(formattedDate);
                }
            }, year, month, day);
            datePickerDialog.show();
        }

        private void reset()
        {
            // Reset the properties of the historyItems object
            historyItems.setName(null);
            historyItems.setDescription(null);
            historyItems.setImageUrl(null);
            historyItems.setAudioUrl(null);
            historyItems.setDate(null);
            historyItems.setTimeStamp(0);


            fragmentAddHistoryItemsBinding.edtName.setText(null);
            fragmentAddHistoryItemsBinding.edtDescription.setText(null);
            fragmentAddHistoryItemsBinding.imageView.setImageResource(R.drawable.baseline_add_a_photo_24 );
            fragmentAddHistoryItemsBinding.tvSelectedDate.setText(null);

            // Notify the binding about the property changes
            notifyPropertyChanged(BR.historyItems);
        }


    public void onImageButtonClickInViewItems(View view) {
        ImagePicker.Companion.with((Activity) view.getContext())
                .crop()
                //.cropOval()
                .cropFreeStyle()
                .maxResultSize(512, 512, true)
                .provider(ImageProvider.BOTH) //Or bothCameraGallery()
                .createIntentFromDialog((Function1) new Function1() {
                    public Object invoke(Object var1) {
                        this.invoke((Intent) var1);
                        return Unit.INSTANCE;
                    }

                    public final void invoke(@NotNull Intent it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        launcher1.launch(it);
                    }
                });


    }

}