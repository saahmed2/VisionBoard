package com.planning.visionboard;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.planning.visionboard.data.ApplicationDatabase;
import com.planning.visionboard.model.Board;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddBoardFragment extends Fragment {
    private EditText name;
    private EditText description;
    private Button addImageButton;
    private Button saveBoardButton;
    private ImageView imageView;
    private String imageUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_board, container, false);

        name = view.findViewById(R.id.add_board_name);
        description = view.findViewById(R.id.add_board_description);
        addImageButton = view.findViewById(R.id.add_image);
        saveBoardButton = view.findViewById(R.id.save_board);
        imageView = view.findViewById(R.id.board_image);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        saveBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Save our Board
                saveBoard();

                name.setText("");
                description.setText("");
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUrl = processImage();
                Picasso.get()
                        .load(imageUrl)
                        .centerCrop()
                        .fit()
                        .into(imageView);

            }
        });
    }

    private String processImage() {
        int min = 120;
        int max = 290;
        int minId = 50;
        int maxId = 100;

        Random random = new Random();
        int height = min + random.nextInt(max);
        int idRand = minId + random.nextInt(maxId);

        //https://i.picsum.photos/id/237/200/300.jpg
        return "https://i.picsum.photos/id/"+idRand+"/200/"+height+".jpg";
    }

    private void saveBoard() {
         String name = this.name.getText().toString().trim();
         String description = this.description.getText().toString().trim();

         if (TextUtils.isEmpty(name) && TextUtils.isEmpty(description)) {

         }else {
             Board board = new Board(name, description, imageUrl);
             new InsertBoardTask(getContext()).execute(board);
         }
    }

    private static class InsertBoardTask extends AsyncTask<Board, Void, Long> {
        private final WeakReference<Context> weakReference;
        InsertBoardTask(Context context) {
            this.weakReference = new WeakReference<>(context);
        }

        @Override
        protected Long doInBackground(Board... boards) {
            ApplicationDatabase database = ApplicationDatabase.getInstance(weakReference.get()
                    .getApplicationContext());


            return database.boardDao().insert(boards[0]);
        }

        @Override
        protected void onPostExecute(Long result) {
             if (result != (long) -1) {
                 Toast.makeText(weakReference.get(), R.string.success, Toast.LENGTH_LONG)
                         .show();
             }else {
                 Toast.makeText(weakReference.get(), R.string.failure, Toast.LENGTH_LONG)
                         .show();
             }
        }
    }
}
