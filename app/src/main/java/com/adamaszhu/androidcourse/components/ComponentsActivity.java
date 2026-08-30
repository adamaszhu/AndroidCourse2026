package com.adamaszhu.androidcourse.components;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.databinding.ActivityComponentsBinding;
import com.adamaszhu.androidcourse.entity.Book;
import com.adamaszhu.androidcourse.entity.MockDataGenerator;
import com.adamaszhu.androidcourse.utility.BaseActivity;

import java.util.Calendar;

public class ComponentsActivity extends BaseActivity {

    private static final String TAG = ComponentsActivity.class.getName();

    private ActivityComponentsBinding binding;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.components, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_first_item) {
            binding.tvResult.setText(item.getTitle());
        }
        return super.onOptionsItemSelected(item);
    }

    public void load() {
        binding = ActivityComponentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Log.i(TAG, "Screen is created");
    }

    public void setup() {

        // Visibility
        binding.btnVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Flip visibility of the result text view
                if (binding.tvResult.getVisibility() == View.VISIBLE) {
                    binding.tvResult.setVisibility(View.INVISIBLE);
                } else {
                    binding.tvResult.setVisibility(View.VISIBLE);
                }
            }
        });

        // Buttons
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup radioGroup, int i) {
                RadioButton button = radioGroup.findViewById(i);
                binding.tvResult.setText(button.getText());
            }
        });
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton compoundButton, boolean b) {
                binding.tvResult.setText(b ? "Checked" : "Unchecked");
            }
        };
        binding.checkbox.setOnCheckedChangeListener(onCheckedChangeListener);
        binding.switchButton.setOnCheckedChangeListener(onCheckedChangeListener);
        binding.toggleButton.setOnCheckedChangeListener(onCheckedChangeListener);

        // Seek bar
        int maxValue = 100;
        binding.seekbar.setMax(maxValue);
        binding.seekbar.setMin(0);
        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                double value = (double) i / maxValue;
                binding.tvResult.setText(String.valueOf(value));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Text view
        binding.btnLinkText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tvResult.setText("Sample: adamas@gmail.com");
            }
        });
        binding.editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    binding.tvResult.setText(binding.editText.getText());
                    return true;
                }
                return false;
            }
        });

        // Date picker
        Calendar calendar = Calendar.getInstance();
        calendar.set(2026, 0, 1);
        binding.datePicker.setMinDate(calendar.getTimeInMillis());
        calendar.set(2026, 8, 1);
        binding.datePicker.setMaxDate(calendar.getTimeInMillis());
        binding.datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                String value = String.format("%d/%d/%d", i, i1 + 1, i2);
                binding.tvResult.setText(value);
            }
        });

        // Number picker
        String[] countries = getResources().getStringArray(R.array.countries);
        binding.numberPicker.setMinValue(0);
        binding.numberPicker.setMaxValue(2);
        binding.numberPicker.setDisplayedValues(countries);
        binding.numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                if (i1 >= 0 && i1 < countries.length) {
                    String country = countries[i1];
                    binding.tvResult.setText(String.valueOf(country));
                }
            }
        });

        // Default alert
        binding.btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = new EditText(ComponentsActivity.this);
                new AlertDialog.Builder(ComponentsActivity.this)
                        .setTitle("Title")
                        .setMessage("Message")
                        .setView(editText)
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {}
                        })
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(ComponentsActivity.this, R.string.ok, Toast.LENGTH_LONG)
                                        .show();
                            }
                        })
                        .show();
            }
        });

        // Custom alert
        binding.btnCustomAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(ComponentsActivity.this);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.tvResult.setText(R.string.ok);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        // Spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ComponentsActivity.this, android.R.layout.simple_spinner_item, countries);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(arrayAdapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0 && i < countries.length) {
                    String country = countries[i];
                    binding.tvResult.setText(String.valueOf(country));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        Book[] books = MockDataGenerator.mockBooks(4);
        ArrayAdapter<Book> bookArrayAdapter = new ArrayAdapter<>(ComponentsActivity.this, android.R.layout.simple_spinner_item, books);
        bookArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerBook.setAdapter(bookArrayAdapter);
        binding.spinnerBook.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0 && i < books.length) {
                    Book book = books[i];
                    binding.tvResult.setText(book.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }
}