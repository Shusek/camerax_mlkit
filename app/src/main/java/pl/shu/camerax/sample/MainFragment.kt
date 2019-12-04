package pl.shu.camerax.sample

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment(R.layout.fragment_main) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<View>(R.id.card_scanner).setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToCardScannerFragment()
            findNavController().navigate(action)
        }
    }
}