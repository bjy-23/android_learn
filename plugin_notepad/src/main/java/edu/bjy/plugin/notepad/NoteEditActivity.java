package edu.bjy.plugin.notepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowId;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.bjy.plugin.notepad.database.DBManager;

public class NoteEditActivity extends AppCompatActivity {
    private TextView tv_save;
    private EditText et_note;
    private NotepadBean notepadBean;
    private int position = -1;
    private int status = STATUS_ADD;
    private static final int STATUS_ADD = 0;
    private static final int STATUS_SAVE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_note_edit);

        initView();

        initData();
    }

    private void initView(){
        tv_save = findViewById(R.id.tv_save);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        DBManager dbManager = new DBManager(NoteEditActivity.this);
                        if (notepadBean != null){
                            notepadBean.setTimestamp(String.valueOf(System.currentTimeMillis()));
                            notepadBean.setNote(et_note.getText().toString());
                            dbManager.updateTableNote(notepadBean);
                        }else {
                            notepadBean = new NotepadBean();
                            notepadBean.setTimestamp(String.valueOf(System.currentTimeMillis()));
                            notepadBean.setNote(et_note.getText().toString());
                            dbManager.insertTableNote(notepadBean);
                        }


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent();
                                intent.putExtra(IntentConst.NOTE, notepadBean);
                                intent.putExtra(IntentConst.POSITION, position);
                                setResult(RESULT_OK, intent);

                                finish();
                            }
                        });
                    }
                };

                new Thread(runnable).start();
            }
        });

        et_note = findViewById(R.id.et_note);
    }

    private void initData(){
        notepadBean = getIntent().getParcelableExtra(IntentConst.NOTE);
        position = getIntent().getIntExtra(IntentConst.POSITION, -1);
        if (notepadBean != null){
            et_note.setText(notepadBean.getNote());
        }
    }
}