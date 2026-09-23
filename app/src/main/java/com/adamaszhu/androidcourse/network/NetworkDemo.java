package com.adamaszhu.androidcourse.network;

import android.view.View;

import androidx.annotation.NonNull;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.demo.Demo;
import com.adamaszhu.androidcourse.demo.DemoButton;
import com.adamaszhu.androidcourse.entity.Book;
import com.adamaszhu.androidcourse.entity.MockDataGenerator;
import com.adamaszhu.androidcourse.entity.Phone;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkDemo extends Demo {
    @Override
    public int getTitleId() {
        return R.string.feature_network;
    }

    @Override
    public DemoButton[] getDemoButtons() {
        return new DemoButton[]{
                rtdbRead(),
                rtdbWrite(),
                api(),
                apiSingle(),
                rtdbReadArray(),
                rtdbWriteArray()
        };
    }

    private DemoButton rtdbRead() {
        return new DemoButton(R.string.feature_network_read_DB, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value = dataSnapshot.getValue(String.class);
                        getListener().updateOutput(value);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }

    private DemoButton rtdbWrite() {
        return new DemoButton(R.string.feature_network_write_db, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");
                myRef.setValue("Hello, World!");
            }
        });
    }

    private DemoButton api() {
        return new DemoButton(R.string.feature_network_api, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAPIClient client = new PhoneAPIClient();
                client.getPhones(new PhoneAPIResult<List<Phone>>() {
                    @Override
                    public void onReceiveObject(List<Phone> object) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (Phone phone : object) {
                            stringBuilder.append(phone.name + "\n");
                        }
                        getListener().updateOutput(stringBuilder.toString());
                    }

                    @Override
                    public void onFailure() {
                    }
                });
            }
        });
    }

    private DemoButton apiSingle() {
        return new DemoButton(R.string.feature_network_api_single, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAPIClient client = new PhoneAPIClient();
                client.getPhone(1, new PhoneAPIResult<Phone>() {
                    @Override
                    public void onReceiveObject(Phone object) {
                        getListener().updateOutput(object.name);
                    }

                    @Override
                    public void onFailure() {
                    }
                });
            }
        });
    }

    private DemoButton rtdbReadArray() {
        return new DemoButton(R.string.feature_network_rtdb_read_array, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("books");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Book book = child.getValue(Book.class);
                            stringBuilder.append(book.toString() + "\n");
                        }
                        getListener().updateOutput(stringBuilder.toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }

    private DemoButton rtdbWriteArray() {
        return new DemoButton(R.string.feature_network_rtdb_write_array, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("books");
                Book[] books = MockDataGenerator.mockBooks(5);
                myRef.setValue(Arrays.asList(books));
            }
        });
    }
}
