package com.adamaszhu.androidcourse.storage;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.View;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.demo.Demo;
import com.adamaszhu.androidcourse.demo.DemoButton;
import com.adamaszhu.androidcourse.entity.Book;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.ArrayList;

public class StorageDemo extends Demo {
    @Override
    public int getTitleId() {
        return R.string.feature_storage;
    }

    @Override
    public DemoButton[] getDemoButtons() {
        return new DemoButton[]{
                prefLoad(),
                prefWrite(),
                dbLoad(),
                dbAdd(),
                dbUpdate(),
                dbDelete(),
                fileLoad(),
                fileWrite(),
                cacheLoad(),
                cacheWrite(),
                contentLoad(),
                contentWrite()
        };
    }

    private DemoButton prefLoad() {
        return new DemoButton(R.string.feature_storage_pref_load, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = activity.getSharedPreferences("Pref", MODE_PRIVATE);
                String value = pref.getString("PrefKey", "Not Exist");
                getListener().updateOutput(value);
            }
        });
    }

    private DemoButton prefWrite() {
        return new DemoButton(R.string.feature_storage_pref_write, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = activity.getSharedPreferences("Pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("PrefKey", "This is a sample");
                editor.apply();
            }
        });
    }

    private DemoButton dbLoad() {
        return new DemoButton(R.string.feature_storage_sql_load, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookDBHelper dbHelper = new BookDBHelper(activity);
                ArrayList<Book> books = dbHelper.getBooks();
                if (books.isEmpty()) {
                    getListener().updateOutput("No records");
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (Book book : books) {
                    stringBuilder.append(book.toString() + "\n");
                }
                getListener().updateOutput(stringBuilder.toString());
            }
        });
    }

    private DemoButton dbAdd() {
        return new DemoButton(R.string.feature_storage_sql_insert, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookDBHelper dbHelper = new BookDBHelper(activity);
                dbHelper.addBook("A Book", "Adamas");
            }
        });
    }

    private DemoButton dbUpdate() {
        return new DemoButton(R.string.feature_storage_sql_update, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookDBHelper dbHelper = new BookDBHelper(activity);
                dbHelper.updateBookName("Secondary Book", "Adamas");
            }
        });
    }

    private DemoButton dbDelete() {
        return new DemoButton(R.string.feature_storage_sql_delete, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookDBHelper dbHelper = new BookDBHelper(activity);
                dbHelper.deleteBook("Adamas");
            }
        });
    }

    private DemoButton fileLoad() {
        return new DemoButton(R.string.feature_storage_file_load, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileInputStream inputStream = activity.openFileInput("Test");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String content = loadContent(reader);
                    reader.close();
                    getListener().updateOutput(content);
                } catch (Exception e) {
                    getListener().updateOutput(e.toString());
                }
            }
        });
    }

    private DemoButton fileWrite() {
        return new DemoButton(R.string.feature_storage_file_write, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream outputStream = activity.openFileOutput("Test", MODE_PRIVATE);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                    writer.write("This is an internal file");
                    writer.close();
                } catch (Exception e) {
                    getListener().updateOutput(e.toString());
                }
            }
        });
    }

    private DemoButton cacheLoad() {
        return new DemoButton(R.string.feature_storage_cache_load, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File[] files = activity.getCacheDir().listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        return file.getName().startsWith("Test");
                    }
                });
                if (files.length == 0) {
                    getListener().updateOutput("No cached file");
                    return;
                }
                File file = files[0];
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String content = loadContent(reader);
                    reader.close();
                    getListener().updateOutput(content);
                } catch (Exception e) {
                    getListener().updateOutput(e.toString());
                }
            }
        });
    }

    private DemoButton cacheWrite() {
        return new DemoButton(R.string.feature_storage_cache_write, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    File file = File.createTempFile("Test", "", activity.getCacheDir());
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write("This is a cache file");
                    writer.close();
                } catch (Exception e) {
                    getListener().updateOutput(e.toString());
                }
            }
        });
    }

    private DemoButton contentLoad() {
        return new DemoButton(R.string.feature_storage_content_load, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = activity.getContentResolver().query(BookContentProvider.CONTENT_URI,
                        null, null, null, null);
                StringBuilder stringBuilder = new StringBuilder();
                while (cursor != null && cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    String author = cursor.getString(cursor.getColumnIndexOrThrow("author"));
                    stringBuilder.append(name + " " + author + "\n");
                }
                if (cursor != null) cursor.close();
                getListener().updateOutput(stringBuilder.toString());
            }
        });
    }

    private DemoButton contentWrite() {
        return new DemoButton(R.string.feature_storage_content_add, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("author", "zzzz");
                values.put("name", "aaa");
                Uri profile = activity.getContentResolver().insert(BookContentProvider.CONTENT_URI, values);
            }
        });
    }

    private String loadContent(BufferedReader reader) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
