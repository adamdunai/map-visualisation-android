package com.example.mapvisualisation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mapvisualisation.R
import com.example.mapvisualisation.databinding.FragmentScooterInfoBinding
import com.example.mapvisualisation.main.model.ScooterInfoUiModel
import com.example.mapvisualisation.main.viewmodel.ScooterInfoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ScooterInfoDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentScooterInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ScooterInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentScooterInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collectLatest { model ->
                    model?.let(::initUi)
                }
            }
        }
    }

    private fun initUi(model: ScooterInfoUiModel) {
        with(binding) {
            scooterNumberTextView.text =
                getString(R.string.scooter_info_number, model.fleetbirdId)
            stateTextView.text =
                getString(R.string.scooter_info_state, model.state)
            batteryTextView.text =
                getString(R.string.scooter_info_battery, model.battery)
            zoneTextView.text = model.zoneId
        }
    }

    fun showBottomSheet(
        manager: FragmentManager,
        scooterId: String,
    ) {
        arguments = Bundle().apply {
            putString(
                ScooterInfoViewModel.KEY_SCOOTER_ID,
                scooterId
            )
        }

        super.show(manager, this::class.java.name)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
