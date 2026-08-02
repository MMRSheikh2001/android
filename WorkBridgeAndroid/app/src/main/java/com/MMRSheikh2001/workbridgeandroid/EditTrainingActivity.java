package com.MMRSheikh2001.workbridgeandroid;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.MMRSheikh2001.workbridgeandroid.cvinformations.repository.TrainingRepository;
import com.MMRSheikh2001.workbridgeandroid.cvinformations.request.TrainingRequestDTO;
import com.MMRSheikh2001.workbridgeandroid.cvinformations.response.TrainingResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.enums.TrainingType;
import com.MMRSheikh2001.workbridgeandroid.response.LoginResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.session.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.File;
import java.time.LocalDate;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTrainingActivity extends AppCompatActivity {


    private Toolbar toolbar;

    private TextInputEditText etTrainingName;
    private TextInputEditText etInstitution;
    private TextInputEditText etDescription;
    private AutoCompleteTextView actTrainingType;
    private TextInputEditText etStartDate;
    private TextInputEditText etEndDate;
    private TextInputEditText etDuration;
    private TextInputEditText etCertificateId;
    private TextInputEditText etCertificateVerificationUrl;

    private TextView tvSelectedFile;

    private MaterialButton btnChooseCertificate;
    private MaterialButton btnCancel;
    private MaterialButton btnSave;

    private ProgressBar progressBar;

    private TrainingRepository repository;
    private SessionManager sessionManager;
    private Gson gson;

    private Uri selectedFileUri;

    private Long trainingId = null;
    private Long profileId;

    private final ActivityResultLauncher<String> filePickerLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {

                        if (uri != null) {

                            selectedFileUri = uri;
                            tvSelectedFile.setText(uri.getLastPathSegment());

                        }

                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_training);


        init();

        LoginResponseDTO user = sessionManager.getUser();

        if (user == null) {
            finish();
            return;
        }

        profileId = user.getProfileId();

        trainingId = getIntent().getLongExtra("trainingId", -1);

        if (trainingId == -1) {
            trainingId = null;
        }

        setupDropdown();

        toolbar.setNavigationOnClickListener(v -> finish());

        etStartDate.setOnClickListener(v -> showDatePicker(etStartDate));
        etEndDate.setOnClickListener(v -> showDatePicker(etEndDate));

        btnChooseCertificate.setOnClickListener(v ->
                filePickerLauncher.launch("*/*"));

        btnCancel.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> saveTraining());

        if (trainingId != null) {
            loadTraining();
        }

    }


    private void init() {

        repository = new TrainingRepository(this);
        sessionManager = new SessionManager(this);
        gson = new Gson();

        toolbar = findViewById(R.id.toolbar);

        etTrainingName = findViewById(R.id.etTrainingName);
        etInstitution = findViewById(R.id.etInstitution);
        etDescription = findViewById(R.id.etDescription);
        actTrainingType = findViewById(R.id.actTrainingType);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        etDuration = findViewById(R.id.etDuration);
        etCertificateId = findViewById(R.id.etCertificateId);
        etCertificateVerificationUrl =
                findViewById(R.id.etCertificateVerificationUrl);

        tvSelectedFile = findViewById(R.id.tvSelectedFile);

        btnChooseCertificate =
                findViewById(R.id.btnChooseCertificate);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);

        progressBar = findViewById(R.id.progressBar);
    }

    private void setupDropdown() {

        String[] types =
                new String[TrainingType.values().length];

        for (int i = 0; i < TrainingType.values().length; i++) {
            types[i] = TrainingType.values()[i].name();
        }

        actTrainingType.setAdapter(
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        types));
    }

    private void showDatePicker(TextInputEditText editText) {

        Calendar calendar = Calendar.getInstance();

        new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) ->

                        editText.setText(
                                String.format(
                                        "%04d-%02d-%02d",
                                        year,
                                        month + 1,
                                        dayOfMonth)),

                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))

                .show();
    }

    private void loadTraining() {

        progressBar.setVisibility(android.view.View.VISIBLE);

        repository.getTrainingById(
                trainingId,
                new Callback<TrainingResponseDTO>() {

                    @Override
                    public void onResponse(
                            Call<TrainingResponseDTO> call,
                            Response<TrainingResponseDTO> response) {

                        progressBar.setVisibility(
                                android.view.View.GONE);

                        if (response.isSuccessful()
                                && response.body() != null) {

                            TrainingResponseDTO t = response.body();

                            etTrainingName.setText(t.getName());
                            etInstitution.setText(t.getInstitution());
                            etDescription.setText(t.getDescription());

                            if (t.getTrainingType() != null)
                                actTrainingType.setText(
                                        t.getTrainingType().name(),
                                        false);

                            if (t.getStartDate() != null)
                                etStartDate.setText(
                                        t.getStartDate().toString());

                            if (t.getEndDate() != null)
                                etEndDate.setText(
                                        t.getEndDate().toString());

                            etDuration.setText(t.getDuration());
                            etCertificateId.setText(
                                    t.getCertificateId());

                            etCertificateVerificationUrl.setText(
                                    t.getCertificateVerificationUrl());

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<TrainingResponseDTO> call,
                            Throwable t) {

                        progressBar.setVisibility(
                                android.view.View.GONE);

                    }
                });

    }

    private void saveTraining() {

        TrainingRequestDTO dto = new TrainingRequestDTO();

        dto.setName(etTrainingName.getText().toString().trim());
        dto.setInstitution(etInstitution.getText().toString().trim());
        dto.setDescription(etDescription.getText().toString().trim());

        dto.setTrainingType(
                TrainingType.valueOf(
                        actTrainingType.getText().toString()));

        if (!etStartDate.getText().toString().isEmpty())
            dto.setStartDate(
                    LocalDate.parse(
                            etStartDate.getText().toString()));

        if (!etEndDate.getText().toString().isEmpty())
            dto.setEndDate(
                    LocalDate.parse(
                            etEndDate.getText().toString()));

        dto.setDuration(etDuration.getText().toString().trim());
        dto.setCertificateId(
                etCertificateId.getText().toString().trim());

        dto.setCertificateVerificationUrl(
                etCertificateVerificationUrl.getText().toString().trim());

        dto.setUserProfileId(profileId);

        RequestBody requestBody =
                RequestBody.create(
                        gson.toJson(dto),
                        MediaType.parse("application/json"));

        MultipartBody.Part filePart = null;

        if (selectedFileUri != null) {

            File file =
                    new File(selectedFileUri.getPath());

            RequestBody fileBody =
                    RequestBody.create(
                            file,
                            MediaType.parse("*/*"));

            filePart =
                    MultipartBody.Part.createFormData(
                            "file",
                            file.getName(),
                            fileBody);

        }

        progressBar.setVisibility(android.view.View.VISIBLE);

        Callback<TrainingResponseDTO> callback =
                new Callback<TrainingResponseDTO>() {

                    @Override
                    public void onResponse(
                            Call<TrainingResponseDTO> call,
                            Response<TrainingResponseDTO> response) {

                        progressBar.setVisibility(
                                android.view.View.GONE);

                        if (response.isSuccessful()) {

                            Toast.makeText(
                                    EditTrainingActivity.this,
                                    "Saved Successfully",
                                    Toast.LENGTH_SHORT).show();

                            finish();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<TrainingResponseDTO> call,
                            Throwable t) {

                        progressBar.setVisibility(
                                android.view.View.GONE);

                        Toast.makeText(
                                EditTrainingActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();

                    }
                };

        if (trainingId == null) {

            repository.saveTraining(
                    requestBody,
                    filePart,
                    callback);

        } else {

            repository.updateTraining(
                    trainingId,
                    requestBody,
                    filePart,
                    callback);

        }

    }


}