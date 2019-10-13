package com.group4.togolist.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.group4.togolist.R;
import com.group4.togolist.viewmodel.AddFormViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class AddFormActivity extends AppCompatActivity implements View.OnClickListener {

    private AddFormViewModel addFormViewModel;
    private Button btnAdd , btnCancel ;
    private RadioButton rdnBtnDaily , rdnBtnWeekly , rdnBtnDays , rdnBtnOneDirection , rdnBtnRoundTrip;
    private EditText eTxtTripName , eTxtStartPoint , eTxtEndPoint , eTxtStartDate , eTxtStartTime , eTxtNotes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form);

        addFormViewModel = ViewModelProviders.of(this , new MyViewModelFactory(AddFormActivity.this)).get(AddFormViewModel.class);

        initComponent();

    }

    /**
     *
     */
    private void initComponent() {
        eTxtTripName = findViewById(R.id.editText_nameTrip);
//        eTxtStartPoint = findViewById(R.id.editText_startPoint);
 //       eTxtEndPoint = findViewById(R.id.editText_endPoint);
        eTxtStartDate =findViewById(R.id.editText_startDate);
        eTxtStartTime = findViewById(R.id.editText_startTime);
        eTxtNotes = findViewById(R.id.editText_Note);

        btnAdd = findViewById(R.id.btn_add);
        btnCancel = findViewById(R.id.btn_cancel);

        rdnBtnDaily = findViewById(R.id.radioBtn_daily);
        rdnBtnWeekly = findViewById(R.id.radioBtnweekly);
        rdnBtnDays = findViewById(R.id.radioBtnDays);
        rdnBtnOneDirection = findViewById(R.id.radioBtnOneDirection);
        rdnBtnRoundTrip = findViewById(R.id.radioBtnRoundTrip);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editText_startPoint:
                break;
            case R.id.editText_endPoint:
                break;
            case R.id.editText_startDate:
                break;
            case R.id.editText_startTime:
                break;

            case R.id.radioBtn_daily:
                break;
            case R.id.radioBtnweekly:
                break;
            case R.id.radioBtnDays:
                break;
            case R.id.radioBtnOneDirection:
                break;
            case R.id.radioBtnRoundTrip:
                break;
            case R.id.btn_add:
                break;
            case R.id.btn_cancel:
                break;



        }
    }

    /**
     *
     */
    class MyViewModelFactory implements ViewModelProvider.Factory {
        private Activity mActivity;


        public MyViewModelFactory(Activity activity) {
            mActivity = activity;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new AddFormViewModel(mActivity);
        }
    }
}
