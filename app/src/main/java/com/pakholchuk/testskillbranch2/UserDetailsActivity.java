package com.pakholchuk.testskillbranch2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pakholchuk.testskillbranch2.databinding.ActivityUserDetailsBinding;
import com.pakholchuk.testskillbranch2.helper.DBHelper;
import com.pakholchuk.testskillbranch2.network.NetworkService;
import com.pakholchuk.testskillbranch2.pojo.Geo;
import com.pakholchuk.testskillbranch2.pojo.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsActivity extends AppCompatActivity {
    ActivityUserDetailsBinding binding;
    int userId;
    private DBHelper dbHelper;
    private User user;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        executorService = Executors.newFixedThreadPool(1);
        getUserInfo();
        dbHelper = new DBHelper(this);
        binding.btnSaveUser.setOnClickListener(v -> saveUser());
    }

    private void getUserInfo() {

        userId = getIntent().getIntExtra("userId", -1);
        NetworkService.getInstance()
                .getJSONApi()
                .getUserById(userId)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        user = response.body();
                        setUI();
                        setTextViewActions();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error occured while getting data from server!", Toast.LENGTH_LONG).show();
                        t.printStackTrace();
                    }
                });
    }

    private void setUI() {
        String postId = String.valueOf(getIntent().getIntExtra("postId", -1));
        binding.tvUserPostId.setText(postId);
        String subtitle = "contact #" + userId;
        binding.toolbar.setSubtitle(subtitle);
        binding.tvUserName.setText(user.getName());
        binding.tvUserUsername.setText(user.getUsername());
        binding.tvUserEmail.setText(user.getEmail());
        binding.tvUserPhone.setText(user.getPhone().substring(0, 11));
        binding.tvUserWebsite.setText(user.getWebsite());
        binding.tvUserCity.setText(user.getAddress().getCity());
    }

    private void setTextViewActions() {
        binding.tvUserCity.setOnClickListener(v -> {
            Geo geo = user.getAddress().getGeo();
            Uri mapsIntentUri = Uri.parse("geo:" + geo.getLat() + "," + geo.getLng());
            Intent mapsIntent = new Intent(Intent.ACTION_VIEW, mapsIntentUri);
            startActivity(mapsIntent);
        });
        binding.tvUserEmail.setOnClickListener(v -> {
            Uri email = Uri.parse("mailto:" + user.getEmail());
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, email);
            startActivity(emailIntent);
        });

        binding.tvUserWebsite.setOnClickListener(v -> {
            String url = user.getWebsite();
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "https://" + url;
            Uri website = Uri.parse(url);
            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, website);
            websiteIntent.addCategory(Intent.CATEGORY_BROWSABLE);
            startActivity(websiteIntent);
        });
        binding.tvUserPhone.setOnClickListener(v -> {
            Uri phone = Uri.parse("tel:" + user.getPhone().substring(0, 11));
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL, phone);
            startActivity(phoneIntent);
        });
    }

    private void saveUser() {
        ContentValues cv = new ContentValues();
        cv.put("id", user.getId());
        cv.put("name", user.getName());
        cv.put("username", user.getUsername());
        cv.put("email", user.getEmail());
        cv.put("city", user.getAddress().getCity());
        cv.put("lat", user.getAddress().getGeo().getLat());
        cv.put("lng", user.getAddress().getGeo().getLng());
        cv.put("phone", user.getPhone());
        cv.put("website", user.getWebsite());
        executorService.submit(() -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert("USERS", null, cv);
        });
    }
}
