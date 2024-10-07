package com.example.criminalintent

import android.os.Bundle  // imports the following
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment

class CrimeFragment: Fragment() {

    private lateinit var crime: Crime  // initializes the variables and the listed UI elements
    private lateinit var titleField: EditText
    private lateinit var dateButton: Button
    private lateinit var solvedCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        crime = Crime()  // initializes crime variable for the Crime data class
    }

    override fun onCreateView(
        inflator: LayoutInflater,
        container: ViewGroup?, // views parent
        savedInstanceState: Bundle?
    ): View? {
        val view = inflator.inflate(R.layout.fragment_crime, container, false) // inflate the fragment's view.  Pass in layout resource id, set view's parent, do not immediately add inflated view to view's parent
        titleField = view.findViewById(R.id.crime_title) as EditText
        dateButton = view.findViewById(R.id.crime_date) as Button
        solvedCheckBox = view.findViewById(R.id.crime_solved) as CheckBox

        dateButton.apply {  // applies today's date to dateButton Button text.
            text = crime.date.toString()
            isEnabled = false
        }

        return view  // returns view
    }

    override fun onStart() {
        super.onStart()

        val titleWatcher = object : TextWatcher {  // initializes titleWatcher variable as TextWatcher listener

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                afer: Int
            ) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(  // onTextChanged function that takes user input and returns a string to set the Crime's title
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                crime.title = sequence.toString()
            }

            override fun afterTextChanged(sequece: Editable?) {
                TODO("Not yet implemented")
            }
        }

        titleField.addTextChangedListener(titleWatcher)  // links TextChangedListener to titleField EditText element.  User input

        solvedCheckBox.apply {  // onCheckedChangeListener for crime solved CheckBox
            setOnCheckedChangeListener { _, isChecked ->
                crime.isSolved = isChecked
            }
        }
    }
}