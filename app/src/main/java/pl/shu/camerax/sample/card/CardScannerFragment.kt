package pl.shu.camerax.sample.card

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.common.util.concurrent.ListenableFuture
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import pl.shu.camerax.sample.R
import java.util.concurrent.Executors

class CardScannerFragment : Fragment(R.layout.fragment_camera) {

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    private val uiThreadExecutor by lazy { ContextCompat.getMainExecutor(context) }

    private val processsingExecutor by lazy { Executors.newSingleThreadExecutor() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        runWithPermissions(Manifest.permission.CAMERA) {
            initCamera(view)
        }
    }

    private fun initCamera(view: View) {
        val previewUseCase = Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .build()
        val previewView = view.findViewById<PreviewView>(R.id.preview_view)
        previewUseCase.previewSurfaceProvider = previewView.previewSurfaceProvider
        cameraProviderFuture.addListener(Runnable {
            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(LensFacing.BACK)
                .build()

            val imageAnalyser = CardScannerAnalyser()
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(android.util.Size(1920, 1080)).build()
            imageAnalysis.setAnalyzer(processsingExecutor, imageAnalyser)


            val cameraProvider = cameraProviderFuture.get()
            cameraProvider.bindToLifecycle(this, cameraSelector, previewUseCase, imageAnalysis)

        }, uiThreadExecutor)
    }

    fun onCardDetected() {
        val cardNumber = ""
        val expiryDate = ""
        //FIXME: Security issue, cardNumber should't be serialized between fragments. Only for test purposes
        val action =
            CardScannerFragmentDirections.actionCardScannerFragmentToCardScannerResultFragment(
                cardNumber,
                expiryDate
            )
        findNavController().navigate(action)
    }
}