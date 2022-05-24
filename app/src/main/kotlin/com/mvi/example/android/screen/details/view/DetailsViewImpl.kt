package com.mvi.example.android.screen.details.view

import androidx.core.widget.doOnTextChanged
import com.arkivanov.mvikotlin.core.view.BaseMviView
import com.mvi.example.android.databinding.FragmentDetailsBinding

class DetailsViewImpl(binding: FragmentDetailsBinding) :
    BaseMviView<DetailsView.Model, DetailsView.Event>(),
    DetailsView {

    private val button = binding.buttonConfirm
    private val title = binding.editTextTitle
    private val text = binding.editTextText
    private var isSettingState = false

    init {
        binding.buttonConfirm.setOnClickListener {
            dispatch(DetailsView.Event.OnButtonClicked)
        }
        binding.editTextTitle.doOnTextChanged { text, _, _, _ ->
            if (!isSettingState) {
                dispatch(DetailsView.Event.UpdateTitle(text.toString()))
            }
        }
        binding.editTextText.doOnTextChanged { text, _, _, _ ->
            if (!isSettingState) {
                dispatch(DetailsView.Event.UpdateText(text.toString()))
            }
        }
    }

    override fun render(model: DetailsView.Model) {
        super.render(model)
        button.text = button.context.getString(model.buttonState.label)

        if (title.text.toString() != model.material.title) {
            isSettingState = true
            title.setText(model.material.title)
            isSettingState = false
        }
        if (text.text.toString() != model.material.text) {
            isSettingState = true
            text.setText(model.material.text)
            isSettingState = false
        }
    }
}