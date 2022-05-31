package com.getstarted.lesson1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LogInActivity : AppCompatActivity() {
    lateinit var userName: EditText
    lateinit var userPassword: EditText
    lateinit var logInButton: Button
    private val passwordLength = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        userName = findViewById(R.id.login_field)
        userPassword = findViewById(R.id.password_field)

         logInButton = findViewById(R.id.log_in_button)
        logInButton.setOnClickListener { checkUser() }
    }

    /**
     * checks user`s input if user name is correct start to check him password
     */
    private fun checkUser() {
        val userNameToText = userName.text

        if (userNameToText.isEmpty()) {
            Toast.makeText(this, "name cannot be empty",
                Toast.LENGTH_SHORT).show()
        }
        else if (' ' in userNameToText){
            Toast.makeText(this, "name cannot contain space sign",
                Toast.LENGTH_SHORT).show()
        }
        else checkPassword()
    }

    /**
     * checks if password is difficult (has length bigger than $passwordLength,
     * min 1 big, small letter and digit)
     */
    private fun checkPassword() {
        val userPasswordString = userPassword.text

        when  {
            (userPasswordString.isEmpty()) ->
                Toast.makeText(this, "password cannot be empty",
                    Toast.LENGTH_SHORT).show()
            ' ' in userPasswordString -> {
                Toast.makeText(this, "password cannot contain space sign",
                    Toast.LENGTH_SHORT).show()
            }
            !hasDigit(userPasswordString) -> {
                Toast.makeText(this, "password must contains at least one digit",
                    Toast.LENGTH_SHORT).show()
            }
            !hasBigLetter(userPasswordString) -> {
                Toast.makeText(this, "password must contains at least one big letter",
                    Toast.LENGTH_SHORT).show()
            }
            !hasSmallLetter(userPasswordString) -> {
                Toast.makeText(this, "password must contains at least one small letter",
                    Toast.LENGTH_SHORT).show()
            }
            userPasswordString.length < passwordLength -> Toast.makeText(this,
                                         "password must contains min 6 elements",
                                                                    Toast.LENGTH_SHORT).show()
            else ->  {
                // Hide the keyboard
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(logInButton.windowToken, 0)

                Toast.makeText(this, "You logged in",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun hasSmallLetter(userPassword: Editable): Boolean {
        for (i in 'a'..'z') {
            if ("$i" in userPassword) return true
        }
        return false
    }

    private fun hasBigLetter(userPassword: Editable): Boolean {
        for (i in 'A'..'Z') {
            if ("$i" in userPassword) return true
        }
        return false
    }

    private fun hasDigit(userPassword: Editable): Boolean {
        for (i in 0..9) {
            if ("$i" in userPassword) return true
        }
        return false
    }
}