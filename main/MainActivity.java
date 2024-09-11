package com.example.todolist;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Entity;

import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    Button add;
    AlertDialog dialog;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = findViewById(R.id.add);
        layout = findViewById(R.id.container);

        buildDialog();
        add.setOnClickListener(v -> dialog.show());
    }

    public void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog, null);

        final EditText name = view.findViewById(R.id.nameEdit);

        builder.setView(view);
        builder.setTitle("Enter your Task")
                .setPositiveButton("SAVE", (dialog, which) -> {
                    addCard(name.getText().toString());
                    name.setText(""); // Clear the EditText after saving
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Dismiss the dialog
                });

        dialog = builder.create();
    }

    private void addCard(String name) {
        final View view = getLayoutInflater().inflate(R.layout.card, null);

        TextView nameView = view.findViewById(R.id.name);
        Button delete = view.findViewById(R.id.delete);
        Button moveUp = view.findViewById(R.id.move_up);  // Move Up button
        Button moveDown = view.findViewById(R.id.move_down);  // Move Down button

        nameView.setText(name);

        delete.setOnClickListener(v -> layout.removeView(view));

        moveUp.setOnClickListener(v -> moveCard(view, -1));  // Move Up functionality
        moveDown.setOnClickListener(v -> moveCard(view, 1));  // Move Down functionality

        layout.addView(view);
    }

    private void moveCard(View card, int direction) {
        int index = layout.indexOfChild(card);
        int targetIndex = index + direction;

        if (targetIndex >= 0 && targetIndex < layout.getChildCount()) {
            layout.removeViewAt(index);
            layout.addView(card, targetIndex);  // Re-insert card at the new index
        }
    }
}