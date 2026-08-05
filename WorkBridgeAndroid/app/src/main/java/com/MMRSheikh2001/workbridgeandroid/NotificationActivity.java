package com.MMRSheikh2001.workbridgeandroid;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MMRSheikh2001.workbridgeandroid.adapter.NotificationAdapter;
import com.MMRSheikh2001.workbridgeandroid.repository.NotificationRepository;
import com.MMRSheikh2001.workbridgeandroid.response.LoginResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.response.NotificationResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView rvNotifications;
    private ImageButton btnBack;
    private ImageButton btnMarkAllRead;
    private ImageButton btnDeleteAll;

    private NotificationAdapter adapter;

    private final List<NotificationResponseDTO> notificationList =
            new ArrayList<>();

    private NotificationRepository repository;
    private SessionManager sessionManager;

    private Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        init();

        loadNotifications();

        btnBack.setOnClickListener(v -> finish());

        btnMarkAllRead.setOnClickListener(v -> markAllRead());

        btnDeleteAll.setOnClickListener(v -> deleteAll());
    }

    private void init() {

        rvNotifications = findViewById(R.id.rvNotifications);

        btnBack = findViewById(R.id.btnBack);
        btnMarkAllRead = findViewById(R.id.btnMarkAllRead);
        btnDeleteAll = findViewById(R.id.btnDeleteAll);

        repository = new NotificationRepository(this);

        sessionManager = new SessionManager(this);

        LoginResponseDTO user = sessionManager.getUser();

        userId = user.getUserId();

        adapter = new NotificationAdapter(
                this,
                notificationList
        );

        rvNotifications.setLayoutManager(
                new LinearLayoutManager(this));

        rvNotifications.setAdapter(adapter);
    }

    private void loadNotifications() {

        repository.getUserNotifications(
                userId,
                new Callback<List<NotificationResponseDTO>>() {

                    @Override
                    public void onResponse(
                            Call<List<NotificationResponseDTO>> call,
                            Response<List<NotificationResponseDTO>> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            notificationList.clear();

                            notificationList.addAll(response.body());

                            adapter.notifyDataSetChanged();

                        } else {

                            Toast.makeText(
                                    NotificationActivity.this,
                                    "Failed to load notifications",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<NotificationResponseDTO>> call,
                            Throwable t) {

                        Toast.makeText(
                                NotificationActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

    private void markAllRead() {

        repository.markAllNotificationsAsRead(
                userId,
                new Callback<Void>() {

                    @Override
                    public void onResponse(
                            Call<Void> call,
                            Response<Void> response) {

                        if (response.isSuccessful()) {

                            Toast.makeText(
                                    NotificationActivity.this,
                                    "All notifications marked as read",
                                    Toast.LENGTH_SHORT
                            ).show();

                            loadNotifications();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<Void> call,
                            Throwable t) {

                        Toast.makeText(
                                NotificationActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

    private void deleteAll() {

        repository.deleteAllNotifications(
                userId,
                new Callback<Void>() {

                    @Override
                    public void onResponse(
                            Call<Void> call,
                            Response<Void> response) {

                        if (response.isSuccessful()) {

                            Toast.makeText(
                                    NotificationActivity.this,
                                    "Notifications cleared",
                                    Toast.LENGTH_SHORT
                            ).show();

                            notificationList.clear();

                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<Void> call,
                            Throwable t) {

                        Toast.makeText(
                                NotificationActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }
}