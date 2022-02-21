package app.tier.details

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import app.tier.scooter.details.R
import app.tier.scooter.details.databinding.FragmentScooterDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ScooterDetailFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentScooterDetailsBinding? = null
    private val binding: FragmentScooterDetailsBinding
        get() = _binding!!

    private val args by navArgs<ScooterDetailFragmentArgs>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog =
            super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        setStyle(DialogFragment.STYLE_NO_FRAME, 0)
        bottomSheetDialog.setOnShowListener {
            val parentLayout =
                bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet)
            parentLayout?.let { view ->
                val bottomSheetBehaviour = BottomSheetBehavior.from(view)
                val layoutParams = view.layoutParams
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
                view.layoutParams = layoutParams
                view.setBackgroundResource(R.color.background)
                bottomSheetBehaviour.state =
                    BottomSheetBehavior.STATE_HALF_EXPANDED
                bottomSheetBehaviour.skipCollapsed = true
            }
        }
        return bottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScooterDetailsBinding.inflate(inflater)
        setupData()
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setupData() {
        binding.title.text = getString(
            R.string.scooter_details_title,
            args.scooter.fleetBirdId
        )
        binding.batteryIcon.setImageResource(args.scooter.batteryStatus.resIcon)
        binding.battery.text = getString(
            R.string.scooter_details_battery,
            args.scooter.battery
        )
        binding.state.text = args.scooter.state
        binding.model.text = getString(
            R.string.scooter_details_model,
            args.scooter.model
        )

        binding.navigation.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    String.format(
                        MAP_URL,
                        args.scooter.position.latitude,
                        args.scooter.position.longitude
                    )
                )
            )
            startActivity(intent)
        }
    }

    companion object {
        private const val MAP_URL =
            "https://www.google.com/maps/search/?api=1&query=%1\$s,%2\$s&travelmode=walking"
    }
}
