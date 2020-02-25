package com.example.user.talleristamod.PackageGameRaceQr

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.user.talleristamod.GlobalVariables.GlobalVariables
import com.example.user.talleristamod.R
import com.google.android.gms.common.api.CommonStatusCodes
import java.util.*


class ActivityFollowCodeQr : AppCompatActivity() {

    private lateinit var mResultTextView: TextView
    internal var idQuestion: ArrayList<String>? = null
    internal var classActivityId: Int? = null
    internal val databaseRaceQr = DatabaseRaceQr(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follow_code_qr)
        idQuestion = ArrayList<String>()

        databaseRaceQr.signalFinishActivity()

        val parametros = this.intent.extras
        if (parametros != null) {
            idQuestion = parametros.getStringArrayList("idQuestion")
            classActivityId = parametros.getInt("activityClassId")
        }

        mResultTextView = findViewById(R.id.result_textview)
        mResultTextView.text = (GlobalVariables.QRVISIT.get(GlobalVariables.POINTS_EARNED)).toString();

        findViewById<Button>(R.id.buttonAceptarReto).setOnClickListener {
            val intent = Intent(applicationContext, BarcodeCaptureActivity::class.java)
            intent.putStringArrayListExtra("idQuestion", idQuestion)
            intent.putExtra("activityClassId", 1)
            startActivityForResult(intent, BARCODE_READER_REQUEST_CODE)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    mResultTextView.text = (GlobalVariables.QRVISIT.get(GlobalVariables.POINTS_EARNED)).toString();
                } else
                    mResultTextView.setText(R.string.no_barcode_captured)
            } else
                Log.e(LOG_TAG, String.format(getString(R.string.barcode_error_format),
                        CommonStatusCodes.getStatusCodeString(resultCode)))
        } else
            super.onActivityResult(requestCode, resultCode, data)
    }



    companion object {
        private val LOG_TAG = ActivityFollowCodeQr::class.java.simpleName
        private val BARCODE_READER_REQUEST_CODE = 1
    }

    override fun onBackPressed() {
        Toast.makeText(this, "La funcion volver esta deshabilitada durante la carrera", Toast.LENGTH_SHORT).show()
    }
}
