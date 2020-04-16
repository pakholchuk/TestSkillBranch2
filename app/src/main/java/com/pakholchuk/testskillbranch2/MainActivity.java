package com.pakholchuk.testskillbranch2;
/*
        В приложении есть 2 экрана.
         На первом экране присутствуют:
         - Кнопка для сохранения logcat в файл (вверху справа).
         - Картинка (любая, например картошка, но область в которой находится картинка, должна занимать 3/5
        высоты экрана). Для картинки опционально можно сделать любую анимацию.
         - Под картинкой горизонтально прокручиваемый двухстрочный список. Каждый элемент списка - id поста.
          Под id в одну строчку (можно сокращённо) title поста. В каждый момент времени должно отображаться
         не более 6 элементов (2 ряда по 3 элемента).
        - Под прокручиваемым списком должен отображаться точечный индикатор, отображающий текущую позицию списка.
         - При нажатии на любой элемент списка происходит переход на второй экран.
         Список постов приложение должно получать по адресу: http://jsonplaceholder.typicode.com/posts
         На втором экране:
        - В самом верху (в тулбаре) отображается строка "contact #X", где X - id контакта.
         - В верхней левой части отображается id поста, на который было нажато на предыдущий экран.
         - Справа от id поста располагаются имя и никнейм юзера (2 строки).
         - Ниже id поста отображается емейл, веб сайт, номер телефона и город пользователя.
        - Опционально - по нажатию на емейл, веб-сайт и номер телефона должны открыться соответствующие
        приложения на телефоне. При нажатии на город должна открыть карта с текущей координатой, равной той,
         что указана у данного юзера в профайле.
        - В самом низу с левой стороны есть кнопка сохранения инф-ции о юзере в базу данных.
        Детали пользователя приложение получает по адресу: http://jsonplaceholder.typicode.com/users/X , где X - целочисленный Id.
        Photo by Charles Deluvio on Unsplash
*/
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import android.widget.Toast;

import com.pakholchuk.testskillbranch2.adapter.ItemViewClickListener;
import com.pakholchuk.testskillbranch2.adapter.PostAdapter;
import com.pakholchuk.testskillbranch2.databinding.ActivityMainBinding;

import com.pakholchuk.testskillbranch2.network.NetworkService;
import com.pakholchuk.testskillbranch2.pojo.Post;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private PostAdapter postAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ivMain.setMaxHeight((getResources().getDisplayMetrics().heightPixels)*3/5);
        initRecyclerView();
        binding.btnSaveLogcat.setOnClickListener(v -> saveLogCat());

    }

    private void initRecyclerView() {
        layoutManager = new GridLayoutManager(getApplicationContext(), 2, RecyclerView.HORIZONTAL, false);
        ItemViewClickListener itemViewClickListener = (view, post) -> MainActivity.this.launchUserDetailsActivity(post);
        postAdapter = new PostAdapter(itemViewClickListener);
        binding.recycler.setAdapter(postAdapter);
        binding.recycler.setLayoutManager(layoutManager);
        binding.recycler.setHasFixedSize(true);
        getPosts();

    }

    private void launchUserDetailsActivity(Post post) {
        Intent intent = new Intent(getApplicationContext(), UserDetailsActivity.class);
        intent.putExtra("userId", post.getUserId());
        intent.putExtra("postId", post.getId());
        startActivity(intent);
    }

    private void saveLogCat() {
        String filePath = Environment.getExternalStorageDirectory() + "/logcat.txt";
        try {
            Runtime.getRuntime().exec(new String[]{"logcat", "-f", filePath, "MyAppTAG:V", "*:S"});
            Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Problem with log saving", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void getPosts() {
        NetworkService.getInstance()
                .getJSONApi()
                .getAllPosts()
                .enqueue(new Callback<ArrayList<Post>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                        ArrayList<Post> posts = response.body();
                        postAdapter.setPosts(posts);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error occured while getting data from server!", Toast.LENGTH_LONG).show();
                        t.printStackTrace();
                    }
                });

    }
}
