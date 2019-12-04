package pl.shu.camerax.sample.card

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pl.shu.camerax.sample.R

class CardScannerFragment : Fragment(R.layout.fragment_camera) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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