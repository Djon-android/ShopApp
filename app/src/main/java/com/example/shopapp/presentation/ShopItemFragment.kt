package com.example.shopapp.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shopapp.R
import com.example.shopapp.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {

    private lateinit var viewModel: ShopItemViewModel
    private lateinit var onEditiningFinishedListener: OnEditiningFinishedListener

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonSave: Button

    private var screenMode: String = MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditiningFinishedListener) {
            onEditiningFinishedListener = context
        } else {
            throw java.lang.RuntimeException("Activity not realization interface OnEditingFinishedListener")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shop_item, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews(view)
        addChangeTextListener()
        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
        setupObserveOnErrorAndSuccess()
    }

    private fun launchEditMode() {
        viewModel.editShopItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        viewModel.getShopItemById(shopItemId)
        buttonSave.setOnClickListener {
            val name = etName.text.toString()
            val coun = etCount.text.toString()
            viewModel.editShopItem(name, coun)
        }
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            val name = etName.text.toString()
            val count = etCount.text.toString()
            viewModel.addShopItem(name, count)
        }
    }

    private fun setupObserveOnErrorAndSuccess() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            if (it) {
                tilName.error = "Введите значение имени"
            } else {
                tilName.error = null
            }
        }
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            if (it) {
                tilCount.error = "Введите значение количества"
            } else {
                tilCount.error = null
            }
        }
        viewModel.successLiveData.observe(viewLifecycleOwner) {
            onEditiningFinishedListener.onEditingFinished()
        }
    }

    private fun addChangeTextListener() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun parseArgs() {
        val args = requireArguments()

        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }

    }

    private fun initViews(view: View) {
        with(view) {
            tilName = findViewById(R.id.til_name)
            tilCount = findViewById(R.id.til_count)
            etName = findViewById(R.id.et_name)
            etCount = findViewById(R.id.et_count)
            buttonSave = findViewById(R.id.button_save)
        }
    }

    interface OnEditiningFinishedListener {
        fun onEditingFinished()
    }
    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }


    }
}