package com.example.fileexplorer.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import com.example.fileexplorer.databinding.ActivitySplashBinding
import com.example.fileexplorer.util.PermissionUtils
import com.example.fileexplorer.view.file.FileActivity

class SplashActivity : AppCompatActivity() {
    companion object {
        const val PERMISSION_EXTERNAL = 111
    }

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (PermissionUtils.isPermission(PERMISSION_EXTERNAL, this)) {
            launchMainScreen(this)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PERMISSION_EXTERNAL) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    launchMainScreen(this)
                } else {
                    finish()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_EXTERNAL) {
            if (grantResults.isNotEmpty() && permissions[0] == Manifest.permission.READ_EXTERNAL_STORAGE) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    launchMainScreen(this)
                } else {
                    PermissionUtils.isPermission(
                        PERMISSION_EXTERNAL,
                        this
                    )
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun launchMainScreen(activity: Activity) {
        startActivity(Intent(activity, FileActivity::class.java))
        finish()
    }
}