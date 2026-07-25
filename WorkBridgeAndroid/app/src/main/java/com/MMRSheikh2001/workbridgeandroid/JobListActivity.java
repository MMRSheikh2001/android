package com.MMRSheikh2001.workbridgeandroid;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.MMRSheikh2001.workbridgeandroid.enums.EmploymentType;
import com.MMRSheikh2001.workbridgeandroid.enums.WorkPlaceType;
import com.MMRSheikh2001.workbridgeandroid.masterdata.repository.CategoryRepository;
import com.MMRSheikh2001.workbridgeandroid.masterdata.repository.CountryRepository;
import com.MMRSheikh2001.workbridgeandroid.masterdata.repository.DistrictRepository;
import com.MMRSheikh2001.workbridgeandroid.masterdata.repository.DivisionRepository;
import com.MMRSheikh2001.workbridgeandroid.masterdata.repository.PoliceStationRepository;
import com.MMRSheikh2001.workbridgeandroid.masterdata.response.CategoryResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.masterdata.response.CountryResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.masterdata.response.DistrictResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.masterdata.response.DivisionResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.masterdata.response.PoliceStationResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.repository.JobRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobListActivity extends AppCompatActivity {

    private TextInputEditText etKeyword;

    private Spinner spCategory;
    private Spinner spCountry;
    private Spinner spDivision;
    private Spinner spDistrict;
    private Spinner spPoliceStation;
    private Spinner spEmploymentType;
    private Spinner spWorkPlaceType;

    private MaterialButton btnSearch;

    private RecyclerView rvJobs;

    private CategoryRepository categoryRepository;
    private CountryRepository countryRepository;
    private DivisionRepository divisionRepository;
    private DistrictRepository districtRepository;
    private PoliceStationRepository policeStationRepository;

    private JobRepository jobRepository;

    private List<CategoryResponseDTO> categoryList;
    private List<CountryResponseDTO> countryList;
    private List<DivisionResponseDTO> divisionList;
    private List<DistrictResponseDTO> districtList;
    private List<PoliceStationResponseDTO> policeStationList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_job_list);
        init();

        loadCategories();

        loadCountries();

        loadEmploymentTypes();

        loadWorkPlaceTypes();

    }

    private void init() {

        etKeyword = findViewById(R.id.etKeyword);

        spCategory = findViewById(R.id.spCategory);
        spCountry = findViewById(R.id.spCountry);
        spDivision = findViewById(R.id.spDivision);
        spDistrict = findViewById(R.id.spDistrict);
        spPoliceStation = findViewById(R.id.spPoliceStation);

        spEmploymentType = findViewById(R.id.spEmploymentType);
        spWorkPlaceType = findViewById(R.id.spWorkPlaceType);

        btnSearch = findViewById(R.id.btnSearch);

        rvJobs = findViewById(R.id.rvJobs);

        categoryRepository = new CategoryRepository(this);
        countryRepository = new CountryRepository(this);
        divisionRepository = new DivisionRepository(this);
        districtRepository = new DistrictRepository(this);
        policeStationRepository = new PoliceStationRepository(this);

        jobRepository = new JobRepository(this);
    }

    private void loadCategories() {
        categoryRepository.getAllCategories(new Callback<List<CategoryResponseDTO>>() {
            @Override
            public void onResponse(Call<List<CategoryResponseDTO>> call, Response<List<CategoryResponseDTO>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    return;
                }
                categoryList = response.body();
                List<String> names = new ArrayList<>();
                names.add("All Categories");
                for (CategoryResponseDTO dto : categoryList) {
                    names.add(dto.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(JobListActivity.this
                        , android.R.layout.simple_spinner_dropdown_item, names);
                spCategory.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CategoryResponseDTO>> call, Throwable t) {
                Toast.makeText(JobListActivity.this, "Failed to load Categories"
                        , Toast.LENGTH_SHORT).show();

//                t.printStackTrace();
//
//                Toast.makeText(JobListActivity.this,
//                        t.getMessage(),
//                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private void loadCountries() {

        countryRepository.getAllCountries(new Callback<List<CountryResponseDTO>>() {
            @Override
            public void onResponse(Call<List<CountryResponseDTO>> call, Response<List<CountryResponseDTO>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    return;
                }
                countryList = response.body();
                List<String> names = new ArrayList<>();
                names.add("All Countries");
                for (CountryResponseDTO dto : countryList) {
                    names.add(dto.getCountryName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(JobListActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, names);
                spCountry.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CountryResponseDTO>> call, Throwable t) {

                Toast.makeText(JobListActivity.this, "Failed to load Countries"
                        , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadEmploymentTypes() {


        List<String> names = new ArrayList<>();

        names.add("All Employment Types");

        for (EmploymentType type : EmploymentType.values()) {
            names.add(type.name().replace("_", " "));
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        names);

        spEmploymentType.setAdapter(adapter);

    }

    private void loadWorkPlaceTypes() {


        List<String> names = new ArrayList<>();

        names.add("All Workplace Types");

        for (WorkPlaceType type : WorkPlaceType.values()) {
            names.add(type.name().replace("_", " "));
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        names);

        spWorkPlaceType.setAdapter(adapter);
    }


}