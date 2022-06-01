package com.getstarted.lesson1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.inputmethod.InputMethodManager
import com.getstarted.lesson1.databinding.ActivityLogInBinding
import com.google.android.material.snackbar.Snackbar


private const val PASSWORD_LENGTH = 6
private val digit = Regex("[0-9]+")
private val smallLetter = Regex("[a-z]+")
private val bigLetter = Regex("[A-Z]+")

class LogInActivity : AppCompatActivity() {
    lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logInButton.setOnClickListener {
            // Hide the keyboard
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.logInButton.windowToken, 0)

            checkUser()
        }
    }

    /**
     * checks user`s input if user name is correct start to check him password
     */
    private fun checkUser() {
        val userNameToText = binding.loginField.text

        if (userNameToText.isEmpty()) {
            Snackbar.make(binding.snackbarText, R.string.name_is_empty,
                Snackbar.LENGTH_LONG).show()
        }
        else if (' ' in userNameToText){
            Snackbar.make(binding.snackbarText, R.string.name_with_space,
                Snackbar.LENGTH_LONG).show()
        }
        else checkPassword()
    }

    /**
     * checks if password is difficult (has length bigger than $passwordLength,
     * min 1 big, small letter and digit)
     */
    private fun checkPassword() {
        val userPasswordString = binding.passwordField.text

        when  {
            (userPasswordString.isEmpty()) ->
                Snackbar.make(binding.snackbarText, R.string.password_is_empty,
                    Snackbar.LENGTH_LONG).show()
            ' ' in userPasswordString -> {
                Snackbar.make(binding.snackbarText, R.string.password_with_space,
                    Snackbar.LENGTH_LONG).show()
            }
            !digit.containsMatchIn(userPasswordString)  -> {
                Snackbar.make(binding.snackbarText, R.string.password_needs_digit,
                    Snackbar.LENGTH_LONG).show()
            }
            !bigLetter.containsMatchIn(userPasswordString) -> {
                Snackbar.make(binding.snackbarText, R.string.password_needs_big_letter,
                    Snackbar.LENGTH_LONG).show()
            }
            !smallLetter.containsMatchIn(userPasswordString) -> {
                Snackbar.make(binding.snackbarText, R.string.password_needs_small_letter,
                    Snackbar.LENGTH_LONG).show()
            }
            userPasswordString.length < PASSWORD_LENGTH -> {
                Snackbar.make(
                    binding.snackbarText, R.string.short_password,
                    Snackbar.LENGTH_LONG).show()
            }
            else ->  {
                Snackbar.make(binding.snackbarText, R.string.you_logged_in,
                    Snackbar.LENGTH_LONG).show()
            }
        }
    }
}