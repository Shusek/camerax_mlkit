package pl.shu.camerax.sample.card

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class CardScannerAnalyser : ImageAnalysis.Analyzer {

    private val firebaseAnalyzer by lazy { FirebaseVision.getInstance().onDeviceTextRecognizer }

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(image: ImageProxy, rotationDegrees: Int) {
        val imageToAnalize = image.image ?: return
        val mediaImage = FirebaseVisionImage.fromMediaImage(
            imageToAnalize, FirebaseVisionImageMetadata.ROTATION_0
        )

        runBlocking {
            val visionText = firebaseAnalyzer.processImage(mediaImage).await()
            for (textBlock in visionText.textBlocks) {
                val lines = textBlock.lines
                for (line in lines) {
                    Timber.d("Found text: ${line.elements.joinToString("<br>") { it.text }}")
                }

            }

            image.close()
        }
    }
}