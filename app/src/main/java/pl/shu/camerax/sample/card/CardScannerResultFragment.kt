package pl.shu.camerax.sample.card

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import pl.shu.camerax.sample.R

class CardScannerResultFragment : Fragment(R.layout.fragment_card_result) {

    private val args: CardScannerResultFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.card_id).text = args.cardNumber
        view.findViewById<TextView>(R.id.expiry_date).text = args.expiryDate
    }
}