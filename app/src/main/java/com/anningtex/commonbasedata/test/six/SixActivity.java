package com.anningtex.commonbasedata.test.six;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import com.anningtex.commonbasedata.R;
import com.anningtex.commonbasedata.data.base.BaseActivity;
import com.anningtex.commonbasedata.test.six.database.SixDataBase;
import com.anningtex.commonbasedata.test.six.database.StudentBean;
import com.anningtex.commonbasedata.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Song
 * @desc:Room
 */
public class SixActivity extends BaseActivity {
    @BindView(R.id.etInputId)
    EditText etInputId;
    @BindView(R.id.lvStudent)
    ListView lvStudent;

    private SixDataBase dataBase;
    private List<StudentBean> studentList;
    private SixAdapter adapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_six;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        studentList = new ArrayList<>();
        adapter = new SixAdapter(SixActivity.this, studentList);
        lvStudent.setAdapter(adapter);
        lvStudent.setOnItemLongClickListener((adapterView, view, position, id) -> {
            updateOrDeleteDialog(studentList.get(position));
            return false;
        });

        dataBase = SixDataBase.getInstance(this);
        new QueryStudentTask().execute();
    }

    @OnClick({R.id.btnInsertStudent, R.id.btnQueryStudentAll, R.id.btnQueryStudentId})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnInsertStudent:
                openAddStudentDialog();
                break;
            case R.id.btnQueryStudentAll:
                new QueryStudentTask().execute();
                break;
            case R.id.btnQueryStudentId:
                String studentID = etInputId.getText().toString().trim();
                if (TextUtils.isEmpty(studentID)) {
                    ToastUtils.showShortToast(SixActivity.this, "输入不能为空");
                } else {
                    new QueryStudentIdTask(Integer.valueOf(studentID)).execute();
                    etInputId.setText("");
                }
                break;
            default:
                break;
        }
    }

    private void openAddStudentDialog() {
        View customView = this.getLayoutInflater().inflate(R.layout.dialog_layout_student, null);
        final EditText etName = customView.findViewById(R.id.etName);
        final EditText etAge = customView.findViewById(R.id.etAge);

        final AlertDialog.Builder builder = new AlertDialog.Builder(SixActivity.this);
        AlertDialog dialog = builder.create();
        dialog.setTitle("Add Student");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", (dialog1, which) -> {
            if (TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etAge.getText().toString())) {
                ToastUtils.showShortToast(SixActivity.this, "输入不能为空");
            } else {
                new InsertStudentTask(etName.getText().toString(), etAge.getText().toString()).execute();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", (dialog12, which) -> dialog12.dismiss());
        dialog.setView(customView);
        dialog.show();
    }

    private void updateOrDeleteDialog(final StudentBean student) {
        final String[] options = new String[]{"修改", "删除"};
        new AlertDialog.Builder(SixActivity.this)
                .setTitle("Choose")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        openUpdateStudentDialog(student);
                    } else if (which == 1) {
                        new DeleteStudentTask(student).execute();
                    }
                }).show();
    }

    private void openUpdateStudentDialog(final StudentBean student) {
        if (student == null) {
            return;
        }

        View customView = this.getLayoutInflater().inflate(R.layout.dialog_layout_student, null);
        final EditText etName = customView.findViewById(R.id.etName);
        final EditText etAge = customView.findViewById(R.id.etAge);
        etName.setText(student.name);
        etAge.setText(student.age);

        final AlertDialog.Builder builder = new AlertDialog.Builder(SixActivity.this);
        AlertDialog dialog = builder.create();
        dialog.setTitle("Update Student");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", (dialog1, which) -> {
            if (TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etAge.getText().toString())) {
                ToastUtils.showShortToast(SixActivity.this, "输入不能为空");
            } else {
                new UpdateStudentTask(student.id, etName.getText().toString(), etAge.getText().toString()).execute();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", (dialog12, which) -> dialog12.dismiss());
        dialog.setView(customView);
        dialog.show();
    }

    /**
     * 增删改查
     */
    private class InsertStudentTask extends AsyncTask<Void, Void, Void> {
        String name;
        String age;

        public InsertStudentTask(final String name, final String age) {
            this.name = name;
            this.age = age;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dataBase.studentDao().insertStudent(new StudentBean(name, age));
            studentList.clear();
            studentList.addAll(dataBase.studentDao().getStudentList());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter.notifyDataSetChanged();
        }
    }

    private class DeleteStudentTask extends AsyncTask<Void, Void, Void> {
        StudentBean student;

        public DeleteStudentTask(StudentBean student) {
            this.student = student;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dataBase.studentDao().deleteStudent(student);
            studentList.clear();
            studentList.addAll(dataBase.studentDao().getStudentList());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter.notifyDataSetChanged();
        }
    }

    private class UpdateStudentTask extends AsyncTask<Void, Void, Void> {
        int id;
        String name;
        String age;

        public UpdateStudentTask(final int id, final String name, final String age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dataBase.studentDao().updateStudent(new StudentBean(id, name, age));
            studentList.clear();
            studentList.addAll(dataBase.studentDao().getStudentList());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter.notifyDataSetChanged();
        }
    }

    private class QueryStudentTask extends AsyncTask<Void, Void, Void> {
        public QueryStudentTask() {
        }

        @Override
        protected Void doInBackground(Void... voids) {
            studentList.clear();
            studentList.addAll(dataBase.studentDao().getStudentList());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
        }
    }

    private class QueryStudentIdTask extends AsyncTask<Void, Void, Void> {
        int id;

        public QueryStudentIdTask(int id) {
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            studentList.clear();
            studentList.add(dataBase.studentDao().getStudentById(id));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
        }
    }
}