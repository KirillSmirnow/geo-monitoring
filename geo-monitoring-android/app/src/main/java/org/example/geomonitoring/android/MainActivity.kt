package org.example.geomonitoring.android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import org.example.geomonitoring.R
import org.example.geomonitoring.android.UserSettings.Setting

class MainActivity : Activity() {

    private lateinit var usernameField: EditText
    private lateinit var saveUsernameButton: Button
    private lateinit var sharePositionSwitch: Switch
    private lateinit var scrollView: ScrollView
    private lateinit var logsOutput: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        PositionSharer.initialize(this) {
            runOnUiThread {
                val html = Html.fromHtml("$it<br/>", Html.FROM_HTML_MODE_LEGACY)
                logsOutput.text = TextUtils.concat(logsOutput.text, html)
                scrollView.fullScroll(ScrollView.FOCUS_DOWN)
            }
        }
        refresh()
    }

    private fun findViews() {
        usernameField = findViewById(R.id.usernameField)
        saveUsernameButton = findViewById(R.id.saveUsernameButton)
        sharePositionSwitch = findViewById(R.id.sharePositionSwitch)
        scrollView = findViewById(R.id.scrollView)
        logsOutput = findViewById(R.id.logsOutput)
    }

    private fun refresh() {
        val username = UserSettings.get<String>(this, Setting.USERNAME)
        if (username == null) {
            usernameField.isEnabled = true
            usernameField.text = null
            saveUsernameButton.visibility = View.VISIBLE
            sharePositionSwitch.visibility = View.GONE
            return
        }
        usernameField.isEnabled = false
        usernameField.setText(username)
        saveUsernameButton.visibility = View.GONE
        sharePositionSwitch.visibility = View.VISIBLE
        val sharePosition = UserSettings.get<Boolean>(this, Setting.SHARE_POSITION)!!
        sharePositionSwitch.isChecked = sharePosition
        PositionSharer.enabled = sharePosition
        toggleLocationForegroundService(sharePosition)
    }

    private fun toggleLocationForegroundService(sharePosition: Boolean) {
        val intent = Intent(this, LocationForegroundService::class.java)
        if (sharePosition) {
            startForegroundService(intent)
        } else {
            stopService(intent)
        }
    }

    fun onSaveUsername(view: View) {
        val username = usernameField.text.toString()
        if (username.isBlank()) {
            Toast.makeText(this, "Blank username", Toast.LENGTH_SHORT).show()
            return
        }
        UserSettings.save(this, Setting.USERNAME, username)
        refresh()
    }

    fun onSharePositionSwitchToggled(view: View) {
        val sharePosition = sharePositionSwitch.isChecked
        PositionSharer.enabled = sharePosition
        toggleLocationForegroundService(sharePosition)
        UserSettings.save(this, Setting.SHARE_POSITION, sharePosition)
    }
}
