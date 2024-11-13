package com.example.criminalintent

import android.os.Bundle  // imports the following
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.util.Date
import java.util.UUID

private const val TAG = "CrimeFragment"
private const val ARG_CRIME_ID = "crime_id"
private const val DIALOG_DATE = "DialogDate"
private const val REQUEST_DATE = 0

class CrimeFragment: Fragment(), DatePickerFragment.Callbacks {

    private lateinit var crime: Crime  // initializes the variables and the listed UI elements
    private lateinit var titleField: EditText
    private lateinit var dateButton: Button
    private lateinit var solvedCheckBox: CheckBox
    private val crimeDetailViewModel: CrimeDetailViewModel by lazy {
        ViewModelProvider(this).get(CrimeDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        crime = Crime()  // initializes crime variable for the Crime data class
        val crimeId: UUID = arguments?.getSerializable(ARG_CRIME_ID) as UUID
        Log.d(TAG, "arg bundle crime ID: $crimeId")
        // eventually load crime from database
        crimeDetailViewModel.loadCrime(crimeId)
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

//        dateButton.apply {  // applies today's date to dateButton Button text.
//            text = crime.date.toString()
//            isEnabled = false
//        }

        return view  // returns view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeDetailViewModel.crimeLiveData.observe(
            viewLifecycleOwner,
            Observer { crime ->
                crime?.let {
                    this.crime = crime
                    updateUI()
                }
            }
        )
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
                //d
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
                // d
            }
        }

        titleField.addTextChangedListener(titleWatcher)  // links TextChangedListener to titleField EditText element.  User input

        solvedCheckBox.apply {  // onCheckedChangeListener for crime solved CheckBox
            setOnCheckedChangeListener { _, isChecked ->
                crime.isSolved = isChecked
            }
        }

        dateButton.setOnClickListener {
            //DatePickerFragment().apply{
            DatePickerFragment.newInstance(crime.date).apply{
                setTargetFragment(this@CrimeFragment, REQUEST_DATE)
                show(this@CrimeFragment.getParentFragmentManager(), DIALOG_DATE)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        crimeDetailViewModel.saveCrime(crime)
    }

    override fun onDateSelected(date: Date) {
        crime.date = date
        updateUI()
    }

    private fun updateUI() {
        titleField.setText(crime.title)
        dateButton.text = crime.date.toString()
//        solvedCheckBox.isChecked = crime.isSolved
        solvedCheckBox.apply {
            isChecked = crime.isSolved
            jumpDrawablesToCurrentState()
        }
    }

    companion object {

        fun newInstance(crimeId: UUID): CrimeFragment {
            val args = Bundle().apply {
                putSerializable(ARG_CRIME_ID, crimeId)
            }
            return CrimeFragment().apply {
                arguments = args
            }
        }
    }
}