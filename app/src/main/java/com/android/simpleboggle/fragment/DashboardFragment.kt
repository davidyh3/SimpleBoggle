package com.android.simpleboggle.fragment

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.simpleboggle.call.NewGameCallback
import com.android.simpleboggle.call.SubmitCallback
import com.android.simpleboggle.databinding.FragmentDashboardBinding
import kotlin.math.sqrt

class DashboardFragment(private val newGameCallback: NewGameCallback) : Fragment(), SubmitCallback, SensorEventListener {

    private val mBinding by lazy { FragmentDashboardBinding.inflate(layoutInflater) }

    private var totalScore = 0

    private var sensorManager: SensorManager? = null
    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f


    companion object {
        private const val SHAKE_THRESHOLD = 3.0f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = requireContext().getSystemService(SensorManager::class.java)

        sensorManager?.registerListener(this, sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {

            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val deltaX = x - lastX
            val deltaY = y - lastY
            val deltaZ = z - lastZ

            lastX = x
            lastY = y
            lastZ = z

            val accelerationSquareRoot = sqrt((deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ).toDouble()) / SensorManager.GRAVITY_EARTH
            if (accelerationSquareRoot > SHAKE_THRESHOLD) {
                mBinding.btNewGame.performClick()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.apply {
            tvScore.text = totalScore.toString()

            btNewGame.setOnClickListener {
                totalScore = 0
                tvScore.text = "0"
                newGameCallback.onNewGameClick()
            }
        }
    }

    override fun onSubmitClick(score: Int) {
        totalScore += score
        mBinding.tvScore.text = totalScore.toString()
    }

}